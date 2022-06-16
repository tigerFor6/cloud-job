package com.wisdge.cloud.quartz.job;


import com.wisdge.cloud.user.dao.UserPerformanceDao;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class UserPerformacnceAnalysisJob extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPerformacnceAnalysisJob.class);

    @Autowired
    private UserPerformanceDao userPerformanceDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LOGGER.info("开始刷新userPerformance数据");
        userPerformanceDao.updateByUserId();
        LOGGER.info("userPerformance数据刷新成功");
    }
}
