package com.ymt.sgr.kitchen.config;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.tz.mvp.framework.base.presenter.MvpPresenter;
import com.tz.mvp.framework.base.view.MvpView;
import com.tz.mvp.framework.support.delegate.activity.ActivityMvpDelegate;
import com.tz.mvp.framework.support.delegate.activity.ActivityMvpDelegateCallback;
import com.tz.mvp.framework.support.delegate.activity.ActivityMvpDelegateImpl;
import com.zhangke.websocket.AbsWebSocketActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 代理模式－静态代理：MvpActivity是MvpDelegateCallback具体的实现类
 * 
 * @author Dream
 *
 */
@SuppressLint("NewApi")
public abstract class MvpWebSocketActivity<V extends MvpView, P extends MvpPresenter<V>>
		extends AbsWebSocketActivity implements ActivityMvpDelegateCallback<V, P>, MvpView {


	private Unbinder unbinder;

	private P presenter;

	private ActivityMvpDelegate<V, P> activityMvpDelegate;
	
	//是否保存数据
	private boolean retainInstance;

	protected ActivityMvpDelegate<V, P> getActivityMvpDelegate() {
		if (this.activityMvpDelegate == null) {
			this.activityMvpDelegate = new ActivityMvpDelegateImpl<V, P>(this);
		}
		return this.activityMvpDelegate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivityMvpDelegate().onCreate(savedInstanceState);
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
	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}

	@Override
	public P getPresenter() {
		return this.presenter;
	}

	@Override
	public V getMvpView() {
		return (V) this;
	}




	protected abstract int setLayoutId();


	protected void initData() {
	}

	protected void initView() {
	}

	@Override
	protected void onStart() {
		super.onStart();
		getActivityMvpDelegate().onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		getActivityMvpDelegate().onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getActivityMvpDelegate().onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getActivityMvpDelegate().onRestart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		getActivityMvpDelegate().onStop();
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		getActivityMvpDelegate().onContentChanged();
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		getActivityMvpDelegate().onAttachedToWindow();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getActivityMvpDelegate().onSaveInstanceState(outState);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getActivityMvpDelegate().onDestroy();
		unbinder.unbind();
	}
	
	/**
	 * 保存一个对象实例
	 * onRetainCustomNonConfigurationInstance
	 * 该方法FragmentActivity本身就存在，这是我们只是扩展了功能而已
	 * 
	 * 我在这里我代理的是FragmentActivity的方法
	 */
	@Override
	public Object onRetainCustomNonConfigurationInstance() {

		if(getActivityMvpDelegate()!=null){
			return getActivityMvpDelegate().onRetainCustomNonConfigurationInstance();
		}
		//回调
		return getActivityMvpDelegate().onRetainCustomNonConfigurationInstance();
	}
	
	/**
	 * 获取保存的实例
	 * 该方法我们是通过静态代理，对FragmentActivity中的方法进行代理，处理相关逻辑
	 * 保存我们自己想要的数据
	 */
	@Override
	public Object getLastCustomNonConfigurationInstance() {
		return super.getLastNonConfigurationInstance();
	}

	//扩张方法
	@Override
	public Object getNonLastCustomNonConfigurationInstance() {
		return getActivityMvpDelegate().getLastCustomNonConfigurationInstance();
	}
	
	@Override
	public boolean isRetainInstance() {
		return retainInstance;
	}
	
	@Override
	public void setRetainInstance(boolean retaionInstance) {
		this.retainInstance = retaionInstance;
	}
	
	@Override
	public boolean shouldInstanceBeRetained() {
		//说明Activity出现了异常情况才缓存数据
		return this.retainInstance && isChangingConfigurations();
	}
	
	

}
