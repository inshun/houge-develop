package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.widget.CommentView.CommentViewListener;
import com.ysp.houge.widget.NeedDetailsView.NeedViewListener;

/**
 * 描述： 需求详情Presenter层接口
 *
 * @ClassName: INeedDetailsPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月19日 下午8:33:15
 * 
 *        版本: 1.0
 */
public interface INeedDetailsPresenter<DATA> extends IRefreshPresenter<DATA>, NeedViewListener, CommentViewListener {

	/** 加载需求详情 */
	void loadSkillDetail(int need_id);

	// 加载喜欢列表
	void loadLoveList(int id);

	/** 加载评论列表 */
	void loadMessageList(int id);

	/** 更多 */
	void more();

	/** 分享 */
	void share();

	/** 举报 */
	void requestReport(int reportType);

	/** 打电话 */
	void callPhone();

	/** 接活 */
	void take();

	/** 聊一聊 */
	void haveATalk();

	/** 点击列表 */
	void clickItem(int id);
}
