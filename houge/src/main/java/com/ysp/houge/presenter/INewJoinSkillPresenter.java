package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 最新加入Presenter层接口
 *
 * @ClassName: INewJoinSkillPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 下午1:11:20
 * 
 *        版本: 1.0
 */
public interface INewJoinSkillPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {

	void setWorkType(WorkTypeEntity entity);
}
