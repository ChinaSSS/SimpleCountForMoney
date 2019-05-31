package com.example.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getFormatDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getFormatTime(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(timestamp);
    }

}
