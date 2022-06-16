package com.wisdge.cloud.quartz.job;

import com.wisdge.cloud.activity.feign.CustomerCalculateFeign;
import com.wisdge.cloud.excel.dao.CustomerEntityDao;
import com.wisdge.cloud.excel.entity.Customer;
import com.wisdge.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
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
 * @since: 2021/11/25
 */
@Slf4j
@DisallowConcurrentExecution
public class CustomerBaseJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBaseJob.class);

    @Autowired
    private CustomerEntityDao customerEntityDao;
    @Autowired
    private CustomerCalculateFeign customerCalculateFeign;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        List<Customer> customers = customerEntityDao.selectList(null);
        for (Customer customer : customers){
            customerCalculateFeign.updateBaseInfo(customer.getId());
        }
        System.out.println("客户基本信息更新成功"+ new Date());
    }
}
