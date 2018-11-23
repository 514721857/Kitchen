package com.ymt.sgr.kitchen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data：2018/11/23/023-16:18
 * By  沈国荣
 * Description:
 */
public class TestDemo {

    public static void main(String[] args) throws ParseException {

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String time1 = dateFormat.format(now);
        System.out.println("当前时间"+time1);
        String time2 = "2018-11-23 16:23:41";
        System.out.println("时间2"+time2);
        String time_out= OrderStatus.timeSubtraction(time2,time1);
        System.out.println("时间差"+time_out);
    }

}
