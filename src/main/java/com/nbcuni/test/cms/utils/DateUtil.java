package com.nbcuni.test.cms.utils;


import com.nbcuni.test.webdriver.Utilities;
import org.testng.TestException;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    public static final String UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String TIME_TILL_MINUTE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate(String format, String timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getDate(Long date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getStringRepresentationForCertainTimeZone(Date date, String format, String timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return dateFormat.format(date);
    }

    public static String getCurrentDateWithoutHour(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = new Date();
        // date.setHours(date.getHours() - 1);
        return dateFormat.format(date);
    }

    public static Date getDateBeforeCurrentByDayNumber(int dayNumber) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        String todate = dateFormat.format(date);
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -dayNumber);
        Date todate1 = cal.getTime();

        return todate1;
    }

    public static String getDateBeforeCurrentByDayNumber(int dayNumber, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -dayNumber);
        Date date = cal.getTime();
        String todate = dateFormat.format(date);
        return todate;
    }

    public static String getDateBeforeCurrentByMonthNumber(int monthNumber, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -monthNumber);
        Date date = cal.getTime();
        String todate = dateFormat.format(date);
        return todate;
    }

    public static String getDateBeforeCurrentByYearNumber(int yearNumber, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -yearNumber);
        Date date = cal.getTime();
        String todate = dateFormat.format(date);
        return todate;
    }

    public static String getDate(int calendar, int number, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(calendar, number);
        Date date = cal.getTime();
        String todate = dateFormat.format(date);
        return todate;
    }

    public static String getDateAfterCurrentByDayNumber(int dayNumber, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +dayNumber);
        Date date = cal.getTime();
        String todate = dateFormat.format(date);

        return todate;
    }

    public static Date getDateAfterCurrentByDayNumber(int dayNumber) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        //getBrandName current date time with Date()
        Date date = new Date();

        String todate = dateFormat.format(date);
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayNumber);
        Date todate1 = cal.getTime();

        return todate1;
    }

    public static String getDateAfterCurrentByMonthNumberString(int monthNumber) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        Format f = new SimpleDateFormat("MM/dd/yyyy");
        date.add(Calendar.MONTH, monthNumber);
        return f.format(date.getTime());
    }

    public static String getCurrentDateAndNumberOfDays(int days) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date m = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(m);
        cal.add(Calendar.DATE, days); // 10 is the days you want to add or subtract   
        m = cal.getTime();
        return dateFormat.format(m);
    }


    public static int getCurrentDay() {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH); // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        return month;
    }

    public static int getCurrentHour() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static int getCurrentSecond() {
        Calendar now = Calendar.getInstance();
        int second = now.get(Calendar.SECOND);
        return second;
    }

    public static int getCurrentMinutes() {
        Calendar now = Calendar.getInstance();
        int minute = now.get(Calendar.MINUTE);
        return minute;
    }

    public static List<String> createScheduleExpectedDate(String dateFormat) {
        List<String> toReturn = new ArrayList<>();
        for (int i = 7; i > 0; i--) {
            toReturn.add(DateUtil.getDateAfterCurrentByDayNumber(i, dateFormat));
        }
        String todayDate = DateUtil.getCurrentDate(dateFormat);
        todayDate = "Today" + todayDate.substring(3);
        toReturn.add(todayDate);
        for (int i = 1; i < 8; i++) {
            toReturn.add(DateUtil.getDateBeforeCurrentByDayNumber(i, dateFormat));
        }
        return toReturn;
    }

    public static Date getDateInEST() {
        Date date = Calendar.getInstance().getTime();
        return getDateInEST(date);
    }

    public static Date getDateInEST(Date date) {
        TimeZone zone = TimeZone.getTimeZone("US/Eastern");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        format.setTimeZone(zone);
        String stringRepresentation = format.format(date);
        try {
            date = format.parse(stringRepresentation);
        } catch (ParseException e) {
            Utilities.logSevereMessage("Error in data parsing");
            throw new TestException(e);
        }
        return date;
    }

    public static Date getDateInCertainTimeZone(TimeZone zone) {
        Date date = Calendar.getInstance().getTime();
        return getDateInCertainTimeZone(zone, date);
    }

    public static Date getDateInCertainTimeZone(TimeZone zone, Date date, String... formats) {
        SimpleDateFormat format = null;
        if (formats.length > 0) {
            format = new SimpleDateFormat(formats[0]);
        } else {
            format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        }
        String stringRepresentation = format.format(date);
        format.setTimeZone(zone);
        try {
            date = format.parse(stringRepresentation);
        } catch (ParseException e) {
            Utilities.logSevereMessage("Error in data parsing");
            throw new TestException(e);
        }
        return date;
    }

    public static String getCurrentDateInUtcString() {
        SimpleDateFormat dateFormatUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUtc.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        return dateFormatUtc.format(date);
    }

    public static Date getCurrentDateInUtc() {
        SimpleDateFormat dateFormatUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUtc.setTimeZone(TimeZone.getTimeZone("UTC"));

        //Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            return dateFormatLocal.parse(dateFormatUtc.format(new Date()));
        } catch (ParseException e) {

            e.printStackTrace();
            throw new RuntimeException("Parse Exception");
        }
    }

    public static Date getCurrentDateInTimeZone(String zone) {
        SimpleDateFormat dateFormatUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUtc.setTimeZone(TimeZone.getTimeZone(zone));
        //Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return dateFormatLocal.parse(dateFormatUtc.format(new Date()));
        } catch (ParseException e) {

            e.printStackTrace();
            throw new RuntimeException("Parse Exception");
        }
    }

    public static Date parseStringToUtcDate(String utcString) {
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return dateFormatLocal.parse(utcString);
        } catch (ParseException e) {

            e.printStackTrace();
            throw new RuntimeException("Parse Exception");
        }
    }

    public static Date parseStringToDateInCertainTimeZone(String dateString, String format, String timeZoneName) {
        TimeZone zone = TimeZone.getTimeZone(timeZoneName);
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat(format);
        dateFormatLocal.setTimeZone(zone);
        try {
            return dateFormatLocal.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse Exception");
        }
    }

    public static Date parseStringToDate(String dateString, String format) {
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat(format);
        try {
            return dateFormatLocal.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Parse Exception");
        }
    }

    public static Long getGmtTimeStampFromEst(Date estDate) {
        TimeZone defaultZone = TimeZone.getDefault();
        TimeZone gmtZone = TimeZone.getTimeZone("Etc/GMT");
        TimeZone estZone = TimeZone.getTimeZone("US/Eastern");
        Calendar cal = Calendar.getInstance();
        TimeZone.setDefault(estZone);
        cal.setTimeZone(estZone);
        cal.setTime(estDate);
        TimeZone.setDefault(gmtZone);
        cal.setTimeZone(gmtZone);
        long timeStamp = cal.getTime().getTime();
        TimeZone.setDefault(defaultZone);
        return timeStamp;
    }

    public static Long getGmtTimeStampFromAnotherTimeZone(TimeZone zone, Date timeZoneDate) {
        TimeZone defaultZone = TimeZone.getDefault();
        TimeZone gmtZone = TimeZone.getTimeZone("Etc/GMT");
        Calendar cal = Calendar.getInstance();
        TimeZone.setDefault(zone);
        cal.setTimeZone(zone);
        cal.setTime(timeZoneDate);
        TimeZone.setDefault(gmtZone);
        cal.setTimeZone(gmtZone);
        long timeStamp = cal.getTime().getTime();
        TimeZone.setDefault(defaultZone);
        return timeStamp;
    }

    public static String getDateStringInEstFromGmtTimeStamp(Long gmtTimestamp, SimpleDateFormat format) {
        TimeZone defaultZone = TimeZone.getDefault();
        TimeZone gmtZone = TimeZone.getTimeZone("Etc/GMT");
        TimeZone estZone = TimeZone.getTimeZone("US/Eastern");
        Calendar cal = Calendar.getInstance();
        TimeZone.setDefault(gmtZone);
        cal.setTimeZone(gmtZone);
        cal.setTimeInMillis(gmtTimestamp);
        TimeZone.setDefault(estZone);
        cal.setTimeZone(estZone);
        Date date = cal.getTime();
        format.setTimeZone(estZone);
        String dateString = format.format(date);
        TimeZone.setDefault(defaultZone);
        return dateString;
    }

    public static Date getDateAfterCurrentByYearNumber(int yearNumber) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, yearNumber);
        return cal.getTime();
    }


    public static String getCurrentDateStringRepresentationInNYtimezone(String formatString) {
        TimeZone estZone = TimeZone.getTimeZone("US/Eastern");
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        format.setTimeZone(estZone);
        return format.format(new Date());
    }

    // Method is used to get a current date in America/New_York time zone
    // in format used in MVPD log.
    @SuppressWarnings("deprecation")
    public static String getTimestampForMvpdLog() {
        SimpleDateFormat isoFormat = new SimpleDateFormat("MM/dd/yyyy '-' HH:mm");
        isoFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = new Date();
        return isoFormat.format(date);
    }
}
