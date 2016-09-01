package com.example.hasee.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hasee on 2016/8/31.
 */
public class Util {
    public static SimpleDateFormat dateSdf = new SimpleDateFormat("E yyyy年MM月dd日 hh:mm", Locale.CHINA);
    public static SimpleDateFormat timeSdf = new SimpleDateFormat("hh:mm", Locale.CHINA);
    public static int year, month, day, hour, minuteOfHour;

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minuteOfHour = calendar.get(Calendar.MINUTE);
        return calendar;
    }
}
