package com.wisdge.cloud.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.wisdge.cloud.excel.dto.CustomerExcel;
import com.wisdge.cloud.excel.service.CustomerService;
import com.wisdge.cloud.util.EncrytUtils;
import com.wisdge.cloud.util.SpringApplicationUtils;
import com.wisdge.utils.SnowflakeIdWorker;
import com.wisdge.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/28
 */

@Slf4j
public class CustomerImportListener extends AnalysisEventListener<CustomerExcel> {

    CustomerService customerService;
    SnowflakeIdWorker snowflakeIdWorker;
    String strategy;
    String createBy;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    private List<CustomerExcel> list = new ArrayList<CustomerExcel>();
    private List<CustomerExcel> nullRequiredList = new ArrayList<CustomerExcel>();
    private List<CustomerExcel> illegalFormatList = new ArrayList<CustomerExcel>();
    private List<CustomerExcel> duplicateExcelList = new ArrayList<CustomerExcel>();
    private List<CustomerExcel> duplicateDBList = new ArrayList<CustomerExcel>();
    private List<String> verifyPhones;
    private Set<String> duplicatePhone = new HashSet<>();
    private Long jobId;

    private int amount = 0;
    private int successSize = 0;
    private int illegalFormatSize = 0;
    private int nullRequiredSize = 0;
    private int duplicateExcelSize = 0;
    private int duplicateDBSize = 0;

    public Map getStatus() {

        amount = illegalFormatSize + nullRequiredSize + successSize + duplicateExcelSize;
        if(!StringUtils.isEmpty(strategy) && strategy.equals("0")) {
            amount += duplicateDBSize;
        }
        Map map = new HashMap();
        map.put("success", successSize);
        map.put("illegalFormat", illegalFormatSize);
        map.put("nullRequired", nullRequiredSize);
        map.put("duplicateExcel", duplicateExcelSize);
        map.put("duplicateDB", duplicateDBSize);
        map.put("amount", amount);
        return map;
    }

    public CustomerImportListener(List<String> verifyPhones, String strategy, String createBy, Long jobId) {// 执行策略: 0忽略 1替换
        this.verifyPhones = verifyPhones;
        this.customerService = SpringApplicationUtils.getBean(CustomerService.class);
        this.snowflakeIdWorker = new SnowflakeIdWorker();
        this.jobId = jobId;
        this.strategy = strategy;
        this.createBy = createBy;
    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(CustomerExcel data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));

        if(StringUtils.isEmpty(data.getPhone()) || StringUtils.isEmpty(data.getFullname())) { //判断必填项是否为空
            nullRequiredList.add(data);
        } else if (!EncrytUtils.CheckMobilePhoneNum(data.getPhone())){ //判断Excel中是否有手机号格式是否合法
            illegalFormatList.add(data);
        } else if (duplicatePhone.contains(data.getPhone())){ //判断Excel中是否有手机号重复
            duplicateExcelList.add(data);
        } else if (verifyPhones.contains(data.getPhone())) { //判断数据库中是否有手机号重复
            duplicateDBList.add(data);
        } else {
            list.add(data);
        }
        duplicatePhone.add(data.getPhone());
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
            nullRequiredList.clear();
            duplicateExcelList.clear();
            duplicateDBList.clear();
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {

        //TODO 执行SQL时插入异常的数据也要统计
        Date now = new Date();
        log.info("{}条数据，开始存储数据库！", list.size());
        //TODO 后期插入批次号相关概念
        if(list.size() > 0) {
            list.forEach(customer -> {
                customer.setId(String.valueOf(snowflakeIdWorker.nextId()));
                customer.setCreateBy(createBy);
                customer.setStatus("1");
                customer.setCreateTime(now);
            });
            //正常数据导入
            customerService.batchCreate(list);
        }
        if(!StringUtils.isEmpty(strategy) && strategy.equals("0")) {
            //异常数据导入
            duplicateDBList.forEach(customer -> {
                customer.setTaskId(String.valueOf(jobId));
                customer.setExceptType("1");
                customer.setId(String.valueOf(snowflakeIdWorker.nextId()));
                customer.setCreateBy(createBy);
                customer.setStatus("1");
                customer.setCreateTime(now);
            });
            customerService.batchCreateTemp(duplicateDBList);
        } else {
            duplicateDBList.forEach(customer -> {
                customer.setId(String.valueOf(snowflakeIdWorker.nextId()));
                customer.setUpdateBy(createBy);
                customer.setUpdateTime(now);
                customerService.update(customer);
            });
            successSize += duplicateDBList.size();
        }
        if(illegalFormatList.size() > 0) {
            illegalFormatList.forEach(customer -> {
                customer.setTaskId(String.valueOf(jobId));
                customer.setExceptType("4");
                customer.setId(String.valueOf(snowflakeIdWorker.nextId()));
                customer.setUpdateBy(createBy);
                customer.setStatus("1");
                customer.setUpdateTime(now);
            });
            customerService.batchCreateTemp(illegalFormatList);
        }
        if(nullRequiredList.size() > 0) {
            nullRequiredList.forEach(customer -> {
                customer.setTaskId(String.valueOf(jobId));
                customer.setExceptType("3");
                customer.setId(String.valueOf(snowflakeIdWorker.nextId()));
                customer.setUpdateBy(createBy);
                customer.setStatus("1");
                customer.setUpdateTime(now);
            });
            customerService.batchCreateTemp(nullRequiredList);
        }
        if(duplicateExcelList.size() > 0) {
            duplicateExcelList.forEach(customer -> {
                customer.setTaskId(String.valueOf(jobId));
                customer.setExceptType("2");
                customer.setId(String.valueOf(snowflakeIdWorker.nextId()));
                customer.setUpdateBy(createBy);
                customer.setStatus("1");
                customer.setUpdateTime(now);
            });
            customerService.batchCreateTemp(duplicateExcelList);
        }
        successSize += list.size();
        illegalFormatSize += illegalFormatList.size();
        nullRequiredSize += nullRequiredList.size();
        duplicateExcelSize += duplicateExcelList.size();
        duplicateDBSize += duplicateDBList.size();
        log.info("存储数据库成功！");
    }
}
