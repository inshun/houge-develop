package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.adapter.AddressAdapter;
import com.ysp.houge.model.entity.bean.AddressEntity;

import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * @描述: 地址管理Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author hx
 * @date 2015年10月23日下午12:51:27
 * @version 1.0
 */
public interface IAddressManagerPresenter<DATA> extends IRefreshPresenter<DATA> {

	void initEnterPage(int enterPage);

	void addAdress();

	void setDefaultAddress();

	void clickListItem(AdapterView<?> parent, View view, int position, long id);

	void longClickListItem(int id,int position, List<AddressEntity> data);

	void delete();
}
