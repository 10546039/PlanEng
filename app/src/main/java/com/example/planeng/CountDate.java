package com.example.planeng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        /**
     * 获取两个日期之间的间隔天数
     * @return
     */




}
