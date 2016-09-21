package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;

import android.view.View;
import android.widget.AdapterView;

/** 
 * @描述: 可用优惠劵presneter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月30日下午5:21:46
 * @version 1.0
 */
public interface IValidCoupomPresenter<DATA> extends IRefreshPresenter<DATA> {
	void clickListItem(AdapterView<?> parent, View view, int position, long id);
}
