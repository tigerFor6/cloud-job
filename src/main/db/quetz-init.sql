QRTZ_表注释
QRTZ_CALENDARS 存储Quartz的Calendar信息
QRTZ_CRON_TRIGGERS 存储CronTrigger，包括Cron表达式和时区信息
QRTZ_FIRED_TRIGGERS 存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息
QRTZ_PAUSED_TRIGGER_GRPS 存储已暂停的Trigger组的信息
QRTZ_SCHEDULER_STATE 存储少量的有关Scheduler的状态信息，和别的Scheduler实例
QRTZ_LOCKS 存储程序的悲观锁的信息
QRTZ_JOB_DETAILS 存储每一个已配置的Job的详细信息
QRTZ_JOB_LISTENERS 存储有关已配置的JobListener的信息
QRTZ_SIMPLE_TRIGGERS 存储简单的Trigger，包括重复次数、间隔、以及已触的次数
QRTZ_BLOG_TRIGGERS Trigger作为Blob类型存储
QRTZ_TRIGGER_LISTENERS 存储已配置的TriggerListener的信息
QRTZ_TRIGGERS 存储已配置的Trigger的信息

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
                                    `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                    `JOB_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                    `JOB_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                    `DESCRIPTION` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
                                    `JOB_CLASS_NAME` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
                                    `IS_DURABLE` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
                                    `IS_NONCONCURRENT` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
                                    `IS_UPDATE_DATA` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
                                    `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
                                    `JOB_DATA` blob,
                                    PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
                                    KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
                                    KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
                                 `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                 `TRIGGER_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                 `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                 `JOB_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                 `JOB_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                 `DESCRIPTION` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
                                 `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
                                 `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
                                 `PRIORITY` int(11) DEFAULT NULL,
                                 `TRIGGER_STATE` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
                                 `TRIGGER_TYPE` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
                                 `START_TIME` bigint(13) NOT NULL,
                                 `END_TIME` bigint(13) DEFAULT NULL,
                                 `CALENDAR_NAME` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
                                 `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
                                 `JOB_DATA` blob,
                                 PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                 KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
                                 KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
                                 KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
                                 KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
                                 KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
                                 KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
                                 KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
                                 KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
                                 KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
                                 KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
                                 KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
                                 KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
                                 CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- DELETE
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
                                      `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                      `TRIGGER_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                      `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                      `BLOB_DATA` blob,
                                      PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- DELETE
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
                                  `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                  `CALENDAR_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                  `CALENDAR` blob NOT NULL,
                                  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
                                      `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                      `TRIGGER_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                      `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                      `CRON_EXPRESSION` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                      `TIME_ZONE_ID` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
                                      PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
                                       `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                       `ENTRY_ID` varchar(95) COLLATE utf8_unicode_ci NOT NULL,
                                       `TRIGGER_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                       `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                       `INSTANCE_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                       `FIRED_TIME` bigint(13) NOT NULL,
                                       `SCHED_TIME` bigint(13) NOT NULL,
                                       `PRIORITY` int(11) NOT NULL,
                                       `STATE` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
                                       `JOB_NAME` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
                                       `JOB_GROUP` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
                                       `IS_NONCONCURRENT` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
                                       `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
                                       PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
                                       KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
                                       KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
                                       KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
                                       KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
                                       KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                       KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
                              `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                              `LOCK_NAME` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
                              PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
                                            `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                            `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                            PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
                                        `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                        `INSTANCE_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                        `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
                                        `CHECKIN_INTERVAL` bigint(13) NOT NULL,
                                        PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
                                        `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                        `TRIGGER_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                        `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                        `REPEAT_COUNT` bigint(7) NOT NULL,
                                        `REPEAT_INTERVAL` bigint(12) NOT NULL,
                                        `TIMES_TRIGGERED` bigint(10) NOT NULL,
                                        PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                        CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- DELETE
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
                                         `SCHED_NAME` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
                                         `TRIGGER_NAME` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                         `TRIGGER_GROUP` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
                                         `STR_PROP_1` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
                                         `STR_PROP_2` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
                                         `STR_PROP_3` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
                                         `INT_PROP_1` int(11) DEFAULT NULL,
                                         `INT_PROP_2` int(11) DEFAULT NULL,
                                         `LONG_PROP_1` bigint(20) DEFAULT NULL,
                                         `LONG_PROP_2` bigint(20) DEFAULT NULL,
                                         `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
                                         `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
                                         `BOOL_PROP_1` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
                                         `BOOL_PROP_2` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
                                         PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                         CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;