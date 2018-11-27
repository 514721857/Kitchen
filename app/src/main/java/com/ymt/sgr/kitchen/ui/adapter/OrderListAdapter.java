package com.ymt.sgr.kitchen.ui.adapter;



import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ymt.sgr.kitchen.R;
import com.ymt.sgr.kitchen.model.OrderBean;
import com.ymt.sgr.kitchen.util.OrderStatus;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */




public class OrderListAdapter extends BaseQuickAdapter<OrderBean,BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener{
  Context context;
    public OrderListAdapter(Context contexts) {
        super(R.layout.layout_item_order, null);
        context=contexts;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderBean personItem) {

        baseViewHolder.setText(R.id.order_list_num,personItem.getTodaySeq()+" ");
        baseViewHolder.setText(R.id.order_list_name,personItem.getUsername());
        baseViewHolder.setText(R.id.order_list_phone,personItem.getPhone());
        baseViewHolder.setText(R.id.order_list_address,personItem.getAddress());
        baseViewHolder.setText(R.id.order_list_list,personItem.getDetail());
        baseViewHolder.setText(R.id.order_list_song,"配送："+personItem.getExpressFee());
        baseViewHolder.setText(R.id.order_list_can,"  餐费"+personItem.getAmount());
        baseViewHolder.setText(R.id.order_list_total,"  合计"+personItem.getTotal());
        baseViewHolder.setText(R.id.order_list_zt,  OrderStatus.getStatusName(personItem.getType(),personItem.getStatus()));
        if(personItem.getSummary()!=null&&!personItem.getSummary().equals("")){
            baseViewHolder.getView(R.id.order_list_bz).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.order_list_bz,"备注："+personItem.getSummary());
        }else{
            baseViewHolder.getView(R.id.order_list_bz).setVisibility(View.GONE);
        }


        try {
            baseViewHolder.setText(R.id.order_list_time,"下单："+OrderStatus.TimeFormat(personItem.getGmtCreate()));
            baseViewHolder.setText(R.id.order_send_time,"送达："+OrderStatus.TimeFormat(personItem.getSendTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        baseViewHolder.addOnClickListener(R.id.order_btn_phone);
        baseViewHolder.addOnClickListener(R.id.order_list_zt);
       /* new DateTime(new Date()).toDateTime(DateTimeZone.UTC).toDateTimeString("yyyy年MM月dd日 hh时mm分ss秒");
        DateTime.ParseExact("2013-11-17T11:59:22+08:00","yyyy-MM-ddTHH:mm:ss+08:00",null)*/



    }




    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}