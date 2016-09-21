package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.db.ItemWorkInfoEntity;

import java.util.List;

/**
 * @描述:工作经历列表页面view层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日上午11:00:49
 * @version 1.0
 */
public interface IMeWorkInfoListPageView extends
		IBaseRefreshListView<List<ItemWorkInfoEntity>> {
	/**
	 * @描述:移除工作经历列表中的某一项
	 * @方法名: removeOneItem
	 * @param itemWorkInfoEntity
	 * @param position
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月2日上午11:01:13
	 * @修改人 tyn
	 * @修改时间 2015年8月2日上午11:01:13
	 * @修改备注
	 * @since
	 * @throws
	 */
	void removeOneItem(ItemWorkInfoEntity itemWorkInfoEntity, int position);

	/**
	 * @描述:添加一条工作经历
	 * @方法名: addItem
	 * @param itemWorkInfoEntity
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月2日上午11:01:31
	 * @修改人 tyn
	 * @修改时间 2015年8月2日上午11:01:31
	 * @修改备注
	 * @since
	 * @throws
	 */
	void addItem(ItemWorkInfoEntity itemWorkInfoEntity);

	/**
	 * @描述:显示确认删除按钮
	 * @方法名: showSubmitDeleteDialog
	 * @param itemWorkInfoEntity
	 * @param position
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月2日上午11:01:41
	 * @修改人 tyn
	 * @修改时间 2015年8月2日上午11:01:41
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showSubmitDeleteDialog(ItemWorkInfoEntity itemWorkInfoEntity,
			int position);
}
