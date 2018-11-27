package com.ymt.sgr.kitchen.ui.order;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ymt.sgr.kitchen.config.BasePresenter;
import com.ymt.sgr.kitchen.http.HttpUtils;
import com.ymt.sgr.kitchen.model.CommonModel;
import com.ymt.sgr.kitchen.model.OrderBean;
import com.ymt.sgr.kitchen.model.OrderRespons;
import com.ymt.sgr.kitchen.model.Result;
import com.ymt.sgr.kitchen.model.oneArea;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Data：2018/1/24/024-10:58
 * By  沈国荣
 * Description:
 */
public class OrderPresenter extends BasePresenter<OrderView> {
    private CommonModel commonModel;
    private Context contexts;
    private SharedPreferences sp ;
    public OrderPresenter(Context context) {
        super(context);
        this.contexts=context;
        this.commonModel = new CommonModel(context);
    }
    public void getOrderList(int status,int page,int type){

/*       commonModel.getSave(status, page, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(Object result) {
                ResponseBody temp=(ResponseBody)result;
                try {

                   System.out.println("订单列表"+temp.string()) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });*/

       commonModel.getOrderList(status, page,type, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(Object result) {
                Result<OrderRespons> temp=(Result<OrderRespons>)result;


                if(temp.status.equals("200")){
                    if(temp.content!=null){
                        getView().showResult(temp.content.getData());
                    }

                }else{
                    getView().showResultOnErr(temp.message);

                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void UpdateOrder(OrderBean order, final int position){
        commonModel.UpdateOrder(order, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(Object result) {
                Result<OrderRespons> temp=(Result<OrderRespons>)result;
                if(temp.status.equals("200")){
                    getView().UpdateSussess(position);
                }else{
                    getView().showResultOnErr(temp.message);

                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    public void getAddress1(){
        commonModel.getAddress(new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(Object result) {
                Result<List<oneArea>> temp=(Result<List<oneArea>>)result;
                if(temp.status.equals("200")){
                    getView().ResultAddress1(temp.content);
                }else{
                    getView().showResultOnErr(temp.message);
                }

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


}
