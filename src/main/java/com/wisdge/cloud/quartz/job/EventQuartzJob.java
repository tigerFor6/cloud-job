package com.wisdge.cloud.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdge.cloud.activity.dao.*;
import com.wisdge.cloud.activity.entity.*;
import com.wisdge.cloud.util.DateUtil;
import com.wisdge.utils.SnowflakeIdWorker;
import com.wisdge.utils.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/13
 */
@DisallowConcurrentExecution
public class EventQuartzJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventQuartzJob.class);
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityTriggerDao activityTriggerDao;

    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private NoticeUserDao noticeUserDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            // 创建人:未开始事件即将开始时（开始前一天）;事件开始时;进行中事件即将结束时（结束前一天）;进行中事件即将结束时;
            // 事件处理人:事件逾期，包括事件出错，任务逾期未处理等情况
            QueryWrapper<ActivityEntity> queryWrapper = new QueryWrapper<>();
            //  草稿：0，未开始：1，进行中：2，已完成：3，逾期：4'
            queryWrapper.in("status", Arrays.asList("1,2".split(",")));
            queryWrapper.isNull("stop_time");
            List<ActivityEntity> activityList = activityDao.selectList(queryWrapper);
            activityList.forEach(activity -> {
                String activityName = activity.getName();
                if (activity.getType() == 1){
                    // 发短信，邮件的活动
                    ActivityTriggerEntity trigger = activityTriggerDao.findActivityTriggerByActivityId(Long.valueOf(activity.getId()));
                    Integer triggerType = trigger.getTriggerType();
                    Date touchTime = trigger.getTouchTime();
                    if (triggerType == 1){
                        // 定时单次
                        if (touchTime.before(new Date())){
                            // 改变活动状态为已完成
                            activity.setStatus(3);
                            activityDao.updateById(activity);
                        }
                    }
                }
                if (activity.getEndTime() != null && activity.getStartTime() != null){
                    if (activity.getEndTime().before(new Date()) && activity.getStatus() == 2){
                        // 任务类:改变活动状态为逾期,系统内部事件：改变活动状态为已完成
                        if (activity.getType() == 0){
                            activity.setStatus(4);
                        }else {
                            activity.setStatus(3);
                        }
                        activityDao.updateById(activity);
                    }
                    if (activity.getStartTime().before(new Date()) && activity.getStatus() == 1){
                        // 改变活动状态为进行中
                        activity.setStatus(2);
                        activityDao.updateById(activity);
                    }
                    long startDay = DateUtil.getRealDiffDays(activity.getStartTime(), new Date()); // 计算差多少天
                    long endDay = DateUtil.getRealDiffDays(activity.getEndTime(), new Date()); // 计算差多少天
                    // 0：等待中，1：执行完成，2：执行中，3：异常
                    String name = "";
                    String comment = "";
                    String link = "/EnevtManagement/Create"; //跳转链接
                    if (startDay == 1L){
                        // 开始前一天，给创建人提醒
                        name = "您创建的事件即将开始-【" + activityName + "】";
                        comment = "您创建的事件即将开始-【" + activityName + "】";
                    }else if (activity.getStartTime().before(new Date())){
                        // 开始，给创建人提醒
                        name = "您创建的事件已开始-【" + activityName + "】";
                        comment = "您创建的事件已开始-【" + activityName + "】";
                    } else if (endDay == 1L){
                        // 结束前一天，给创建人提醒
                        name = "您创建的事件即将结束-【" + activityName + "】";
                        comment = "您创建的事件即将结束-【" + activityName + "】";
                    }else if (activity.getEndTime().before(new Date())){
                        // 结束，给创建人提醒
                        name = "您创建的事件已结束-【" + activityName + "】";
                        comment = "您创建的事件已结束-【" + activityName + "】";
                    }
                    if (StringUtils.isBlank(name)){
                        return;
                    }
                    List<Notice> countNotice = noticeUserDao.findCountNotice(name, activity.getCreateBy());
                    if (countNotice.size() > 0){
                        return;
                    }
                    Notice notice = new Notice();
                    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
                    notice.setId(String.valueOf(snowflakeIdWorker.nextId()));
                    notice.setCatalogId("1");
                    notice.setStatus(1);
                    notice.setSubject(name);
                    notice.setContent(comment);
                    notice.setResult(link);
                    notice.setCreateBy(activity.getCreateBy());
                    notice.setCreateTime(new Date());
                    int result = noticeDao.insert(notice);
                    if (result == 0) {
                        LOGGER.error("插入通知失败");
                        return;
                    }
                    // insert notice-user records
                    NoticeUser noticeUser = new NoticeUser();
                    noticeUser.setId(String.valueOf(snowflakeIdWorker.nextId()));
                    noticeUser.setNoticeId(notice.getId());
                    noticeUser.setUserId(activity.getCreateBy());
                    noticeUserDao.insert(noticeUser);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
