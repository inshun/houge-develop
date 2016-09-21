package com.ysp.houge.presenter;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.model.entity.bean.PhotoEntity;
import com.ysp.houge.model.entity.db.ItemWorkInfoEntity;

/**
 * @描述:工作经历列表页面p层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月25日下午12:16:53
 * @version 2.2
 */
public interface IMeWorkInfoListPagePresenter extends
		IRefreshPresenter<List<ItemWorkInfoEntity>> {
	void clickListItem(AdapterView<?> parent, View view, int position, long id);

	void clickItemDeleteBtn(ItemWorkInfoEntity itemWorkInfoEntity, int position);

	void clickItemPicture(List<PhotoEntity> photoEntities,
			int positionInPictureList);

	void submitDeleteWorkInfo(ItemWorkInfoEntity itemWorkInfoEntity,
			int position);
}
