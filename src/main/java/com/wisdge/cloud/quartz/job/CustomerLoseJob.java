package com.wisdge.cloud.quartz.job;

import com.wisdge.cloud.activity.feign.CustomerCalculateFeign;
import com.wisdge.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Description
 *
 * @author tiger
 * @since: 2022/01/04
 */
@Slf4j
@DisallowConcurrentExecution
public class CustomerLoseJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLoseJob.class);

    @Autowired
    private CustomerCalculateFeign customerCalculateFeign;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        customerCalculateFeign.updateCustomerLoseJob();
        LOGGER.info("customerCockpitJob execute success");
    }
}
