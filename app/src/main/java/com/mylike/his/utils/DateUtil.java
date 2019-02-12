package com.mylike.his.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getBirthYear(String birthDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(birthDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            Calendar today = Calendar.getInstance();
            return (today.get(Calendar.YEAR)-calendar.get(Calendar.YEAR))+"岁"+" ("+birthDay+")";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }


    }
}
