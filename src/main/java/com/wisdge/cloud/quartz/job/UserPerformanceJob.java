package com.wisdge.cloud.quartz.job;

import com.wisdge.cloud.user.dao.UserDao;
import com.wisdge.cloud.user.dao.UserPerformanceDao;
import com.wisdge.cloud.user.entity.User;
import com.wisdge.cloud.user.entity.UserPerformance;
import com.wisdge.utils.SnowflakeIdWorker;
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
 * @since: 2021/7/19
 */
@DisallowConcurrentExecution
public class UserPerformanceJob  extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPerformanceJob.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPerformanceDao userPerformanceDao;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            List<User> users = userDao.findPerformanceUser();
            for (User user : users) {
                UserPerformance userPerformance = new UserPerformance();
                userPerformance.setId(String.valueOf(snowflakeIdWorker.nextId()));
                userPerformance.setUserId(user.getId());
                userPerformance.setCreateTime(new Date());
                userPerformance.setUpdateTime(new Date());
                userPerformanceDao.insert(userPerformance);
            }
            LOGGER.info("用户绩效数据初始化完毕:{}", new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
