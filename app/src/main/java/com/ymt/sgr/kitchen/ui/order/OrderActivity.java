package com.ymt.sgr.kitchen.ui.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.google.gson.Gson;
import com.ymt.sgr.kitchen.R;
import com.ymt.sgr.kitchen.config.AppCon;
import com.ymt.sgr.kitchen.config.BaseMvpActivity;
import com.ymt.sgr.kitchen.config.MvpWebSocketActivity;
import com.ymt.sgr.kitchen.http.HttpUtils;
import com.ymt.sgr.kitchen.model.CommonModel;
import com.ymt.sgr.kitchen.model.GsonTip;
import com.ymt.sgr.kitchen.model.OrderBean;
import com.ymt.sgr.kitchen.model.clientMsg;
import com.ymt.sgr.kitchen.model.oneArea;
import com.ymt.sgr.kitchen.ui.LoginActivity;

import com.ymt.sgr.kitchen.ui.adapter.OrderListAdapter;
import com.ymt.sgr.kitchen.util.OrderStatus;
import com.ymt.sgr.kitchen.util.SoundPoolPlayer;
import com.ymt.sgr.kitchen.util.StartActivityUtil;
import com.ymt.sgr.kitchen.util.ToastUtils;
import com.zhangke.websocket.ErrorResponse;
import com.zhangke.websocket.Response;


