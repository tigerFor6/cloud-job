package com.wisdge.cloud.quartz.job;

import com.wisdge.cloud.activity.feign.CustomerCalculateFeign;
import com.wisdge.cloud.excel.dao.CustomerEntityDao;
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
 * @since: 2021/11/25
 */
@Slf4j
@DisallowConcurrentExecution
public class CustomerPositionJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPositionJob.class);

    @Autowired
    private CustomerCalculateFeign customerCalculateFeign;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        customerCalculateFeign.updateCustPositions();
        System.out.println("客户持仓信息批量更新成功"+ new Date());
    }
}
