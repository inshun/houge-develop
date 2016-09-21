package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * 描述： 需求详情Presenter层
 *
 * @ClassName: INeedDetailsPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月19日 下午8:31:59
 * 
 *        版本: 1.0
 */
public interface INeedDetailsPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 显示更多对话框 */
	void showMoreDialog();

	/** 加载需求详情完成 */
	void loadDitailFinish(GoodsDetailEntity entity);

	/** 加载赞完成 */
	void loadZanFinish(List<UserInfoEntity> list);

	/** 加载评论完成 */
	void loadCommentFinish(List<CommentEntity> list);

	/** 跳转更多喜欢 */
	void jumpToMoreLove(int id);

	/** 打电话 */
	void jumpToCallPhone(String phoneNum);

	/** 跳转到更多评论 */
	void jumpToMoreComment(int id);

	/** 聊一聊(接活) */
	void haveATalk(ChatPageEntity chatPageEntity, int op);

	/** 跳转登录 */
	void jumpToLogin();

	/** 分享 */
	void share(GoodsDetailEntity detailEntity);

	/** 跳转用户信息 */
	void jumpToUserInfo(int id);

	/** 隐藏出售 */
	void hideSell();
}
