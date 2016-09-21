package com.ysp.houge.presenter;

import android.view.View;
import android.widget.AdapterView;

import com.tyn.view.IRefreshPresenter;

/**
 * @描述:首页推荐页面的presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月6日下午12:37:58
 * @version 1.0
 */
public interface IRecommandListPresenter<DATA> extends
		IRefreshPresenter<DATA> {
	void clickListItem(AdapterView<?> parent, View view, int position, long id);
}
