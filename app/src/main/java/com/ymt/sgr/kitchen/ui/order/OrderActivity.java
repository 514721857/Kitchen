package com.ymt.sgr.kitchen.ui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.google.gson.Gson;
import com.ymt.sgr.kitchen.R;
import com.ymt.sgr.kitchen.config.AppCon;
import com.ymt.sgr.kitchen.config.BaseMvpActivity;
import com.ymt.sgr.kitchen.http.HttpUtils;
import com.ymt.sgr.kitchen.model.CommonModel;
import com.ymt.sgr.kitchen.model.OrderBean;
import com.ymt.sgr.kitchen.model.oneArea;
import com.ymt.sgr.kitchen.ui.LoginActivity;
import com.ymt.sgr.kitchen.ui.adapter.ConstellationAdapter;
import com.ymt.sgr.kitchen.ui.adapter.GirdDropDownAdapter;
import com.ymt.sgr.kitchen.ui.adapter.ListDropDownAdapter;
import com.ymt.sgr.kitchen.ui.adapter.OrderListAdapter;
import com.ymt.sgr.kitchen.util.StartActivityUtil;
import com.ymt.sgr.kitchen.view.MyDialog;
import com.yyydjk.library.DropDownMenu;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class OrderActivity extends BaseMvpActivity<OrderView,OrderPresenter> implements OrderView ,OnItemChildClickListener{

    RecyclerView mRecyclerView;


    SwipeRefreshLayout mSwipeRefreshLayout;

    private OrderListAdapter mAdapter;

    private int mNextRequestPage = 0;
    private static final int PAGE_SIZE = 10;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    String address;
    private int status=2;

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;


    private String headers[] = {"地址", "待配送"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    List<oneArea> oneAreas;
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
//    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = { "待配送", "已完成","已取消"};
    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    private int constellationPosition = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }
    @OnClick({R.id.result,R.id.top_view_right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.result:


                new CommonModel(this).getSave(new HttpUtils.OnHttpResultListener() {
                    @Override
                    public void onResult(Object result) {
                        ResponseBody tempBody = (ResponseBody)result;
                        String tempResult= null;
                        try {
                            tempResult = tempBody.string().toString();
                            Toast.makeText(OrderActivity.this,tempResult,Toast.LENGTH_SHORT).show();
                            Log.d("tempResult",tempResult);
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
                });
                break;
                case R.id.top_view_right_text:
                    pref = this.getSharedPreferences(AppCon.USER_KEY,MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putString(AppCon.SCCESS_TOKEN_KEY,"");
                    editor.commit();
                    StartActivityUtil.skipAnotherActivity(this, LoginActivity.class);
                    break;

        }
    }

    @Override
    protected void initView() {
        super.initView();

        getPresenter().getAddress1();
        //init city menu

    }


    private void initMenu(){
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, oneAreas);
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);


 /*       //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);*/

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

/*        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });*/

        //init popupViews
        popupViews.add(cityView);
//        popupViews.add(ageView);
        popupViews.add(sexView);

/*
        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });*/

/*        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });*/

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText( oneAreas.get(position).getAddroneArea());
                mDropDownMenu.closeMenu();
                address=oneAreas.get(position).getAddroneArea();
                refresh();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText( sexs[position]);
                mDropDownMenu.closeMenu();
                if(position==0){
                    status=2;//待配送
                }else if(position==1){
                    status=4;//完成配送
                }else if(position==2){
                    status=-1;
                }else{

                }
                refresh();
            }
        });


        //init context view
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_list, null);
        mRecyclerView=view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeLayout);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view);






        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mAdapter = new OrderListAdapter(this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        refresh();
    }


    private void loadMore() {
        getPresenter().getOrderList(status,mNextRequestPage,address);
    }

    private void refresh() {
        mNextRequestPage = 0;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
       getPresenter().getOrderList(status,mNextRequestPage,address);
    }


    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
//            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public OrderPresenter createPresenter() {
        return new OrderPresenter(this);
    }

    @Override
    public void UpdateSussess(int position) {
        mAdapter.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void ResultAddress1(List<oneArea> reslt) {
        oneAreas=reslt;
        initMenu();
    }

    @Override
    public void showResult(List<OrderBean> result) {
        if(mNextRequestPage==0){
            setData(true,result);
            mAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        }else{
            setData(false, result);
        }


    }

    @Override
    public void showResultOnErr(String err) {
               Toast.makeText(this,err,Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onItemChildClick(final BaseQuickAdapter adapter, View view,final int position) {
        switch (view.getId()) {

            case R.id.order_btn_phone://打电话
                OrderBean temp=  (OrderBean) adapter.getData().get(position);
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + temp.getPhone());
                intent.setData(data);
                OrderActivity.this.startActivity(intent);
                break;
            case R.id.order_list_zt:

                final MyDialog myDialog = new MyDialog(OrderActivity.this, "是否执行该操作?");
                myDialog.show();
                myDialog.positive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        OrderBean temp1=  (OrderBean) adapter.getData().get(position);
                        if(temp1.getStatus()==0){
                            temp1.setStatus(2);
                        }else if(temp1.getStatus()==2){
                            temp1.setStatus(4);
                        }else{
                            Toast.makeText(OrderActivity.this,"不可修改",Toast.LENGTH_LONG).show();
                        }
                        getPresenter().UpdateOrder(temp1,position);
                        myDialog.dismiss();
                    }
                });
                myDialog.negative.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        myDialog.dismiss();
                    }

                });



              /*  orderBean.setPhone(temp1.getPhone());
                orderBean.setUsername(temp1.getUsername());
                orderBean.setSummary(temp1.getSummary());
                orderBean.setAddress(temp1.getAddress());
                orderBean.setExpressFee(temp1.getExpressFee());
                orderBean.setAmount(temp1.getAmount());
                orderBean.setTotal(temp1.getTotal());
                orderBean.setDetail(temp1.getDetail());*/


                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        }
    }
}