import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterTSC;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class OrderActivity extends MvpWebSocketActivity<OrderView,OrderPresenter> implements OrderView ,OnItemChildClickListener{

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;


    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private OrderListAdapter mAdapter;

    private int mNextRequestPage = 0;
    private static final int PAGE_SIZE = 10;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    String address;
    private int status=1;//默认是待制作
    private int lastWm=1;
    private int lastZq=1;
    private int type=1;//默认是外卖
    private String userID;
    int shopId;;


    @BindView(R.id.order_text_dzz)
    TextView order_text_dzz;

    @BindView(R.id.order_text_dps)
    TextView order_text_dps;

    @BindView(R.id.order_text_zzz)
    TextView order_text_zzz;



    @BindView(R.id.zq_dzz)
    TextView zq_dzz;

    @BindView(R.id.zq_zzz)
    TextView zq_zzz;

    @BindView(R.id.zq_dqc)
    TextView zq_dqc;

    @BindView(R.id.zq_yqc)
    TextView zq_yqc;

    @BindView(R.id.top_view_left)
    TextView top_view_left;


    @BindView(R.id.order_btn_wm)
    Button order_btn_wm;

    @BindView(R.id.order_btn_zq)
    Button order_btn_zq;

    @BindView(R.id.wm_bottom_view)
    View wm_bottom_view;

    @BindView(R.id.zq_bottom_view)
    View zq_bottom_view;



    Badge wm_badeg_but,zq_badeg_but;

    private  boolean isWm,isZq;

    private Gson gson;
    SoundPoolPlayer mPlayer;


    //设置默认
    private void setMoren(){


            isWm=true;
            order_btn_wm.setTextColor(ContextCompat.getColor(this, R.color.colorRed));

            order_btn_wm.setBackgroundResource(R.drawable.touch_bg_select);
            order_btn_zq.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
            wm_bottom_view.setVisibility(View.VISIBLE);
            zq_bottom_view.setVisibility(View.GONE);
            setWmChoice(0);
            setzQChoice(0);

        zq_badeg_but=new QBadgeView(this).bindTarget(order_btn_zq).setBadgeGravity(Gravity.END | Gravity.TOP);
        wm_badeg_but=new QBadgeView(this).bindTarget(order_btn_wm).setBadgeGravity(Gravity.END | Gravity.TOP);

  /*      if(isZq){
            order_btn_zq.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
            order_btn_wm .setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
            isWm=false;
        }else{
            order_btn_wm.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
            order_btn_zq.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
            isZq=false;
        }*/
    }



    private String headers[] = {"外卖", "自取"};
    private List<View> popupViews = new ArrayList<>();




//    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = { "待配送", "已完成","已取消"};

    private int constellationPosition = 0;
    BluetoothAdapter blueadapter;//蓝牙适配器
    private View dialogView,dialogView2,dialogView3;
    private ArrayAdapter<String> adapter1,adapter2,adapter3;
    private ArrayList<String> deviceList_bonded=new ArrayList<String>();
    private ArrayList<String> deviceList_found=new ArrayList<String>();
    private ListView lv1,lv2,lv_usb;
    private LinearLayout ll1;
    Button btn_scan;
    AlertDialog dialog;
    public  String mac="",usbDev="";
    public static IMyBinder binder;//IMyBinder接口，所有可供调用的连接和发送数据的方法都封装在这个接口内
    StringBuffer buf;
    //bindService的参数conn
    ServiceConnection conn=new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            //绑定成功
            binder=(IMyBinder) service;



        }
    };
    boolean isConnect;//用来标识连接状态的一个boolean值


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }
    @OnClick({R.id.top_view_right_text,R.id.layout_zq_dqc,R.id.layout_zq_zzz,R.id.layout_zq_dzz,R.id.layout_dps,R.id.layout_zzz,R.id.layout_dzz
            ,R.id.top_view_left,R.id.layout_zq_yqc,R.id.order_btn_wm,R.id.order_btn_zq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_btn_wm://外卖
                type=1;
//                this.sendText("测试");
              if(!isWm){

                  order_btn_wm.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
                  order_btn_wm.setBackgroundResource(R.drawable.touch_bg_select);
                  order_btn_zq .setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
                  order_btn_zq.setBackgroundResource(R.drawable.touch_bg);
                  wm_bottom_view.setVisibility(View.VISIBLE);
                  zq_bottom_view.setVisibility(View.GONE);
                  isZq=false;
                  status=lastWm;
                  refresh();

              }
                break;
            case R.id.order_btn_zq://自取
                type=0;
                if(!isZq){

                    order_btn_zq.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
                    order_btn_zq.setBackgroundResource(R.drawable.touch_bg_select);
                    order_btn_wm .setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
                    order_btn_wm.setBackgroundResource(R.drawable.touch_bg);
                    zq_bottom_view.setVisibility(View.VISIBLE);
                    wm_bottom_view .setVisibility(View.GONE);
                    isWm=false;

                        status=lastZq;

                    refresh();

                }
//                setzQChoice(3);
                break;

            case R.id.layout_zq_yqc://已取餐
              status=lastZq=4;
                refresh();
                setzQChoice(3);
                break;
            case R.id.layout_zq_dqc://待取餐
                status=lastZq=3;
                refresh();
                setzQChoice(2);
                break;
            case R.id.layout_zq_zzz://自取 制作中
                status= lastZq=2;
                refresh();
                setzQChoice(1);
                break;
            case R.id.layout_zq_dzz:// 自取待制作
                status= lastZq=1;
                refresh();
                setzQChoice(0);
                break;
            /////////////////////////////////////////////外卖
            case R.id.layout_dps:// 待配送
                status=lastWm=3;
                refresh();
                setWmChoice(2);

                break;
            case R.id.layout_zzz:// 制作中
                status=lastWm=2;
                refresh();
                setWmChoice(1);
                break;
            case R.id.layout_dzz:// 待制作
                status= lastWm=1;
                refresh();

                setWmChoice(0);
                break;
            case R.id.top_view_right_text:

                editor = pref.edit();
                editor.putString(AppCon.SCCESS_TOKEN_KEY,"");
                editor.commit();
                StartActivityUtil.skipAnotherActivity(this, LoginActivity.class);
                break;
            case R.id.top_view_left://打印机
                if (!isConnect) {//如果没有连接
                    setbluetooth();//蓝牙连接
                }
                break;

     /*       case R.id.result:


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

            case R.id.top_view_left://打印机
                if (!isConnect) {//如果没有连接
                    setbluetooth();//蓝牙连接
                }


                break;*/

        }
    }
    private void setzQChoice(int i){
        clearZq();
        if(i==0){
            zq_dzz.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }else if(i==1){
            zq_zzz.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }else if(i==2){
            zq_dqc.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }else if(i==3){
            zq_yqc.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }
    }
    private void clearZq(){
        zq_yqc.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
        zq_dqc.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
        zq_zzz.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
        zq_dzz.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
    }

    private void setWmChoice(int i){
        clearWm();
        if(i==0){
            order_text_dzz.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }else if(i==1){
            order_text_zzz.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }else if(i==2){
            order_text_dps.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }
    }
    private void clearWm(){
        order_text_dzz.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
        order_text_dps.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
        order_text_zzz.setTextColor(ContextCompat.getColor(this, R.color.bottom_text));
    }

    protected void setbluetooth() {
        // TODO Auto-generated method stub
        blueadapter= BluetoothAdapter.getDefaultAdapter();
        //确认开启蓝牙
        if(!blueadapter.isEnabled()){
            //请求用户开启
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, AppCon.ENABLE_BLUETOOTH);

        } else {
            //蓝牙已开启
            showblueboothlist();
        }

    }

    private void showblueboothlist() {
        if (!blueadapter.isDiscovering()) {
            blueadapter.startDiscovery();
        }
        LayoutInflater inflater=LayoutInflater.from(this);
        dialogView=inflater.inflate(R.layout.printer_list, null);
        adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList_bonded);
        lv1=(ListView) dialogView.findViewById(R.id.listView1);
        btn_scan=(Button) dialogView.findViewById(R.id.btn_scan);
        ll1=(LinearLayout) dialogView.findViewById(R.id.ll1);
        lv2=(ListView) dialogView.findViewById(R.id.listView2);
        adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList_found);
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        dialog=new AlertDialog.Builder(this).setTitle("BLE").setView(dialogView).create();
        dialog.show();
        setlistener();
        findAvalibleDevice();
    }


    private void setlistener() {
        // TODO Auto-generated method stub
        btn_scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ll1.setVisibility(View.VISIBLE);
                //btn_scan.setVisibility(View.GONE);
            }
        });
        //已配对的设备的点击连接
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    if(blueadapter!=null&&blueadapter.isDiscovering()){
                        blueadapter.cancelDiscovery();

                    }

                    String msg=deviceList_bonded.get(arg2);
                    mac=msg.substring(msg.length()-17);
                    String name=msg.substring(0, msg.length()-18);
                    //lv1.setSelection(arg2);
                    dialog.cancel();
                    if(mac!=null){
                        sendble();
                    }else{
                        Toast.makeText(getApplicationContext(),getString(R.string.con_failed), Toast.LENGTH_SHORT).show();
                    }
