package com.wisdge.cloud.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wisdge.cloud.quartz.JobView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/13
 */
public class QuartzJobUtils {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobUtils.class);

    private QuartzJobUtils() {
        super();
    }

    /**
     * 创建quartz定时任务
     *
     * @param scheduler      调度器
     * @param jobClass       任务class,必须继承QuartzJobBean
     * @param cronExpression cron表达式
     * @param jobDataMap     任务信息
     */
    public static void createJob(Scheduler scheduler, String name, String group, Class<? extends QuartzJobBean> jobClass, String cronExpression, JobDataMap jobDataMap) {
        //任务所属分组
        LOG.info("----createJob start ，name:{},group:{}", name, group);
        if (exist(scheduler, name, group)) {
            LOG.info("----createJob fail ,job already existed，name:{},group:{}", name, group);
            return;
        }
        //创建任务
        JobDetail jobDetail;
        if (jobDataMap != null) {
            //requestRecovery(true)指在集群中，一个scheduler执行job失败，将会被另外一个scheduler执行
            jobDetail = newJob(jobClass).withIdentity(name, group).usingJobData(jobDataMap).requestRecovery(true).build();
        } else {
            jobDetail = newJob(jobClass).withIdentity(name, group).requestRecovery(true).build();
        }
        //创建任务触发器
        Trigger trigger;
        if (StringUtils.isBlank(cronExpression)) {
            Date startTime = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);
            trigger = newTrigger().withIdentity(name, group).startAt(startTime).build();
        }else{
            //cron表达式封装
            //missfire处理 withMisfireHandlingInstructionDoNothing 错过触发时间时，不执行执行的，等待下一个执行时间
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            trigger = newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
        }
        //将触发器与任务绑定到调度器内
        try {
            Date firstFireTime = scheduler.scheduleJob(jobDetail, trigger);
            LOG.info("----createJob success，firstFireTime:{}", DateFormatUtils.format(firstFireTime, "yyyy-MM-dd HH:mm:ss"));
        } catch (SchedulerException e) {
            LOG.error("-----createJob exception", e);
        }
    }

    /**
     * 移除一个任务
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */

    public static void close(Scheduler scheduler, String jobName, String jobGroupName,
                             String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            LOG.info("----quartzJob success");
        } catch (SchedulerException e) {
            LOG.error("----quartzJob close exception", e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     */
    public static void modifyJobTime(Scheduler scheduler, String triggerName, String triggerGroupName, String cron) {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 暂停一个job
     *
     * @param scheduler
     * @throws SchedulerException
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Description:恢复一个任务
     * @param jobName
     * @param jobGroupName
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static Trigger.TriggerState getJobStatus(Scheduler scheduler, String name, String group) {
        TriggerKey triggerKey = new TriggerKey(name, group);
        Trigger.TriggerState triggerState = null;
        try {
            triggerState = scheduler.getTriggerState(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggerState;
    }


    /**
     * 判断任务是否存在
     *
     * @param scheduler 调度器
     * @param name      任务名称
     * @param group     任务分组
     * @return true==存在 false==不存在
     */
    public static boolean exist(Scheduler scheduler, String name, String group) {
        JobDetail jobDetail;
        JobKey jobKey = new JobKey(name, group);
        TriggerKey triggerKey = new TriggerKey(name, group);
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                if (Trigger.TriggerState.ERROR.equals(scheduler.getTriggerState(triggerKey))) {
                    LOG.info("-----定时任务状态异常，恢复状态，job name :{}", name);
                    scheduler.resumeJob(jobKey);
                }
                return true;
            }
        } catch (SchedulerException e) {
            LOG.error("-----exist exception", e);
        }
        return false;
    }

    /**
     * 获取定时任务列表
     *
     * @param scheduler 任务调度器
     * @return
     */
    public static List<JobView> jobs(Scheduler scheduler) {
        List<JobView> jobViews = Lists.newArrayList();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    // name and group
                    JobView jobView = new JobView();
                    jobView.setName(jobName);
                    jobView.setGroup(jobGroup);

                    // params
                    JobDetail qJobDetail = scheduler.getJobDetail(jobKey);
                    if (null != qJobDetail.getJobDataMap()) {
                        Map<String, Object> params = Maps.newHashMap();
                        params.putAll(qJobDetail.getJobDataMap());
                        jobView.setParams(params);
                    }
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    // 应该只有一个
                    for (Trigger trigger : triggers) {
                        trigger.getNextFireTime();
                        if (trigger instanceof CronTrigger) {
                            CronTrigger cronTrigger = (CronTrigger) trigger;
                            String cronExpr = cronTrigger.getCronExpression();
                            jobView.setCronExpression(cronExpr);
                        }
                        jobView.setNextFireTime(DateUtils.formatDate(trigger.getNextFireTime(), "yyyy-MM-dd HH:mm:ss"));
                        Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                        jobView.setStatus(state.name());
                    }
                    jobViews.add(jobView);
                }
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return jobViews;

    }
}
