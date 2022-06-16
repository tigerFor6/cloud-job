package com.wisdge.cloud.quartz.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdge.cloud.activity.dao.*;
import com.wisdge.cloud.activity.entity.*;
import com.wisdge.cloud.activity.feign.TgCustomerGroupFeign;
import com.wisdge.cloud.dto.ApiResult;
import com.wisdge.utils.CollectionUtils;
import com.wisdge.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/14
 */
@DisallowConcurrentExecution
public class SmsQuartzJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsQuartzJob.class);
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityUserDao activityUserDao;

    @Autowired
    private ActivityTriggerDao activityTriggerDao;

    @Autowired
    private ActivityConditionDao activityConditionDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private MessageInfoDao messageInfoDao;

    @Autowired
    private TgCustomerGroupFeign tgCustomerGroupFeign;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            List<String> customUserIds = new ArrayList<String>();
            List<String> stepTwoCustomUserIds = new ArrayList<String>();
            String activityId = jobExecutionContext.getJobDetail().getJobDataMap().getString("activity_id");
            ActivityEntity activityEntity = activityDao.selectById(activityId);
            if (activityEntity.getEndTime() != null && activityEntity.getEndTime().before(new Date())){
                return;
            }
            QueryWrapper<ActivityUserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_id", Long.valueOf(activityId));
            List<ActivityUserEntity> users = activityUserDao.selectList(queryWrapper);
            ActivityTriggerEntity activityTrigger = activityTriggerDao.findActivityTriggerByActivityId(Long.valueOf(activityId));
            if (users != null && users.size() > 0){
                List<ActivityUserEntity> customUsers = users.stream().filter(e -> e.getType() == 1).collect(Collectors.toList());
                if (customUsers != null && customUsers.size() > 0){
                    // 全部客户
                    stepTwoCustomUserIds = customerDao.selectList(null).stream().map(CustomerEntity::getId).collect(Collectors.toList());
                }
                List<ActivityUserEntity> tagUsers = users.stream().filter(e -> e.getType() == 2).collect(Collectors.toList());
                if (tagUsers != null && tagUsers.size() > 0){
                    ActivityUserEntity activityUserEntity = tagUsers.get(0);
                    String ruleId = activityUserEntity.getRuleId();
                    // 根据标签组id去查询客户列表
                    ApiResult result = tgCustomerGroupFeign.getCustomerList(String.valueOf(ruleId));
                    HashMap<String, Object> map = (HashMap<String, Object>) result.getData();
                    List<HashMap<String, Object>> tagCustomerList = (List<HashMap<String, Object>>) map.get("tagCustomerList");
                    for (HashMap<String, Object> tagCustomer : tagCustomerList){
                        stepTwoCustomUserIds.add(tagCustomer.get("id").toString());
                    }
                }
            }
            if (activityTrigger == null){
                return;
            }
            Integer triggerType = activityTrigger.getTriggerType();
            if (triggerType == 3){
                // 按条件触发
                String conditionRule = activityTrigger.getConditionRule();
                JSONArray jsonArray= JSONArray.parseArray(conditionRule);
                StringBuffer sBuffer = new StringBuffer();
                jsonArray.stream().forEach(jsonobejct -> arrayIdToString((JSONObject) jsonobejct,sBuffer));
                String ids = sBuffer.toString();
                // 通过条件找到对应得规则条件下的客户
                List<ActivityConditionEntity> conditions = activityConditionDao.selectBatchIds(CollectionUtils.arrayToList(ids.split(",")));
                List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
                if (!CollectionUtils.isEmpty(conditions)){
                    results = jdbcTemplate.queryForList(conditions.get(0).getRule());
                }
                for (ActivityConditionEntity conditionEntity : conditions){
                    String rule = conditionEntity.getRule();
                    List<Map<String, Object>> maps = jdbcTemplate.queryForList(rule);
                    // 交集
                    results = results.stream().filter(item -> maps.contains(item)).collect(Collectors.toList());
                }
                for (Map<String, Object> result : results){
                    String id = result.get("id").toString();
                    if (stepTwoCustomUserIds.contains(id)){
                        customUserIds.add(id);
                    }
                }
            }else{
                // 按照单次，重复触发的活动
                customUserIds = stepTwoCustomUserIds;
            }
            // 通过userIds查询客户的电话号码的集合
            if (CollectionUtils.isEmpty(customUserIds)){
                return;
            }
            List<CustomerEntity> customerEntities = customerDao.selectBatchIds(customUserIds);
            for (CustomerEntity customer : customerEntities){
                if (StringUtils.isEmpty(customer.getPhone())){
                    continue;
                }
                MessageInfoEntity message = new MessageInfoEntity();
                message.setBusinessId(Long.valueOf(activityId));
                message.setType(1);
                message.setSource(customer.getPhone());
                message.setContent(activityTrigger.getContent());
                message.setStatus(0);
                message.setCreateTime(new Date());
                messageInfoDao.insert(message);
            }
            LOGGER.info("发送短信的定时任务:{},{}", activityId, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StringBuffer arrayIdToString(JSONObject jsonobejct, StringBuffer sBuffer) {
        return sBuffer.append(jsonobejct.get("conditionId")).append(",");
    }

}
