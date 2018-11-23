package com.ymt.sgr.kitchen.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 沈国荣 on 2018/6/21.
 * QQ:514721857
 * Description:
 */

public class OrderStatus {
//    type：0到店自取，1表示外卖送

//    status：
//0新订单/待付款；
//1客服设置为确认付款/待制作；
//2厨房开始制作/制作中；
//3厨房完成制作/待配送(type=0时显示待领取)
//4配送员开始配送/配送中(type=0时没有这步)
//5配送员完成配送/订单完成；
//-1取消；

    public static String getStatusName(int tppe,int status){
        if(tppe==1){//外卖
            if(status==1){//待制作
                return "开始制作";
            }else if(status==2){//制作中
                return "完成制作";
            }else if(status==3){//待配送
                return "待配送";
            }else if(status==4){//派送中
                return "派送中";
            }else if(status==5){//订单完成
                return "订单完成";
            }else if(status==-1){//
                return "已取消";
            }
        }else { //自取
          if(status==1){////待制作
                return "开始制作";
            }else if(status==2){//制作中
                return "完成制作";
            }else if(status==3){//待取
                return "确认领取";
            }else if(status==4){//确认领取
                return "完成订单";
            }else if(status==-1){//已取消
               return "已取消";
          }
        }

        return "";

    }


    public static String TimeFormat(String dateStr) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date result;
        result = df.parse(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return sdf.format(result);

    }

    //时间差  第一个是时间小的
    public static String timeSubtraction(String time1, String time2) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date begin = dfs.parse(time1);

        Date end = dfs.parse(time2);

        long between = (end.getTime() - begin.getTime())/1000;

        long min = between/60;

        return String.valueOf(min);
    }
}
