package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @author it_hu
 * 
 *         买家关注设置View层接口
 *
 */
public interface IRecommendNeedSettingPageView extends IBaseView {
	/** 更新页面工种信息 */
	void notifyListDate(String string);

	/** 跳转到选择工种页面 */
	void jumpToSkillList();

	/** 刷新显示的工种 */
	void notifyListDate(List<WorkTypeEntity> entities);

	/** 更新页面地图地址 */
	void setAddress(AddressEntity addressEntity);

	/** 销毁定位对象 */
	void destroyLocationClient();

	/** 地图取点 */
	void jumpToChooseMap(double latitude, double langtitude);

	/** 更新服务范围 */
	void setServiceDistance(int serviceArea);

	/** 回到首页 */
	void jumpToHome(boolean isRefresh);
}
