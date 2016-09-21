package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

import java.util.List;

/**
 * @描述: 附近页面Map View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月30日下午7:00:30
 * @version 1.0
 */
public interface INearbyMapPageView extends IBaseView {

    /**设置页面数据*/
    void setWorkTypeEntity(WorkTypeEntity workTypeEntity);

	/** 开始定位(定位后获取对应的需要的数据) */
	void startLocal();

	/** 显示地图的坐标点 */
	void showMapMarkerList(List<UserInfoEntity> list);
}
