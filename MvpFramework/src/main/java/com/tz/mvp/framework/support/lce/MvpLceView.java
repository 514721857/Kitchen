package com.tz.mvp.framework.support.lce;

import com.tz.mvp.framework.base.view.MvpView;

/**
 * 目标接口
 * 
 * @author Dream
 *
 *
 */
public interface MvpLceView extends MvpView {

	/**
	 * 显示loading页面 pullToRefresh：true代表你用的是下拉刷新组件
	 * 
	 * @param pullToRefresh
	 */
	public void showLoading(boolean pullToRefresh);

	/**
	 * 显示ContentView
	 */
	public void showContent();

	/**
	 * 显示异常界面
	 */
	public void showError();

	/**
	 * 绑定数据
	 * @param type 当多个请求时，用一个type标记，所有返回数据都会在这里进行处理
	 * @param data
	 */
	public void bindData(Object data,String type);

	/**
	 * 加载数据
	 *
	 * @param pullToRefresh
	 */
	public void loadData(boolean pullToRefresh,int page);

}
