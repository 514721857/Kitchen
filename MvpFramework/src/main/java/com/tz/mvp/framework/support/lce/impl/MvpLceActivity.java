package com.tz.mvp.framework.support.lce.impl;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.tz.mvp.framework.base.presenter.MvpPresenter;
import com.tz.mvp.framework.support.lce.MvpLceView;
import com.tz.mvp.framework.support.lce.impl.animator.ILceAnimator;
import com.tz.mvp.framework.support.view.MvpActivity;

public abstract class MvpLceActivity< V extends MvpLceView, P extends MvpPresenter<V>>
		extends MvpActivity<V, P> implements MvpLceView {

	private MvpLceViewImpl lceViewImpl;

	// 初始化Lce UI布局（规定你的Lce布局文件的id）

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		lceViewImpl = new MvpLceViewImpl();
		initLceView(getWindow().getDecorView());
	}

	private void initLceView(View v) {
		lceViewImpl.initLceView(v);
		lceViewImpl.setOnErrorViewClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onErrorClick();
			}
		});
	}

	/**
	 * 提供给子类配置自己想要的动画策略
	 * @param lceAnimator
	 */
	public void setLceAnimator(ILceAnimator lceAnimator) {
		lceViewImpl.setLceAnimator(lceAnimator);
	}

	@Override
	public void showLoading(boolean pullToRefresh) {
		lceViewImpl.showLoading(pullToRefresh);
	}

	@Override
	public void showContent() {
		lceViewImpl.showContent();
	}

	@Override
	public void showError() {
		lceViewImpl.showError();
	}

	public void onErrorClick() {
		loadData(false,1);
	}
	@Override
	public void bindData(Object data,String type) {
		lceViewImpl.bindData(data,type);
	}

	@Override
	public void loadData(boolean pullToRefresh,int page) {
		lceViewImpl.loadData(pullToRefresh,page);
	}

}
