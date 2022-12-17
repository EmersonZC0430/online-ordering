package com.my.woelegobuy.utils;

import android.annotation.SuppressLint;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtils {

    /**
     * 根据指定时间按照指定格式转换
     *
     * @param time
     * @param format
     * @return
     */
    public static String timeFormat(long time, String format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdr.format(new Date(time ));
    }

    public static long timeToStamp(String time, String format) {
        Date d = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.US);
        try {
            d = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d != null ? d.getTime() : 0;
    }


    /**
     * 计算间隔日
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDaysBetween(long start, long end) {
        int daysBetween;
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy/mm/dd");
//        Date d1 = sf.parse(getTimestampFormatYMD(start));
//        Date d2 = sf.parse(getTimestampFormatYMD(end));

//        assert d2 != null;
//        assert d1 != null;
//
//        Logger.e("-*-*-*-*-*-*-*- d2.getTime: " + d2.getTime());
//        Logger.e("-*-*-*-*-*-*-*- d1.getTime: " + d1.getTime());

//        daysBetween = (int)(d2.getTime() - d1.getTime() + 1000000) / (60 * 60 * 24 * 1000);

        daysBetween = ((int) (end - start)) / (60 * 60 * 24 * 1000);

        return daysBetween;
    }

    public static long getZeroTimeOfDay(long timestamp) {
        long zero;
        zero = timestamp / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//零点零分零秒的毫秒数
        return zero;
    }

    public static long getTwelveTimeOfDay(long timestamp) {
        long twelve;
        twelve = getZeroTimeOfDay(timestamp) + 24 * 60 * 60 * 1000 - 1000;//今天23点59分59秒的毫秒数
        return twelve;
    }

    public static long getDaysBeforeOrAfter(int days) {
        long result;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        result = c.getTime().getTime();
        return result;
    }

    /**
     * 获取下个月 yyyy/MM/xx
     *
     * @param timestamp
     * @return
     */
    public static String getNextMonthLastDayYMDStr(long timestamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM");
        Date date = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        date = c.getTime();
        return sf.format(date) + "/" + getDaysOfMonth(Integer.parseInt(getFormatMonth(date.getTime())));
    }

    /**
     * 获取上个月 yyyy/MM/01
     *
     * @param timestamp
     * @return
     */
    public static String getLastMonthFirstDayYMDStr(long timestamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM");
        Date date = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        date = c.getTime();
        return sf.format(date) + "/01";
    }

    /**
     * 获取某天是本月第几个周 N
     *
     * @param timestamp
     * @return
     */
    public static int getDayOfWeekInMonth(long timestamp) {
        Date d = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 获取某天是本月第几周
     *
     * @param timestamp
     * @return
     */
    public static int getWeekOfMonth(long timestamp) {
        Date d = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取某天是周几
     *
     * @param timestamp
     * @return
     */
    public static int getDayOfWeek(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取某月有多少天
     *
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int month) {
        Calendar c = Calendar.getInstance();
        c.set(2020, month, 0);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据 格式化日期时间 获取时间戳
     *
     * @return
     */
    public static long getTimestampBasedOnFormat(String format) {
        Date d = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        try {
            d = sf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d != null ? d.getTime() : 0;
    }

    /**
     * 获取格式化 minStr
     *
     * @param timestamp
     * @return
     */
    public static String getFormatMinute(long timestamp) {
        String d = getTimestampFormat(timestamp);
        return d.substring(14, 16);
    }

    /**
     * 获取格式化 hourStr
     *
     * @param timestamp
     * @return
     */
    public static String getFormatHour(long timestamp) {
        String d = getTimestampFormat(timestamp);
        return d.substring(11, 13);
    }

    /**
     * 获取格式化 dateStr
     *
     * @param timestamp
     * @return
     */
    public static String getFormatDate(long timestamp) {
        String d = getTimestampFormat(timestamp);
        return d.substring(8, 10);
    }

    /**
     * 获取格式化 monthStr
     *
     * @param timestamp
     * @return
     */
    public static String getFormatMonth(long timestamp) {
        String d = getTimestampFormat(timestamp);
        return d.substring(5, 7);
    }

    /**
     * 获取格式化 yearStr
     *
     * @param timestamp
     * @return
     */
    public static String getFormatYear(long timestamp) {
        String d = getTimestampFormat(timestamp);
        return d.substring(0, 4);
    }

    /**
     * 格式化时间戳 yyyy/MM/dd HH:mm
     *
     * @param timestamp
     * @return
     */
    public static String getTimestampFormat(long timestamp) {
        Date d = new Date(timestamp);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return simpleDateFormat.format(d);
    }

    /**
     * 格式化时间戳 yyyy/MM/dd
     *
     * @param timestamp
     * @return
     */
    public static String getTimestampFormatYMD(long timestamp) {
        Date d = new Date(timestamp);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(d);
    }

    /**
     * 获取月份 Jan / Feb ...
     *
     * @param month
     * @return
     */
    public static String getMonthStr(int month) {
        String monthStr = "";
        switch (month) {
            case 0:
                monthStr = "Jan";
                break;
            case 1:
                monthStr = "Feb";
                break;
            case 2:
                monthStr = "Mar";
                break;
            case 3:
                monthStr = "Apr";
                break;
            case 4:
                monthStr = "May";
                break;
            case 5:
                monthStr = "Jun";
                break;
            case 6:
                monthStr = "Jul";
                break;
            case 7:
                monthStr = "Aug";
                break;
            case 8:
                monthStr = "Sep";
                break;
            case 9:
                monthStr = "Oct";
                break;
            case 10:
                monthStr = "Nov";
                break;
            case 11:
                monthStr = "Dec";
                break;
        }
        return monthStr;
    }

    /**
     * 获取月份 January / February ...
     *
     * @param month
     * @return
     */
    public static String getFullMonthStr(int month) {
        String monthStr = "";
        switch (month) {
            case 0:
                monthStr = "January";
                break;
            case 1:
                monthStr = "February";
                break;
            case 2:
                monthStr = "March";
                break;
            case 3:
                monthStr = "April";
                break;
            case 4:
                monthStr = "May";
                break;
            case 5:
                monthStr = "June";
                break;
            case 6:
                monthStr = "July";
                break;
            case 7:
                monthStr = "August";
                break;
            case 8:
                monthStr = "September";
                break;
            case 9:
                monthStr = "October";
                break;
            case 10:
                monthStr = "November";
                break;
            case 11:
                monthStr = "December";
                break;
        }
        return monthStr;
    }

    /**
     * 获取时间 hh:mm aa
     *
     * @param timestamp
     * @return
     */
    public static String getTime(Long timestamp) {
        Date date = new Date(timestamp);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        return dateFormat.format(date).toLowerCase();
    }

    /**
     * 根据 时间戳 获取该时间点的 信息 (calendar)
     *
     * @param timestamp
     * @return
     */
    public static Calendar getCalendarOfTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 根据 时间戳 获取该时间点 距离当前时间点 的 时间间隔
     * eg. 5s ago, 1hr ago (s / min, mins / hr, hrs / dy, ds / wk, wks / mth, mos)
     *
     * @param timestamp
     * @return
     */
    public static String getStrOfTimeTillNow(String timestamp) {
        String str = "";

        long timestampBefore = Long.parseLong(timestamp + "000");
        long timestampNow = Long.parseLong(System.currentTimeMillis() + "");
//        long timestampBefore = Long.parseLong(timestamp);
//        long timestampNow = Long.parseLong(System.currentTimeMillis() / 1000 + "");
        long duration = (timestampNow - timestampBefore) / 1000;

        Calendar cBefore = getCalendarOfTimestamp(timestampBefore);
        Calendar cNow = getCalendarOfTimestamp(timestampNow);
        int yearBefore = cBefore.get(Calendar.YEAR);
        int yearNow = cNow.get(Calendar.YEAR);
        int monthBefore = cBefore.get(Calendar.MONTH);
        int monthNow = cNow.get(Calendar.MONTH);
        int dateBefore = cBefore.get(Calendar.DATE);
        int dateNow = cNow.get(Calendar.DATE);

        int temp = 0;

//        Logger.e("-**-*-*-*-*-*-*- format time");

        if (yearBefore == yearNow) {
//            Logger.e("-**-*-*-*-*-*-*- same year: " + yearNow);
            if (monthBefore == monthNow) {
//                Logger.e("-**-*-*-*-*-*-*- same month: " + monthNow);
                if (dateBefore == dateNow) {
//                    Logger.e("-**-*-*-*-*-*-*- same day: " + dateNow);
                    if (duration < 60) {
                        str = "Just";
                    } else if (duration < 60 * 60) {
                        if (duration == 60) {
                            str = "1min ago";
                        } else {
                            str = (duration / 60) + "mins ago";
                        }
                    } else if (duration < 60 * 60 * 24) {
                        if (duration == 60 * 60) {
                            str = "1hr ago";
                        } else {
                            str = (duration / 60 / 60) + "hrs ago";
                        }
                    }
                } else {
//                    Logger.e("-**-*-*-*-*-*-*- different day: " + dateBefore + " / " + dateNow);
                    temp = dateNow - dateBefore;
                    str = temp > 1 ? ((temp % 7) == 0 ? ((temp / 7) > 1 ? (temp + " wks ago") : ("1 wk ago")) : (temp + " days ago")) : ("1 day ago");
                }
            } else {
//                Logger.e("-**-*-*-*-*-*-*- different month: " + monthBefore + " / " + monthNow);
                temp = monthNow - monthBefore;
                str = temp > 1 ? ("mths ago") : ("mth ago");
            }
        } else {
//            Logger.e("-**-*-*-*-*-*-*- different year: " + yearBefore + " / " + yearNow);
            temp = yearNow - yearBefore;
            str = temp > 1 ? ("years ago") : ("year ago");
        }
        str += timeFormat((int) (timestampBefore / 1000), "- MMM d");
        return str;
    }

    /**
     * 根据时间戳获取日期时间信息
     * dd mm yy, hh:mm ap
     *
     * @param timestampSecond
     * @return
     */
    public static String getDateMonthYearAndTime(String timestampSecond) {
        String str = "";
        long timestamp = Long.parseLong(timestampSecond + "000");
        Calendar c = getCalendarOfTimestamp(timestamp);
        str = c.get(Calendar.DATE) + " " + getMonthStr(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + ", " + getTime(timestamp);
        return str;
    }

    /**
     * 根据时间戳获取time的月份
     */
    public static int getMonthOfYear(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 当前时间戳加X个月
     *
     * @param time
     * @param addCount
     * @return
     */
    public static long addMonth(long time, int addCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(Calendar.MONDAY, addCount);
        return cal.getTime().getTime();
    }

    /**
     * 当前时间戳加X天
     *
     * @param time
     * @param addCount
     * @return
     */
    public static long addDay(long time, int addCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(Calendar.DATE, addCount);
        return cal.getTime().getTime();
    }


    /**
     * 当前时间戳加X分钟
     *
     * @param time
     * @param addCount
     * @return
     */
    public static long addMinute(long time, int addCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(Calendar.MINUTE, addCount);
        return cal.getTime().getTime();
    }

    /**
     * 根据时间戳获取当前月有多少天
     */
    public static int getDaysOfMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据时间戳获取当前月第一天
     *
     * @return
     */
    public static long getMonthFirstDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        return calendar.getTime().getTime();
    }

    /**
     * 根据时间戳获取当前月最后一天
     *
     * @return
     */
    public static long getMonthLastDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime().getTime();
    }

    /**
     * 根据时间戳获取下一个月的起始时间戳
     *
     * @param time
     * @return
     */
    public static long getNextMonthStartTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime().getTime();
    }

    /**
     * 根据时间戳获取上一个月的起始时间戳
     *
     * @param time
     * @return
     */
    public static long getLastMonthStartTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime().getTime();
    }


    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }



    /**
     * 获取当前日期的起始时间
     *
     * @return
     */
    public static Calendar getCurrentMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }


    public static Calendar getStartDay(int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set( 0, 0, 0);
        return calendar;
    }
    public static Calendar getEndDay(int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        calendar.set(Calendar.AM_PM, Calendar.PM);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
//        calendar.set( 0, 0, 0);
        return calendar;
    }

    public static Integer getStartTimeDiffWithLocalTime(int startTimestamp, int showTimestamp) {
        if (startTimestamp <= 0 || showTimestamp <= 0) {
            return 0;
        }

        TimeZone timeZone = TimeZone.getDefault();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startTimestamp * 1000L);
        Date startDate = startCalendar.getTime();
        Calendar showCalendar = Calendar.getInstance();
        showCalendar.setTimeInMillis(showTimestamp * 1000L);
        Date showDate = showCalendar.getTime();
        if (timeZone.inDaylightTime(startDate)) {
            if (timeZone.inDaylightTime(showDate)){
                return 0;
            }else {
                return 1;
            }
        }else {
            if (timeZone.inDaylightTime(showDate)){
                return -1;
            }else {
                return 0;
            }
        }
    }

}
