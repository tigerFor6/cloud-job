package com.wisdge.cloud.quartz;

import com.wisdge.cloud.util.QuartzJobUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 初始化job
 *
 * @author tiger
 * @since: 2021/9/9
 */

@Component
public class QuartzJobManager {
    @Autowired
    private Scheduler scheduler;

    /**
     * 初始化任务的创建
     * 判断任务是否存在，不存在则创建
     */
    @PostConstruct
    public void initJob() throws ClassNotFoundException {
        //任务逾期每天凌晨执行一次 0 0 0 * * ?   测试 0 */1 * * * ?
        createJob("ActivityLateJob", "activity-late-job", "zinger", "0 0 0 * * ?");
        //创建用户绩效用户定时任务
        createJob("UserPerformanceJob", "userPerformance-job", "zinger", "0 0/30 * * * ?");
        //创建用户绩效数据刷新定时任务
        createJob("UserPerformacnceAnalysisJob", "userPerformance-analysis-job", "zinger", "0 0 0 * * ?");
        // 下面3个job合并执行
        createJob("SynCustomerInfoJob","synCustomerInfo-job","zinger","0 30 7,21 * * ?");
        //创建同步客户customer表中客户经理,网点,基金账号,交易账号,风险等级信息的定时任务,凌晨1点执行
//        createJob("CustomerBaseJob","customer-base-job","zinger","0 0 1 * * ?");
        //创建同步持仓峰值记录customer_position_count表中资产峰值，峰值时间，最近交易时间，近一年交易次数,最近一年交易金额,凌晨2点执行
//        createJob("CustomerPositionCountJob","customer-position-count-job","zinger","0 0 2 * * ?");
        //创建同步持仓历史记录customer_position表中总资产，昨日收益(日收益)，持仓盈亏，持仓收益率，持有产品编码，持有产品名称,凌晨3点执行
//        createJob("CustomerPositionJob","customer-position-job","zinger","0 0 3 * * ?");
        // 更新交易记录，每十分钟执行一次
        createJob("UpdateTradeInfoJob", "update-tradeInfo-job", "zinger", "0 0/10 * * * ?");
        // mot事件更新，每天中午12点执行一次
        createJob("CustomerMotJob","update-mot-job","zinger","0 0 12 * * ?");
        // 驾驶舱数据预处理，每天晚上1点执行
        createJob("CustomerCockpitJob","customer-Cockpit-job","zinger","0 0 1 * * ?");
        // 驾驶舱流失客户计算，每天早上9点执行
        createJob("CustomerLoseJob","customer-lose-job","zinger","0 0 9 * * ?");
    }

    private void createJob(String className, String jobName, String groupName, String cronExpression) throws ClassNotFoundException {
        String path = "com.wisdge.cloud.quartz.job." + className;
        Class jobClass = Class.forName(path);
        QuartzJobUtils.createJob(scheduler, jobName, groupName, jobClass,
                cronExpression, null);
    }
}
