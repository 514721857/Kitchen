package com.ymt.sgr.kitchen.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ymt.sgr.kitchen.config.AppCon;
import com.ymt.sgr.kitchen.http.HttpService;
import com.ymt.sgr.kitchen.http.HttpUtils;


import io.reactivex.Observable;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;


/**
 * 作者: Dream on 16/9/24 21:09
 * QQ:510278658
 * E-mail:510278658@qq.com
 */
public class CommonModel extends BaseModel {
    String userID;
    String token;
    SharedPreferences pref ;
    int shopId;;
    public CommonModel(Context context) {
        super(context);
        pref = context.getSharedPreferences(AppCon.USER_KEY,MODE_PRIVATE);
        token= pref.getString(AppCon.SCCESS_TOKEN_KEY,"");
        shopId=pref.getInt(AppCon.USER_SHOP_ID,0);
        userID= pref.getString(AppCon.USER_USER_ID,"");
    }

    /**
     * 测试
     *
     * @param onLceHttpResultListener
     */
    public void getSave(int  status,int page, final HttpUtils.OnHttpResultListener onLceHttpResultListener){

        RequestOrder order=new RequestOrder();
        order.setPageSize(10);
        order.setCurrPage(page);
        order.setStatus(status);
        order.setShopId(shopId);
        Gson gson=new Gson();
        String obj=gson.toJson(order);
        System.out.println("请求参数"+obj);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        HttpService essenceService= buildService(HttpService.class);
        buildObserve((Observable)essenceService.getSave(token,body),onLceHttpResultListener);

    }

    /**
     * 登录
     *
     * @param onLceHttpResultListener
     */
    public void getLogin(User info, final HttpUtils.OnHttpResultListener onLceHttpResultListener) {
        Gson gson=new Gson();
        String obj=gson.toJson(info);
        System.out.println("登录"+obj);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        HttpService essenceService= buildService(HttpService.class);
        buildObserve((Observable)essenceService.getLogin(body),onLceHttpResultListener);

    }

    /**
     * 订单列表
     *
     * @param onLceHttpResultListener
     */
    public void getOrderList(int  status,int page,int type, final HttpUtils.OnHttpResultListener onLceHttpResultListener) {
       RequestOrder order=new RequestOrder();
        order.setPageSize(10);
        order.setCurrPage(page);
        order.setStatus(status);
        if(status>1){
            order.setCfId(userID);
        }
        order.setType(type);
        order.setShopId(shopId);
        Gson gson=new Gson();
        String obj=gson.toJson(order);
        System.out.println("请求参数"+obj);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        HttpService essenceService= buildService(HttpService.class);
        buildObserve((Observable)essenceService.getOrderList(token,body),onLceHttpResultListener);

    }


    /**
     * 订单列表
     *
     * @param onLceHttpResultListener
     */
    public void UpdateOrder(OrderBean order, final HttpUtils.OnHttpResultListener onLceHttpResultListener) {
    /*    RequestOrder order=new RequestOrder();
        order.setStatus(status);*/
        Gson gson=new Gson();
        String obj=gson.toJson(order);
        System.out.println("请求参数"+obj);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        HttpService essenceService= buildService(HttpService.class);
        buildObserve((Observable)essenceService.UpdateOrder(token,body),onLceHttpResultListener);

    }

    /**
     * 获取一级地址
     *
     * @param onLceHttpResultListener
     */
    public void getAddress( final HttpUtils.OnHttpResultListener onLceHttpResultListener) {

        HttpService essenceService= buildService(HttpService.class);
        buildObserve((Observable)essenceService.getAddress1(),onLceHttpResultListener);

    }

}
