package com.wisdge.cloud.quartz.job;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdge.cloud.activity.dao.AsyncJobDao;
import com.wisdge.cloud.activity.entity.AsyncJobEntity;
import com.wisdge.cloud.excel.dto.CustomerExcel;
import com.wisdge.cloud.excel.listener.CustomerImportListener;
import com.wisdge.cloud.excel.service.CustomerService;
import com.wisdge.cloud.feign.FsServiceFeign;
import com.wisdge.utils.StringUtils;
import feign.Response;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/14
 */
@DisallowConcurrentExecution
public class CustomerImportJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerImportJob.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FsServiceFeign feign;

    @Autowired
    private AsyncJobDao asyncJobDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {

            //提取任务参数
            String jobId = jobExecutionContext.getJobDetail().getJobDataMap().getString("jobId");
            AsyncJobEntity asyncJobEntity = asyncJobDao.selectById(jobId);
            // 0：等待中，1：执行完成，2：执行中，3：异常
            asyncJobEntity.setStatus(2);
            asyncJobEntity.setUpdateTime(new Date());
            asyncJobDao.updateById(asyncJobEntity);
            String changeMapStr = jobExecutionContext.getJobDetail().getJobDataMap().getString("changeMapStr");
            // 执行策略: 0忽略 1替换
            String strategy = jobExecutionContext.getJobDetail().getJobDataMap().getString("strategy");
            String createBy = jobExecutionContext.getJobDetail().getJobDataMap().getString("createBy");

            String url = jobExecutionContext.getJobDetail().getJobDataMap().getString("url");
            String as = jobExecutionContext.getJobDetail().getJobDataMap().getString("as");
            boolean d = jobExecutionContext.getJobDetail().getJobDataMap().getBoolean("d");
            Map map = new HashMap();
            map.put("file", url);
            map.put("d", d);
            map.put("as", as);
            Response response = feign.get(map);
            Response.Body body = response.body();

            InputStream inputStream = null;

            //创建异步任务记录
            QueryWrapper<AsyncJobEntity> jobQueryWrapper = new QueryWrapper<>();
            jobQueryWrapper.eq("id", asyncJobEntity.getId());
            jobQueryWrapper.in("status", Arrays.asList("1,2"));
            jobQueryWrapper.eq("create_by", createBy);
            jobQueryWrapper.eq("name", asyncJobEntity.getName());
            List<AsyncJobEntity> jobList = asyncJobDao.selectList(jobQueryWrapper);
            if (CollectionUtils.isEmpty(jobList)){
                Map changeMap = JSON.parseObject(changeMapStr, HashMap.class);
                Map result = new HashMap();
                //获取输入流
                try {
                    inputStream = body.asInputStream();
                    List<String> verifyPhones = customerService.verifyPhone(null);
                    CustomerImportListener customerImportListener = getExcelList(new BufferedInputStream(inputStream), changeMap, verifyPhones, strategy, createBy, asyncJobEntity);

                    //统计结果
                    Map countMap = customerImportListener.getStatus();
                    if(((int)countMap.get("amount")) != 0) {
                        result.put("总数", countMap.get("amount"));
                    }
                    if(((int)countMap.get("success")) != 0) {
                        result.put("成功数", countMap.get("success"));
                    }
                    if(((int)countMap.get("illegalFormat")) != 0) {
                        result.put("解析异常数", countMap.get("illegalFormat"));
                        asyncJobEntity.setStatus(3);
                    }
                    if(((int)countMap.get("nullRequired")) != 0) {
                        result.put("必填项异常", countMap.get("nullRequired"));
                        asyncJobEntity.setStatus(3);
                    }
                    if(((int)countMap.get("duplicateExcel")) != 0) {
                        result.put("表中必填项重复数", countMap.get("duplicateExcel"));
                        asyncJobEntity.setStatus(3);
                    }
                    if(((int)countMap.get("duplicateDB")) != 0) {
                        result.put("数据库中重复数", countMap.get("duplicateDB"));
                        //执行策略 0:不更新 1:更新 不更新时认为是异常数据
                        if(!StringUtils.isEmpty(strategy) && strategy.equals("0")) {
                            asyncJobEntity.setStatus(3);
                        }
                    }
                    asyncJobEntity.setName("客户导入完成, 上传" + countMap.get("amount") + "个, 导入" + countMap.get("success") + "个");
                } catch (Exception e) {
                    e.printStackTrace();
                    asyncJobEntity.setStatus(3);
                    asyncJobEntity.setName("客户导入失败，请重新导入");
                } finally {
                    //回调异步任务存储
                    if(inputStream != null) {
                        inputStream.close();
                    }
                    //更新任务记录状态
                    if(asyncJobEntity.getStatus() == 2) {
                        asyncJobEntity.setStatus(1);
                    }
                    asyncJobEntity.setComment(result.toString().replace("{", "").replace("}", ""));
                    asyncJobEntity.setUpdateTime(new Date());
                    asyncJobDao.updateById(asyncJobEntity);
                }
            } else {
                //TODO 任务已存在,后续逻辑
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("客户导入"+ new Date());
    }


    public CustomerImportListener getExcelList(BufferedInputStream bis, Map<String, String> map, List<String> verifyPhones, String strategy, String createBy, AsyncJobEntity asyncJobEntity) throws Exception {
        ZipSecureFile.setMinInflateRatio(-1.0d);

        ExcelReader excelReader = null;
        CustomerImportListener customerImportListener = new CustomerImportListener(verifyPhones, strategy, createBy, asyncJobEntity.getId());
        CustomerExcel demoData = new CustomerExcel();
        if(map != null && map.size() > 0) {
            this.changeAnnotation(demoData, map);
        }
        try {
            excelReader = EasyExcel.read(bis, demoData.getClass(), customerImportListener).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return customerImportListener;
    }

    public void changeAnnotation(CustomerExcel demoData, Map<String, String> map) throws NoSuchFieldException, IllegalAccessException {

        Field[] fields = demoData.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            Field field = fields[j];

            if (map.containsKey(field.getName()) && field.isAnnotationPresent(ExcelProperty.class)) {
                //获取SignalsRuleExportDTO字段上的ExcelProperty注解实例
                ExcelProperty excel = field.getAnnotation(ExcelProperty.class);
                //获取 ExcelProperty 这个代理实例所持有的 InvocationHandler
                InvocationHandler excelH = Proxy.getInvocationHandler(excel);
                // 获取 AnnotationInvocationHandler 的 memberValues 字段
                Field excelF = excelH.getClass().getDeclaredField("memberValues");
                // 因为这个字段事 private final 修饰，所以要打开权限
                excelF.setAccessible(true);
                // 获取 memberValues
                Map excelValues = (Map) excelF.get(excelH);
                // 修改 value 属性值
                String[] excelOS = (String[])excelValues.get("value");
                if (excelOS != null) {
                    //TODO 传入要更新的值 更改列的位置
                    excelOS[0] = map.get(field.getName());
//                    excelValues.put("value", map.get(field.getName()));
                }
            }
        }
    }

}