//                    et.setText(mac);
                    //Log.i("TAG", "mac="+mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        //未配对的设备，点击，配对，再连接
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    if(blueadapter!=null&&blueadapter.isDiscovering()){
                        blueadapter.cancelDiscovery();

                    }
                    String msg=deviceList_found.get(arg2);
                    mac=msg.substring(msg.length()-17);
                    String name=msg.substring(0, msg.length()-18);
                    //lv2.setSelection(arg2);
                    dialog.cancel();
                    if(mac!=null){
                        sendble();
                    }else{
                        Toast.makeText(getApplicationContext(),getString(R.string.con_failed), Toast.LENGTH_SHORT).show();
                    }
//                    et.setText(mac);
                    Log.i("TAG", "mac="+mac);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    private void findAvalibleDevice() {
        // TODO Auto-generated method stub
        //获取可配对蓝牙设备
        Set<BluetoothDevice> device=blueadapter.getBondedDevices();

        deviceList_bonded.clear();
        if(blueadapter!=null&&blueadapter.isDiscovering()){
            adapter1.notifyDataSetChanged();
        }
        if(device.size()>0){
            //存在已经配对过的蓝牙设备
            for(Iterator<BluetoothDevice> it = device.iterator(); it.hasNext();){
                BluetoothDevice btd=it.next();
                deviceList_bonded.add(btd.getName()+'\n'+btd.getAddress());
                adapter1.notifyDataSetChanged();
            }
        }else{  //不存在已经配对过的蓝牙设备
            deviceList_bonded.add("No can be matched to use bluetooth");
            adapter1.notifyDataSetChanged();
        }

    }

    //连接打印机
    public void sendble() {
        binder.connectBtPort(mac, new UiExecute() {

            @Override
            public void onsucess() {
                // TODO Auto-generated method stub
                //连接成功后在UI线程中的执行
                isConnect=true;
                Toast.makeText(getApplicationContext(),getString(R.string.con_success), Toast.LENGTH_SHORT).show();
                top_view_left.setText(getString(R.string.hasconnect));

                //此处也可以开启读取打印机的数据
                //参数同样是一个实现的UiExecute接口对象
                //如果读的过程重出现异常，可以判断连接也发生异常，已经断开
                //这个读取的方法中，会一直在一条子线程中执行读取打印机发生的数据，
                //直到连接断开或异常才结束，并执行onfailed
                binder.acceptdatafromprinter(new UiExecute() {

                    @Override
                    public void onsucess() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onfailed() {
                        // TODO Auto-generated method stub
                        isConnect=false;
                        Toast.makeText(getApplicationContext(), getString(R.string.con_has_discon), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onfailed() {
                // TODO Auto-generated method stub
                //连接失败后在UI线程中的执行
                isConnect=false;
                Toast.makeText(getApplicationContext(), getString(R.string.con_failed), Toast.LENGTH_SHORT).show();
                //connect.setText("连接失败");
            }
        });
    }


    @Override
    protected void initView() {
        super.initView();

        mPlayer = SoundPoolPlayer.create(this, R.raw.newtip);
        mPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) { 	//mp will be null here
                        Log.d("debug", "completed");
                    }
                }
        );

        pref = this.getSharedPreferences(AppCon.USER_KEY,MODE_PRIVATE);
        userID= pref.getString(AppCon.USER_USER_ID,"");
        shopId=pref.getInt(AppCon.USER_SHOP_ID,0);
        Intent intent=new Intent(this,PosprinterService.class);
        //绑定service，获取ImyBinder对象
        bindService(intent, conn, BIND_AUTO_CREATE);
        top_view_left.setText(getString(R.string.unconnect));
        setMoren();
        initMenu();
//        getPresenter().getAddress1();
        //init city menu

    }

    private void initMenu(){
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
        getPresenter().getOrderList(status,mNextRequestPage,type);
    }

    private void refresh() {
        if(type==0&&status==1){//自取的提醒点要取消
            zq_badeg_but.hide(true);
        }
        if(type==1&&status==1){
            wm_badeg_but.hide(true);
        }

        mNextRequestPage = 0;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
       getPresenter().getOrderList(status,mNextRequestPage,type);
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
//        oneAreas=reslt;
        initMenu();
    }

    @Override
    public void showResult(List<OrderBean> result) {
        if(result!=null){
            System.out.println("显示数据"+result.size());
        }else{
            System.out.println("没有数据");
        }

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


            case R.id.order_list_zt://修改订单状态
                OrderBean updateOrder=  (OrderBean) adapter.getData().get(position);
                if(updateOrder.getStatus()==1){//需要设置时间//按下开始制作
               /*     Date now = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                    String time = dateFormat.format(now);
                    updateOrder.setCfStartTime(time);*/
                    updateOrder.setCfId(userID);
                    updateOrder.setStatus(2);
                    getPresenter().UpdateOrder(updateOrder,position);
                }else if(updateOrder.getStatus()==2){//按下完成制作
                  /*  Date now = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                    String time = dateFormat.format(now);
                    updateOrder.setCfEndTime(time);
                    try {
                        updateOrder.setCfTimeLen(OrderStatus.timeSubtraction(updateOrder.getCfStartTime(),time));//分钟
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    updateOrder.setStatus(3);
                    getPresenter().UpdateOrder(updateOrder,position);
          /*          if(gson==null){
                        gson=new Gson();
                    }
                    GsonTip tip=new GsonTip();
                    tip.setOrderId(updateOrder.getOrderId());
                    tip.setShopId(updateOrder.getShopId());
                    tip.setStatus(updateOrder.getStatus());
                    tip.setType(updateOrder.getType());
                    OrderActivity.this.sendText(gson.toJson(tip));//推送给厨房*/

                }else if(updateOrder.getStatus()==3){
                   return;
                }


                break;

            case R.id.order_btn_phone://打电话
                OrderBean temp=  (OrderBean) adapter.getData().get(position);
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + temp.getPhone());
                intent.setData(data);
                OrderActivity.this.startActivity(intent);
                break;
            case R.id.order_btn_dy://打印
                OrderBean temp1=  (OrderBean) adapter.getData().get(position);

                if (isConnect) {

                    try {
                        binder.write(PrintContent(temp1), new UiExecute() {//打印
                            @Override
                            public void onsucess() {
                                // TODO Auto-generated method stub
                                Toast.makeText(getApplicationContext(), getString(R.string.send_success), Toast.LENGTH_SHORT)
                                        .show();
                            }
                            @Override
                            public void onfailed() {
                                // TODO Auto-generated method stub
                                Toast.makeText(getApplicationContext(), getString(R.string.send_failed), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.not_con_printer), Toast.LENGTH_SHORT).show();
                }



        /*        final MyDialog myDialog = new MyDialog(OrderActivity.this, "是否执行该操作?");
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

                });*/



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

    //打印内容
    private  byte[] PrintContent(OrderBean result) throws ParseException {

        if(buf==null){
            buf =new StringBuffer();
        }else{
            buf.setLength(0);
        }
        buf.append(" ");
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
        buf.append(result.getUsername());
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
        buf.append(result.getPhone());
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
        buf.append(result.getAddress());
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
        //菜单
        getBuff( result.getDetail(),buf);
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
        buf.append(result.getSummary());
        buf.append("\r\n");
        buf.append( "送达时间："+ OrderStatus.TimeFormat(result.getSendTime()));
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
        buf.append(" ");
        buf.append("\r\n");
//        result.getGmtCreate()
//        buf.append( "送达时间："+ OrderStatus.TimeFormat(result.getSendTime()));
        String results=buf.toString();

        System.out.println("打印内容"+results);
        return strTobytes(results);

    }
    /**
     * 字符串转byte数组
     * */
    public static byte[] strTobytes(String str){
        byte[] b=null,data=null;
        try {
            b = str.getBytes("utf-8");
            data=new String(b,"utf-8").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }
    private void getBuff(String str,StringBuffer buf) {

        if(str.contains("+")){
            String[] arr1 = str.split("\\+");
            for(int i=0;i<arr1.length;i++){
                buf.append(arr1[i]);
                buf.append("\r\n");
            }
        }else{
            buf.append(str);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onMessageResponse(Response message) {
        System.out.println("新消息提示"+message.getResponseText());
        if(gson==null){
            gson=new Gson();
        }
        if(message.getResponseText().contains("clientMsg")){//app端推送的东西
            clientMsg clientMsg= gson.fromJson( message.getResponseText() , clientMsg.class ) ;
            showTip(clientMsg.getClientMsg());
        }else{ //服务器推送的东西
            showTip(message.getResponseText());

        }

    }

    private void showTip(String message) {
        GsonTip newTip= gson.fromJson(message , GsonTip.class ) ;
        System.out.println("newTip.getShopId()"+newTip.getShopId()+"shopId"+shopId);
        if(newTip.getShopId().equals(String.valueOf(shopId))){

            if(newTip.getStatus()==1) { //新订单提醒
                showMusicTip();
                ToastUtils.showLong("新订单");
                if(newTip.getType()==1){//外卖提醒
                    wm_badeg_but.setBadgeText("");
                }else{//自取提醒
                    zq_badeg_but.setBadgeText("");
                }

            }
        }

    }

    @Override
    public void onSendMessageError(ErrorResponse error) {
        System.out.println("新消息错误"+error.getResponseText());
    }

    private void showMusicTip() {
        if(!mPlayer.isPlaying()){
            mPlayer.play();
        }
    }
}
