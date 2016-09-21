package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;

/**
 * 描述： 搜索技能Presenter层接口
 *
 * @ClassName: ISearchSkillPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午11:31:55
 * 
 *        版本: 1.0
 */
public interface ISearchSkillPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener{

	void setSearchText(String string);
}
