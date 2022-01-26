package com.nwpu.gateway.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Mote
 * @Date: 2022/1/17 11:09
 */
public class StringHelper {
    public static String getTokenID(String uid) {
        Date nowTime = new Date(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        String retStrFormatNowDate = sdFormatter.format(nowTime);


        StringBuilder stringBuilder = new StringBuilder(16);
        long totalMilliSeconds = System.currentTimeMillis();
        stringBuilder.append(totalMilliSeconds % 1000);
        long totalSeconds = totalMilliSeconds / 1000;
        //求出现在的分
        long totalMinutes = totalSeconds / 60;
        long currentMinute = totalMinutes % 60;
        stringBuilder.append(currentMinute);
        //求出现在的小时
        long totalHour = totalMinutes / 60;
        long currentHour = totalHour % 24;
        stringBuilder.append(currentHour);
        return uid + stringBuilder.toString();
    }
}
