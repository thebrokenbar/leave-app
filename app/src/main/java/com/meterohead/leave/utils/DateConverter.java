package com.meterohead.leave.utils;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by wierzchanowskig on 24.09.2016.
 */

public class DateConverter {
    private final static GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());

    public static Date getDate(int year, int month, int day) {
        calendar.set(year, month, day);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        calendar.set(year, month, day, hour, minute, second);
        return new Date(calendar.getTimeInMillis());
    }

    public static Calendar getAsCalendar(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c;
    }
}
