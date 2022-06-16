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

import java.util.Date;

/**
 * Description
 *
 * @author tiger
 * @since: 2022/01/04
 */
@Slf4j
@DisallowConcurrentExecution
public class CustomerMotJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMotJob.class);

    @Autowired
    private CustomerCalculateFeign customerCalculateFeign;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        for (int i = 1; i < 12; i++) {
            customerCalculateFeign.task(String.valueOf(i));
            System.out.println("mot success ,taskId is "+ i);
        }
        System.out.println("客户mot更新成功"+ new Date());
    }
}
