package com.wisdge.cloud.redis;

/**
 * RedisKeys类
 *
 * @author tiger
 * @since: 2021/11/25
 */

public class RedisKeys {
    /**
     * 脱敏信息Key
     */
    public static String getSensitizeKey() {
        return "sensitize";
    }

    /**
     * ods联系信息Key
     */
    public static String getOdsContractKey(String customerId) {
        return "odsContract:" + customerId;
    }

    /**
     * 分红记录Key
     */
    public static String getBonusKey(String customerId) {
        return "bonus:" + customerId;
    }

    /**
     * 交易明细Key
     */
    public static String getAffirmBaseKey(String customerId) {
        return "affirmBase:" + customerId;
    }

    /**
     * 定投计划Key
     */
    public static String getPlanKey(String customerId) {
        return "plan:" + customerId;
    }

    /**
     * accountInfo Key
     */
    public static String getAccountInfoKey(String customerId) {
        return "accountInfo:" + customerId;
    }

    /**
     * baseInfo Key
     */
    public static String getBaseInfoKey(String customerId) {
        return "baseInfo:" + customerId;
    }

    /**
     * tagInfo Key
     */
    public static String getTagInfoKey(String customerId) {
        return "tagInfo:" + customerId;
    }

    /**
     * 最近客户信息动态Key
     */
    public static String getRecentCustomerDevKey(String customerId) {
        return "recentCustomerDev:" + customerId;
    }

    /**
     * 最近账户动态Key
     */
    public static String getRecentAccountDevKey(String customerId) {
        return "recentAccountDev:" + customerId;
    }

    /**
     * 客户持有基金产品的名称和金额Key
     */
    public static String getHoldFundNameKey(String customerId) {
        return "holdFundNameInfo:" + customerId;
    }

    /**
     * 客户持有基金产品的类型和金额Key
     */
    public static String getHoldFundTypeKey(String customerId) {
        return "holdFundTypeInfo:" + customerId;
    }

    /**
     * 客户持有的渠道和金额Key
     */
    public static String getHoldAgencyKey(String customerId) {
        return "holdAgencyInfo:" + customerId;
    }

    /**
     * 持仓情况-总资产时间曲线数据Key
     */
    public static String getHoldChartInfoKey(String customerId, String timeFlag) {
        return "holdChartInfo:" + customerId + ":" + timeFlag;
    }

    /**
     * 客户持有基金产品详情Key
     */
    public static String getHoldFundDetailKey(String customerId) {
        return "holdFundDetailInfo:" + customerId;
    }

    /**
     * 损益详情-基金产品名称Key
     */
    public static String getFundNameProfitKey(String customerId) {
        return "fundNameProfit:" + customerId;
    }

    /**
     * 损益详情-基金产品类型Key
     */
    public static String getFundTypeProfitKey(String customerId) {
        return "fundTypeProfit:" + customerId;
    }

    /**
     * 损益详情-渠道Key
     */
    public static String getFundAgencyProfitKey(String customerId) {
        return "fundAgencyProfit:" + customerId;
    }

    /**
     * 损益详情-累计收益时间曲线数据Key
     */
    public static String getIncomeChartInfoKey(String customerId, String timeFlag) {
        return "incomeChartInfo:" + customerId + ":" + timeFlag;
    }

    /**
     * 持有基金收益数据Key
     */
    public static String getHoldFundProfitKey(String customerId,String type) {
        return "holdFundProfitInfo:" + customerId + ":" + type;
    }

    /**
     * 企业微信账户分析数据Key
     */
    public static String getWechatKey(String customerId) {
        return "wechatInfo:" + customerId;
    }

    /**
     * 企业微信账户展开分析数据Key
     */
    public static String getWechatDetailKey(String customerId,String fundCode) {
        return "wechatDetailInfo:" + customerId + ":" + fundCode;
    }

    /**
     * 企业微信基金产品累计收益图表数据Key
     */
    public static String getWechatProfitChartKey(String customerId, String fundCode, String chartDay) {
        return "wechatProfitChart:" + customerId + ":" + fundCode + ":" + chartDay;
    }

    /**
     * 企业微信基金产品七日年化收益率图表数据Key
     */
    public static String getWechatWeekChartKey(String fundCode, String chartDay) {
        return "wechatWeekChart:" + fundCode + ":" + chartDay;
    }
}

