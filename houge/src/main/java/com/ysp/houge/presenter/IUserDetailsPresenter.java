package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;

import android.view.View;
import android.widget.AdapterView;

/**
 * @描述: 用户详情以及技能列表页面Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月12日下午9:28:10
 * @version 1.0
 */
public interface IUserDetailsPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {
	
	void setTAG(int TAG);

    void shareUser();

	void setUserId(int id);

	void LoadUserInfo();
	
	void requestReport(int reportType);
	
	void clickItem(AdapterView<?> parent, View view, int position, long id);
}
