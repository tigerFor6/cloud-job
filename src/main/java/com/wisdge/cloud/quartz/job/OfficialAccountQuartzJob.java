package com.wisdge.cloud.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdge.cloud.activity.dao.ActivityDao;
import com.wisdge.cloud.activity.entity.ActivityEntity;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/14
 */
@DisallowConcurrentExecution
public class OfficialAccountQuartzJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfficialAccountQuartzJob.class);

    @Autowired
    private ActivityDao activityDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            String activityId = jobExecutionContext.getJobDetail().getJobDataMap().getString("activity_id");
            QueryWrapper<ActivityEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", Long.valueOf(activityId));
            queryWrapper.eq("status", 1);
            List<ActivityEntity> activityList = activityDao.selectList(queryWrapper);
            if (activityList != null && activityList.size() > 0){
                // 查询公众号活动发给那些人
                String userIds = "";
                // todo 发短信逻辑
            }
            System.out.println("发送公众号的定时任务"+ new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
