package com.example.codelabsvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    public static final String ddMMyyyy = "dd/MM/yyyy";
    public static final String ddMMyyyyHHmmSS = "dd/MM/yyyy HH:mm:ss";
    public static final String dd_MM_yyyy_HH_mm_SS = "dd_MM_yyyy_HH_mm_ss";
    public static final String HHmmSSddMMyyyy = "HH:mm:ss dd/MM/yyyy";
    public static final String DATE_TIME_MYSQL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SMS_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String HHmmddMM = "HH:mm dd/MM";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddTHHmmssSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static final String DDMMYYYY = "ddMMyyyy";

    public static String toDateString(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String toDateStringWithTimezone(Date date, String format, String timeZone) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date) + " ("+timeZone+")" ;
    }

    public static String toDateString(Date date, String format, String timeZone) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date) ;
    }

    public static Date getYesterday() {
        return new Date(System.currentTimeMillis() - 86400000);// 1 day = 86400000 ms
    }

    public static Date startTimeOfDate(Date date) {
        return convertStringToDate(toDateString(date, "dd/MM/yyyy"), "dd/MM/yyyy");
    }

    public static Date addDate(int day) {
        long dayTime = (long) day * 86400000;
        return new Date(System.currentTimeMillis() + dayTime);// 1 day = 86400000 ms
    }

    public static Date addDate(Date originDate, int day) {
        long dayTime = (long) day * 86400000;
        return new Date(originDate.getTime() + dayTime);// 1 day = 86400000 ms
    }

    public static int compareDate(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(ddMMyyyyHHmmSS);

        try {
            date1 = sdf.parse(sdf.format(date1));
            date2 = sdf.parse(sdf.format(date2));

            return (int) ((date1.getTime() - date2.getTime()) / 86400000);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
        Date cur = new Date();
        Date startTimeOfDate = startTimeOfDate(cur);
        System.out.println(startTimeOfDate);
    }

    public static Date convertStringToDate(String dateStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean isFirstDayOfTheMonth(Date dateToday) {
        Calendar c = new GregorianCalendar();
        c.setTime(dateToday);

        if (c.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    public static String getDate(String fmOfTime) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat(fmOfTime);
        return ft.format(date);
    }

    public static int compareTime(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(ddMMyyyyHHmmSS);

        try {
            date1 = sdf.parse(sdf.format(date1));
            date2 = sdf.parse(sdf.format(date2));

            return (int) ((date1.getTime() - date2.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
