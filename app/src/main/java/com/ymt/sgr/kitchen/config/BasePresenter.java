package com.ymt.sgr.kitchen.config;

import android.content.Context;

import com.tz.mvp.framework.base.presenter.impl.MvpBasePresenter;
import com.tz.mvp.framework.base.view.MvpView;

/**
 * Data：2018/1/23/023-16:01
 * By  沈国荣
 * presenter的基类
 */

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    public BasePresenter(Context context) {
        super(context);
    }

}
