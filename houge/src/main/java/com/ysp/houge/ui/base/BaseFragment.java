package com.ysp.houge.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysp.houge.dialog.HougeProgressDialog;
import com.ysp.houge.ui.iview.IBaseView;
import com.ysp.houge.utility.Toaster;

/**
 * 公共Fragment
 * 
 * @author Administrator
 *
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

	/**
	 * 要显示的View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(getContentView(), container, false);
	}

	/**
	 * 在onCreateView后被触发
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initActionbar(view);
		initializeViews(view, savedInstanceState);
		initializeData(savedInstanceState);
	}

	/**
	 * 初始导航栏
	 * 
	 * @param view
	 */
	protected abstract void initActionbar(View view);

	/**
	 * 初始各View
	 * 
	 * @param view
	 * @param savedInstanceState
	 */
	protected abstract void initializeViews(View view, Bundle savedInstanceState);

	/**
	 * View id
	 * 
	 * @return
	 */
	protected abstract int getContentView();

	/**
	 * 初始数据
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void initializeData(Bundle savedInstanceState);

	@Override
	public void close() {
	}

	@Override
	public void showProgress(boolean flag, String message) {
		if (isAdded()) {
			HougeProgressDialog.show(getActivity());
		}
	}

	@Override
	public void showProgress(String message) {
		showProgress(true, message);
	}

	@Override
	public void showProgress() {
		showProgress(true);
	}


	@Override
	public void showProgress(boolean flag) {
		showProgress(flag, "");
	}

	@Override
	public void hideProgress() {
		HougeProgressDialog.hide();
	}

	@Override
	public void showToast(int resId) {
		showToast(getString(resId));
	}

	@Override
	public void showToast(String msg) {
		if (isAdded()) {
			Toaster.getInstance().displayToast(msg, -1);
		}
	}

	@Override
	public void showNetError() {
		showToast("网络异常,请稍后重试");
	}

	@Override
	public void showParseError() {
		showToast("数据解析异常");
	}

}
