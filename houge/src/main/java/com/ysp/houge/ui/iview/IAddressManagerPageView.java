package com.ysp.houge.ui.iview;

import java.util.List;

import com.tyn.view.listviewhelper.ListViewHelper;
import com.ysp.houge.model.entity.bean.AddressEntity;

import android.os.Bundle;

/**
 * @描述: 地址管理View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月23日上午11:06:21
 * @version 1.0
 */
public interface IAddressManagerPageView  extends IBaseRefreshListView<List<AddressEntity>> {
	
	ListViewHelper<List<AddressEntity>> getListViewHelper();
	
	void showDeleteDialog();
	
	void finishPage();
	
	void jumpToAddAdress(Bundle bundle);
}
