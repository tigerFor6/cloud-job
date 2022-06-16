package com.wisdge.cloud.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/13
 */
@Configuration
public class QuartzConfiguration implements SchedulerFactoryBeanCustomizer {

    @Autowired
    DataSource dataSource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setStartupDelay(2);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setDataSource(dataSource);
        //如果这个覆盖配置为false，quratz启动以后将以数据库的数据为准，配置文件的修改不起作用。
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }

    /**
     * 设置参数
     *
     * @return quartz参数
     */
    private Properties quartzProperties() {
        Properties prop = new Properties();
        // 调度标识名 集群中每一个实例都必须使用相同的名称
        prop.put("org.quartz.scheduler.instanceName", "quartz-job");
        // ID设置为自动获取 每一个必须不同
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 数据保存方式为持久化
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 数据库方言
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        // 表前缀
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        // 是否启用集群功能
        prop.put("org.quartz.jobStore.isClustered", "true");
        // 设置一个频度(毫秒)，用于实例报告给集群中的其他实例。这会影响到侦测失败实例的敏捷度。它只用于设置了 isClustered 为 true 的时候。
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        // 这是 JobStore 能处理的错过触发的 Trigger 的最大数量。处理太多(超过两打) 很快会导致数据库表被锁定够长的时间，这样就妨碍了触发别的(还未错过触发) trigger 执行的性能。
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        // 当前时间与下一次执行的时间差大于改值时认为missFire(错过触发)，根据missFire原则处理任务；若小于该值直接执行任务。默认60000（60秒）,此参数设置要小于定时任务的最间隔小时间
        prop.put("org.quartz.jobStore.misfireThreshold", "5000");
        // 值为 True 时告诉 Quartz (当使用 JobStoreTX 或 CMT 时),调用 JDBC 连接的 setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE) 方法,设置事务隔离级别为串行
        // 这能助于防止某些数据库在高负荷和长事物时的锁超时。
        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");
        // 执行任务的线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "10");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // 指定Quartz生成的线程是否继承初始化线程的上下文类加载器
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
        // 跳过更新检查
        prop.put("org.quartz.scheduler.skipUpdateCheck", true);
        prop.put("org.quartz.plugin.shutdownhook.class", "org.quartz.plugins.management.ShutdownHookPlugin");
        prop.put("org.quartz.plugin.shutdownhook.cleanShutdown", "true");
        return prop;
    }

}
