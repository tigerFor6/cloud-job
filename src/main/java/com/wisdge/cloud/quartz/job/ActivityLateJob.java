package com.wisdge.cloud.quartz.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wisdge.cloud.activity.dao.ActivityDao;
import com.wisdge.cloud.activity.dao.ActivityUserDao;
import com.wisdge.cloud.activity.dao.NoticeDao;
import com.wisdge.cloud.activity.dao.NoticeUserDao;
import com.wisdge.cloud.activity.entity.ActivityEntity;
import com.wisdge.cloud.activity.entity.ActivityUserEntity;
import com.wisdge.cloud.activity.entity.Notice;
import com.wisdge.cloud.activity.entity.NoticeUser;
import com.wisdge.cloud.activity.feign.TgCustomerGroupFeign;
import com.wisdge.cloud.enums.ActivityStatusEnum;
import com.wisdge.cloud.user.dao.UserDao;
import com.wisdge.cloud.user.entity.User;
import com.wisdge.cloud.util.DateUtil;
import com.wisdge.utils.SnowflakeIdWorker;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * 活动逾期定时任务
 *
 * @author lsy
 * @date 2022/3/14
 */
@DisallowConcurrentExecution
public class ActivityLateJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLateJob.class);

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private NoticeUserDao noticeUserDao;

    @Autowired
    private ActivityUserDao activityUserDao;

    @Autowired
    private TgCustomerGroupFeign tgCustomerGroupFeign;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        System.out.println("任务逾期定时任务开始执行！");
        //逾期定时任务，逾期前一天和逾期当天发送通知给服务专员
        List<ActivityEntity> activityEntities = activityDao.selectLateActivity();
        //check
        if (null == activityEntities || activityEntities.isEmpty()) {
            System.out.println("当前没有逾期任务！");
            return;
        }

        for (ActivityEntity activityEntity : activityEntities) {
            String activityId = String.valueOf(activityEntity.getId());
            Date endTime = activityEntity.getEndTime();

            //计算结束日期与今天的日期差， 0代表当天即将逾期，发送即将逾期通知，-1代表今天逾期
            long diffDays = DateUtil.getDiffDays(endTime, new Date());
            //是否是当天
            boolean sameDay = isSameDay(diffDays);
            //任务结束时间第二天凌晨，逾期任务
            if (!sameDay) {
                activityEntity.setStatus(ActivityStatusEnum.BE_OVERDUE.getCode());
                activityDao.updateById(activityEntity);
                System.out.println("任务逾期定时任务更新任务状态为逾期！任务ID：" + activityEntity.getId());
            }

            String noticeName = null;
            List<ActivityUserEntity> activityUserEntities = getActivityUserEntities(activityId);
            if (null != activityUserEntities && !activityUserEntities.isEmpty()) {
                for (ActivityUserEntity activityUserEntity : activityUserEntities) {
                    //任务逾期
                    if (!sameDay) {
                        noticeName = "【任务逾期】 您有任务已逾期，点击查看详情";
                        activityUserEntity.setStatus(ActivityStatusEnum.BE_OVERDUE.getCode());
                        activityUserDao.updateById(activityUserEntity);
                        System.out.println("任务逾期定时任务更新活动专员任务状态为逾期！活动专员ID：" + activityUserEntity.getId());
                    } else {
                        //逾期前一天提醒
                        noticeName = "【任务即将逾期】 您有待办任务即将逾期，点击查看详情";
                    }
                    insertNotice(activityUserEntity.getUserId(), noticeName);
                }
            }
        }
    }

    /**
     * 是否是当天
     *
     * @param diffDays 日期差
     * @return
     */
    private boolean isSameDay(long diffDays) {
        //默认前一天
        boolean isSameDay = false;
        //0代表是当天
        if (diffDays == 0L) {
            isSameDay = true;
        }
        return isSameDay;
    }

    /**
     * 获取未完成任务的活动专员
     *
     * @param activityId
     * @return
     */
    private List<ActivityUserEntity> getActivityUserEntities(String activityId) {
        QueryWrapper<ActivityUserEntity> wrapper = new QueryWrapper();
        wrapper.eq("activity_id", activityId);
        wrapper.eq("status", ActivityStatusEnum.ON_GING.getCode());
        List<ActivityUserEntity> activityUserEntities = activityUserDao.selectList(wrapper);
        return activityUserEntities;
    }

    /**
     * 保存通知
     */
    private void insertNotice(String userId, String noticeName) {
        Notice notice = new Notice();
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
        notice.setId(String.valueOf(snowflakeIdWorker.nextId()));
        notice.setCatalogId("2");
        notice.setStatus(1);
        notice.setResult("/TaskManagement/TodoLists");
        notice.setSubject(noticeName);
        notice.setContent(noticeName);
        notice.setCreateBy(userId);
        notice.setCreateTime(new Date());
        noticeDao.insert(notice);
        NoticeUser noticeUser = new NoticeUser();
        noticeUser.setId(String.valueOf(snowflakeIdWorker.nextId()));
        noticeUser.setNoticeId(notice.getId());
        noticeUser.setUserId(userId);
        noticeUserDao.insert(noticeUser);

        User user = userDao.selectById(userId);
        if (user != null) {
            tgCustomerGroupFeign.sendWxActivityNotice(user.getPhone(), noticeName);
            System.out.println("任务逾期定时任务发送企业微信消息！专员手机号：" + user.getPhone());
        }
    }
}
