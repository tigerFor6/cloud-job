package com.wisdge.cloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author tiger
 * @since: 2021/6/1
 */
@Slf4j
@Component
public class DateUtil {

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMM = "yyyy-MM";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";
    public static final String DATE_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_YY_MM_DD_HH_MM_SS = "yy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_HHMMSS_SSS = "HHmmssSSS";
    public static final String DATE_FORMAT_YYMMDDHH = "yyMMddHH";
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_YYYYMMDD_HH_MM_SS = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_FORMAT_YYYYMMDD_HH_MM = "yyyy年M月d日 HH点mm分";
    public static final String DATE_FORMAT_YYYYMD_HH_MM_SS = "yyyy年M月d日 HH点mm分ss秒";
    public static final String DATE_FORMAT_MM_dd_HH_mm = "MM月dd日 HH:mm";
    public static final String DATE_FORMAT_M_D_HHMM = "M月d日 HH:mm";
    public static final String DATE_FORMAT_YYYYMD_HHMMSS = "yyyy年M月d日 HH:mm:ss";
    public static final String DATE_FORMAT_YYYYMD_HHMM = "yyyy年M月d日 HH:mm";
    public static final String DATE_FORMAT_M_D = "M月d日";
    public static final String DATE_FORMAT_H_M = "HH时mm分";
    public static final String DATE_FORMAT_HHMM = "HH:mm";
    public static final String DATE_FORMAT_Y_M_D = "yyyy年M月d日";
    public static final String DATE_FORMAT_YM_D = "yyyy年M月_d";
    public static final String DATE_FORMAT_YY = "yy";
    public static final String DATE_FORMAT_YYYY = "yyyy";
    public static final int FMT_DATE_YYYY = 0;
    public static final int FMT_DATE_YYYYMMDD = 1;
    public static final int FMT_DATE_YYYYMMDD_HHMMSS = 2;
    public static final int FMT_DATE_HHMMSS = 3;
    public static final int FMT_DATE_HHMM = 4;
    public static final int FMT_DATE_SPECIAL = 5;
    /**
     * 日期转换格式：MM-dd
     */
    public static final int FMT_DATE_MMDD = 6;
    public static final int FMT_DATE_YYYYMMDDHHMM = 7;
    public static final int FMT_DATE_MMDD_HHMM = 8;
    public static final int FMT_DATE_MMMDDD = 9;
    public static final int FMT_DATE_YYYYMMDDHHMM_NEW = 10;
    public static final int FMT_DATE_YYYY_MM_DD = 11;
    public static final int FMT_DATE_YYYYMMDDHHMMSS = 12;
    public static final int FMT_DATE_YYMMDD = 13;
    public static final int FMT_DATE_YYMMDDHH = 14;
    public static final int FMT_DATE_MMDD_HHMM_CH = 15;
    public static final int FMT_DATE_MMdd = 16;
    public static final int FMT_DATE_YYYYMMDD_HHSS = 17;
    public static final int FMT_DATE_MMDD_HHMMSS = 18;
    public static final int FMT_DATE_YYYYMMDD_DOT = 19; //add by luming 2013.09.04
    public static final int FMT_DATE_MMDD_HH_CH = 20;
    /**
     * 静态常量值 用于获取 某一个日期的 年 月 日 时 分 秒 标识
     **/
    public static final int GET_TIME_OF_YEAR = 100;// 获得 日期的年份
    public static final int GET_TIME_OF_MONTH = 200;// 获得 日期的月份
    public static final int GET_TIME_OF_DAY = 300;// 获取 日期的天
    public static final int GET_TIME_IF_HOUR = 400;// 获取日期的小时
    public static final int GET_TIME_OF_MINUTE = 500;
    public static final int GET_TIME_OF_SECOND = 600;
    public static String[] formatTab;

    public static final String HH_EN = "小时";
    public static final String MM_EN = "分钟";
    public static final String SS_EN = "秒";

    static {
        formatTab = new String[21];
        formatTab[FMT_DATE_YYYY] = "yyyy";
        formatTab[FMT_DATE_YYYYMMDD] = "yyyy-MM-dd";
        formatTab[FMT_DATE_YYYYMMDD_HHMMSS] = "yyyy-MM-dd HH:mm:ss";
        formatTab[FMT_DATE_HHMMSS] = "HH:mm:ss";
        formatTab[FMT_DATE_HHMM] = "HH:mm";
        formatTab[FMT_DATE_SPECIAL] = "yyyyMMdd";
        formatTab[FMT_DATE_MMDD] = "MM-dd";
        formatTab[FMT_DATE_YYYYMMDDHHMM] = "yyyy-MM-dd HH:mm";
        formatTab[FMT_DATE_MMDD_HHMM] = "MM-dd HH:mm";
        formatTab[FMT_DATE_MMMDDD] = "MM月dd日";
        formatTab[FMT_DATE_YYYYMMDDHHMM_NEW] = "yyyyMMddHHmm";
        formatTab[FMT_DATE_YYYY_MM_DD] = "yyyy年MM月dd日";
        formatTab[FMT_DATE_YYYYMMDDHHMMSS] = "yyyyMMddHHmmss";
        formatTab[FMT_DATE_YYMMDD] = "yyMMdd";
        formatTab[FMT_DATE_YYMMDDHH] = "yyyyMMddHH";
        formatTab[FMT_DATE_MMDD_HHMM_CH] = "MM月dd日HH时mm分";
        formatTab[FMT_DATE_MMDD_HH_CH] = "MM月dd日HH点";
        formatTab[FMT_DATE_MMdd] = "MMdd";
    }

