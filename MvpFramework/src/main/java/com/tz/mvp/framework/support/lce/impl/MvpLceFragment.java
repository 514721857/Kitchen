package com.tz.mvp.framework.support.lce.impl;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.tz.mvp.framework.base.presenter.MvpPresenter;
import com.tz.mvp.framework.support.lce.MvpLceView;
import com.tz.mvp.framework.support.lce.impl.animator.ILceAnimator;
import com.tz.mvp.framework.support.view.MvpFragment;

public abstract class MvpLceFragment< V extends MvpLceView, P extends MvpPresenter<V>>
		extends MvpFragment<V, P> implements MvpLceView {

	// 初始化Lce UI布局（规定你的Lce布局文件的id）
	private MvpLceViewImpl lceViewImpl;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (lceViewImpl == null) {
			lceViewImpl = new MvpLceViewImpl();
		}
		initLceView(view);
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

	public void setLceAnimator(ILceAnimator lceAnimator){
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

	@Override
	public void loadData(boolean pullToRefresh,int page) {
		lceViewImpl.loadData(pullToRefresh,page);
	}

	@Override
	public void bindData(Object data,String type) {
		lceViewImpl.bindData(data,type);
	}

	public void onErrorClick() {
		loadData(false,1);
	}

}
