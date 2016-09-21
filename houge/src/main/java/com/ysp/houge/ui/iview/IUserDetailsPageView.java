package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * @描述: 用户详情以及技能列表页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月12日下午8:44:57
 * @version 1.0
 */
public interface IUserDetailsPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 选择列表类型 */
	void swithListType(int type);

	/** 设置用户信息 */
	void showUserInfo(UserInfoEntity infoEntity);

	/** 分享 */
	void share(UserInfoEntity entity);

	/** 更多 */
	void showMoreDialog();

	/** 跳转到技能详情 */
	void jumpToSkillDetails(int id);

	/** 跳转到需求详情 */
	void jumpToNeedDetails(int id);

	/** 分享 */
	void share(GoodsDetailEntity entity);

	/** 聊一聊 */
	void haveATalk(ChatPageEntity chatPageEntity);

}