    /**
     * 获得一个date 类型的 某个 特殊的 内容
     * <p>
     * 比如 返回 时间的 年 、月、日、时、分、秒
     *
     * @param date
     * @param flag
     * @return
     */
    public static String getPartTime(Date date, int flag) {
        if (date == null) {
            return "";
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int result = 0;
        switch (flag) {
            case GET_TIME_OF_YEAR:
                result = cal.get(Calendar.YEAR);
                break;

            case GET_TIME_OF_MONTH:
                result = cal.get(Calendar.MONTH) + 1;
                break;

            case GET_TIME_OF_DAY:
                result = cal.get(Calendar.DAY_OF_MONTH);
                break;

            case GET_TIME_IF_HOUR:
                result = cal.get(Calendar.HOUR_OF_DAY);
                break;

            case GET_TIME_OF_MINUTE:
                result = cal.get(Calendar.MINUTE);
                break;

            case GET_TIME_OF_SECOND:
                result = cal.get(Calendar.SECOND);
                break;

            default:// 注意默认返回一个时间的年份
                result = cal.get(Calendar.YEAR);
                break;

        }
        return String.valueOf(result);
    }

    public static String formatNowTime(int nFmt) {
        Calendar cal = Calendar.getInstance();

        return formatDate(cal.getTime(), nFmt);
    }

    public static String formatDate(Date date, int nFmt) {
        SimpleDateFormat fmtDate = new SimpleDateFormat();
        switch (nFmt) {
            case FMT_DATE_YYYY:
                fmtDate.applyLocalizedPattern("yyyy");
                break;
            case FMT_DATE_YYYYMMDD:
                fmtDate.applyPattern("yyyy-MM-dd");
                break;
            case FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate.applyPattern("yyyy-MM-dd HH:mm:ss");
                break;
            case FMT_DATE_HHMM:
                fmtDate.applyPattern("HH:mm");
                break;
            case FMT_DATE_HHMMSS:
                fmtDate.applyPattern("HH:mm:ss");
                break;
            case FMT_DATE_SPECIAL:
                fmtDate.applyPattern("yyyyMMdd");
                break;
            case FMT_DATE_MMDD:
                fmtDate.applyPattern("MM-dd");
                break;
            case FMT_DATE_MMdd:
                fmtDate.applyPattern("MMdd");
                break;
            case FMT_DATE_YYYYMMDDHHMM:
                fmtDate.applyPattern("yyyy-MM-dd HH:mm");
                break;
            case FMT_DATE_MMDD_HHMM:
                fmtDate.applyPattern("MM-dd HH:mm");
                break;
            case FMT_DATE_MMMDDD:
                fmtDate.applyPattern("MM月dd日");
                break;
            case DateUtil.FMT_DATE_YYYYMMDDHHMM_NEW:
                fmtDate.applyPattern("yyyyMMddHHmm");
                break;
            case DateUtil.FMT_DATE_YYYY_MM_DD:
                fmtDate.applyPattern("yyyy年MM月dd日");
                break;
            case DateUtil.FMT_DATE_YYYYMMDDHHMMSS:
                fmtDate.applyPattern("yyyyMMddHHmmss");
                break;
            case FMT_DATE_YYMMDD:
                fmtDate.applyPattern("yyMMdd");
                break;
            case FMT_DATE_YYMMDDHH:
                fmtDate.applyPattern("yyyyMMddHH");
                break;
            case FMT_DATE_MMDD_HHMM_CH:
                fmtDate.applyPattern("MM月dd日HH时mm分");
                break;
            case FMT_DATE_MMDD_HH_CH:
                fmtDate.applyPattern("MM月dd日HH点");
                break;
            case DateUtil.FMT_DATE_YYYYMMDD_HHSS:
                fmtDate.applyPattern("yyyy年MM月dd日HH时mm分");
                break;
            case DateUtil.FMT_DATE_MMDD_HHMMSS:
                fmtDate.applyPattern("MM-dd HH:mm:ss");
                break;
            case DateUtil.FMT_DATE_YYYYMMDD_DOT:
                fmtDate.applyPattern("yyyy.MM.dd");
                break;
        }
        return fmtDate.format(date);
    }

    public static Timestamp parseUtilDate(String strDate, int nFmtDate) {
        if (strDate == null || strDate.trim().length() == 0)
            return null;
        SimpleDateFormat fmtDate = null;
        switch (nFmtDate) {
            case DateUtil.FMT_DATE_YYYYMMDD:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case DateUtil.FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case DateUtil.FMT_DATE_HHMM:
                fmtDate = new SimpleDateFormat("HH:mm");
                break;
            case DateUtil.FMT_DATE_HHMMSS:
                fmtDate = new SimpleDateFormat("HH:mm:ss");
                break;
            case DateUtil.FMT_DATE_YYYYMMDDHHMMSS:
                fmtDate = new SimpleDateFormat("yyyyMMddHHmmss");
                break;
            case DateUtil.FMT_DATE_YYYYMMDDHHMM:
                fmtDate = new SimpleDateFormat("yyyyMMddHHmm");
                break;
        }
        try {
            return new Timestamp(fmtDate.parse(strDate).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getToday(Timestamp ts) {
        try {
            String dateStr = formatDate(ts, FMT_DATE_YYYYMMDD);
            SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
            return new Timestamp(fmtDate.parse(dateStr).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获得当前时间的n天前或后
     *
     * @param origin
     * @param intervals
     * @return
     */
    public static Date getIntervalDate(Date origin, long intervals) {
        return new Date(origin.getTime() + intervals * 86400000L);
    }

    /**
     * 获得当前时间的n秒前或后
     *
     * @param origin
     * @param seconds
     * @return
     */
    public static Timestamp getIntervalSeconds(Date origin, long seconds) {
        return new Timestamp(origin.getTime() + seconds * 1000L);
    }

    /**
     * 获得指定时间间隔的timestamp
     *
     * @param ts
     * @param minutes
     * @return
     */
    public static Timestamp getIntervalMinutes(Timestamp ts, int minutes) {
        if (minutes == 0) {
            return ts;
        }
        return new Timestamp(ts.getTime() + minutes * 60 * 1000L);
    }

    public static Timestamp getIntervalDays(Timestamp ts, long days) {
        return getIntervalSeconds(ts, days * 86400L);
    }

    /**
     * 获得两个时间之间的天数
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDiffDays(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;//每天毫秒数
        long nh = 1000 * 60 * 60;//每小时毫秒数
        long diff = endDate.getTime() - nowDate.getTime(); // 获得两个时间的毫秒时间差异
        long day = diff / nd;   // 计算差多少天
        return day;
    }

    /**
     * 获得两个时间之间的天数
     *
     * @param endDate
     *
     *
     * @param nowDate
     * @return
     */
    public static long getRealDiffDays(Date endDate, Date nowDate) {
        long nd = 1000 * 3600 * 24;
        long day = endDate.getTime()/nd - nowDate.getTime()/nd;
        return day;
    }

    /**
     * 获得两个时间之内的毫秒数
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDiffMsecs(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;//每天毫秒数
        long nh = 1000 * 60 * 60;//每小时毫秒数
        long nm = 1000 * 60;//每分钟毫秒数
        long ns = 1000;//每秒钟毫秒数
        long diff = endDate.getTime() - nowDate.getTime(); // 获得两个时间的毫秒时间差异
        long sec = diff % nd % nh % nm / ns ;  // 计算差多少毫秒
        return sec;
    }

    public static boolean isBetween(Date date, Date start, Date end) {
        boolean isBetween = date.compareTo(start) > 0 && date.getTime() <= end.getTime();
        return isBetween;
    }

    /**
     * @param firstDate
     * @param nextDate
     * @return
     * 比较两个时间，if: firstDate before nextDate return true;
     * else: return true;
     */
    public static boolean compareDate(Date firstDate, Date nextDate) {
        if (firstDate.before(nextDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将字符串格式化为日期对象
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Timestamp formatToTimestamp(String dateStr, String format) {
        try {
            SimpleDateFormat sorceFmt = new SimpleDateFormat(format);
            return new Timestamp(sorceFmt.parse(dateStr).getTime()); // 一天的时间24*3600*1000
        } catch (ParseException e) {
            log.warn("invalid date2Get :" + dateStr);
            return null;
        }
    }
    /**
     * 计算两个时间相差时间
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;//每天毫秒数
        long nh = 1000 * 60 * 60;//每小时毫秒数
        long nm = 1000 * 60;//每分钟毫秒数
        long ns = 1000;//每秒钟毫秒数
        long diff = endDate.getTime() - nowDate.getTime(); // 获得两个时间的毫秒时间差异
        long day = diff / nd;   // 计算差多少天
        long hour = diff % nd / nh; // 计算差多少小时
        long min = diff % nd % nh / nm;  // 计算差多少分钟
        long sec = diff % nd % nh % nm / ns ;  // 计算差多少毫秒
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒钟";
    }
}
