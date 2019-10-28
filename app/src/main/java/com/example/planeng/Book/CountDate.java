package com.example.planeng.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CountDate {

    public static Date DateDemo(String a) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Date date = null;
        try {
            date = format.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String DateToString(Date a) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(a);
        return date;
    }

    public static Date DatePlus(Date a) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        return date;
    }
    public static Date DateM(Date a) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        return date;
    }
    public static Date DatePlusInt(Date a,Integer i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.DAY_OF_MONTH, i);
        Date date = cal.getTime();
        return date;
    }




}
