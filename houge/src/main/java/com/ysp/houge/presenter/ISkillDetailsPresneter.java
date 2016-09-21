package com.ysp.houge.presenter;

import android.view.View;
import android.widget.AdapterView;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.widget.CommentView.CommentViewListener;
import com.ysp.houge.widget.SkillDetailsView.SkillViewListener;

/**
 * @描述: 技能详情以及其他服务列表页面Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月12日下午9:28:10
 * @version 1.0
 */
public interface ISkillDetailsPresneter<DATA>
		extends IRefreshPresenter<DATA>, SkillViewListener, CommentViewListener {
	void clickItem(AdapterView<?> parent, View view, int position, long id);

	/** 加载技能详情 */
	void loadSkillDetail(int id);

	/** 加载赞的列表 */
	void loadLoveList(int id);
	
	/** 加载评论的列表 */
	void loadMessageList(int id);

	/** 点击更多 */
	void more();

	/*分享*/
	void onShare();

	/**分享*/
	void shareUser();

	/** 预定 */
	void order();

	/** 聊一聊 */
	void haveATalk();
	
	/** 投诉 */
	void requestReport(int reportType);

}
