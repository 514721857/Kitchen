package com.ymt.sgr.kitchen.config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;


import com.tz.mvp.framework.base.presenter.MvpPresenter;
import com.tz.mvp.framework.base.view.MvpView;
import com.tz.mvp.framework.support.view.MvpActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Data：2018/1/23/023-16:01
 * By  沈国荣
 * 不具有lce模式，带有沉浸式标题栏的 mvpactivity基类 buffer注解
 */
public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {


    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        getSupportActionBar().hide();       去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
//        getSupportActionBar().hide();       //去掉状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(setLayoutId());
        //绑定控件
        unbinder = ButterKnife.bind(this);
        //初始化沉浸式
    /*    if (isImmersionBarEnabled())
            initImmersionBar();*/

        //view与数据绑定
        initView();
        //初始化数据
        initData();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存数据
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    protected abstract int setLayoutId();

    protected void initImmersionBar() {
        //在BaseActivity里初始化  因为界面需要，状态栏的背景都需要是以图片为背景，所以这里需要在每个Activity里的xml布局文件中加入  topview布局
        //        https://github.com/gyf-dev/ImmersionBar  最外面布局为realayout 可能会导致挡住标题栏

        /*
         .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
             .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
             .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
        */
//         */



       /* mImmersionBar = ImmersionBar.with(this).fitsSystemWindows(true) .statusBarDarkFont(true, 0.2f)  ;
        mImmersionBar.init();*/
    }

    protected void initData() {
    }

    protected void initView() {
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

}
