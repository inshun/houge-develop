package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SkillMoneyUnitEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

import java.util.List;

/**
 * @描述: 技能详情以及其他服务页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月16日上午9:44:58
 * @version 1.0
 */
public interface ISkillDetailsPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 显示更多对话框 */
	void showMoreDialog();

	/** 加载技能详情完成 */
	void loadDetailFinish(GoodsDetailEntity entity);

	/** 加载赞完成 */
	void loadZanFinish(List<UserInfoEntity> list);

	/** 加载评论完成 */
	void loadCommentFinish(List<CommentEntity> list);

	/** 分享 */
	void share(GoodsDetailEntity entity);

	/** 预约 */
	void jumpToOrder(GoodsDetailEntity entity);

	/** 跳转更多喜欢 */
	void jumpToMoreLove(int id);

	/** 跳转到更多评论 */
	void jumpToMoreComment(int id);

	/** 聊一聊 */
	void haveATalk(ChatPageEntity chatPageEntity);

	/** 显示从新计算的金额 */
	void calcAndShowTotalMoney(String money);

	/** 显示赞的状态 */
	void showZanStatus(boolean isZan);

	/** 跳转登录 */
	void jumpToLogin();

	/** 跳转用户详情 */
	void jumpUserInfo(int id);

	/** 隐藏下面售卖的一行 */
	void hideSell();


}
