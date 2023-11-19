package com.example.zhidao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
