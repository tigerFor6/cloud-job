package com.wisdge.cloud.quartz.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdge.cloud.activity.dao.NoticeDao;
import com.wisdge.cloud.activity.dao.NoticeUserDao;
import com.wisdge.cloud.activity.entity.Notice;
import com.wisdge.cloud.activity.entity.NoticeUser;
import com.wisdge.cloud.excel.dao.CustomerEntityDao;
import com.wisdge.cloud.excel.dao.UserCustomerDao;
import com.wisdge.cloud.excel.dao.UserCustomerPlanDao;
import com.wisdge.cloud.excel.entity.Customer;
import com.wisdge.cloud.excel.entity.UserCustomerEntity;
import com.wisdge.cloud.excel.entity.UserCustomerPlanEntity;
import com.wisdge.cloud.excel.service.CustomerService;
import com.wisdge.cloud.redis.RedisUtils;
import com.wisdge.cloud.user.dao.UserPerformanceDao;
import com.wisdge.cloud.user.entity.CustViewRecentDevelpRes;
import com.wisdge.cloud.util.DateUtil;
import com.wisdge.utils.SnowflakeIdWorker;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/14
 */
@DisallowConcurrentExecution
public class CustomerUpdateJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerUpdateJob.class);

    @Autowired
    private CustomerEntityDao customerEntityDao;

    @Autowired
    private UserCustomerDao userCustomerDao;

    @Autowired
    private UserCustomerPlanDao userCustomerPlanDao;

    @Autowired
    RedisUtils redisUtils;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    @Autowired
    private UserPerformanceDao userPerformanceDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeUserDao noticeUserDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LOGGER.info("?????????????????????"+ new Date());
        //??????????????????
        List<String> idList = (List<String>)jobExecutionContext.getJobDetail().getJobDataMap().get("idList");
        LOGGER.info("idList???"+idList);
        List<Map<String,String>> managersList = (List<Map<String, String>>) jobExecutionContext.getJobDetail().getJobDataMap().get("managersList");
        LOGGER.info("managersList???"+managersList);
        Map<String,String> userMap = (Map<String, String>) jobExecutionContext.getJobDetail().getJobDataMap().get("userMap");
        String link = String.valueOf(jobExecutionContext.getJobDetail().getJobDataMap().get("link"));
        QueryWrapper<UserCustomerPlanEntity> planWrapper = new QueryWrapper<>();
        planWrapper.in("customer_id",idList);
        planWrapper.orderByDesc("weight");
        List<UserCustomerPlanEntity> planList = userCustomerPlanDao.selectList(planWrapper);
        //????????????????????????
        LOGGER.info("????????????????????????");
        QueryWrapper<UserCustomerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("customer_id", idList);
        userCustomerDao.delete(queryWrapper);
        //??????????????????
        LOGGER.info("??????????????????");
        UserCustomerEntity userCustomerEntity = new UserCustomerEntity();
        userCustomerEntity.setEffectiveTime(planList.get(0).getEffectiveTime());
        userCustomerEntity.setCreateBy(planList.get(0).getCreateBy());
        userCustomerEntity.setCreateTime(planList.get(0).getCreateTime());
        userCustomerEntity.setStatus("1");
        //????????????
        LOGGER.info("????????????");
        Notice manaNotice = new Notice();
        manaNotice.setId(String.valueOf(snowflakeIdWorker.nextId()));
        manaNotice.setCatalogId("2");
        manaNotice.setStatus(1);
        manaNotice.setResult(link);
        manaNotice.setSubject("???????????????????????????");
        manaNotice.setContent("?????????????????????????????????" );
        manaNotice.setCreateBy(planList.get(0).getCreateBy());
        manaNotice.setCreateTime(new Date());
        noticeDao.insert(manaNotice);
        //???????????????
        LOGGER.info("???????????????");
        NoticeUser manaNoticeUser = new NoticeUser();
        manaNoticeUser.setNoticeId(manaNotice.getId());
        for (UserCustomerPlanEntity planEntity : planList) {
            userCustomerEntity.setWeight(planEntity.getWeight());
            userCustomerEntity.setUserId(planEntity.getUserId());
            userCustomerEntity.setId(String.valueOf(snowflakeIdWorker.nextId()));
            userCustomerEntity.setCustomerId(planEntity.getCustomerId());
            userCustomerDao.insert(userCustomerEntity);
            //??????????????????
            manaNoticeUser.setId(String.valueOf(snowflakeIdWorker.nextId()));
            manaNoticeUser.setUserId(planEntity.getUserId());
            noticeUserDao.insert(manaNoticeUser);
        }
        LOGGER.info("??????CUSTOMER????????????");
        idList.stream().forEach(id -> {
            //??????CUSTOMER????????????
            Customer customer = new Customer();
            customer.setId(id);
            customer.setServiceAdminId(planList.get(0).getUserId());
            customerEntityDao.updateById(customer);
            //????????????
            Long expire = redisUtils.getExpire("baseInfo:" + id);
            if (expire > 0){
                redisUtils.set("baseInfo:" + id, "",1);
            }
            managersList.stream().forEach(managers -> {
                CustViewRecentDevelpRes develpRes = new CustViewRecentDevelpRes();
                develpRes.setDevelpDate(DateUtil.formatDate(new Date(), 1));
                develpRes.setType("4");
                develpRes.setContent("?????????????????????:" + userMap.get(managers.get("serviceAdminId")));
                develpRes.setRelationId(null);
                List<CustViewRecentDevelpRes> custViewRecentDevelpRes = new ArrayList<CustViewRecentDevelpRes>();
                Long exp = redisUtils.getExpire("recentCustomerDev:" + id);
                if (exp >= -1){
                    String recentCustomerDevKey = redisUtils.get("recentCustomerDev:" + id);
                    custViewRecentDevelpRes = JSONObject.parseArray(recentCustomerDevKey, CustViewRecentDevelpRes.class);
                    custViewRecentDevelpRes.add(develpRes);
                }else {
                    custViewRecentDevelpRes.add(develpRes);
                }
                redisUtils.set("recentCustomerDev:" + id, JSON.toJSONString(custViewRecentDevelpRes), -1L);
            });
        });
        QueryWrapper<UserCustomerPlanEntity> deletePlanWrapper = new QueryWrapper<>();
        deletePlanWrapper.in("customer_id",idList);
        userCustomerPlanDao.delete(deletePlanWrapper);
        userPerformanceDao.updateByUserId();
        LOGGER.info("??????????????????:"+ new Date());
    }

}
