package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;

/**
 * @author it_huang
 *
 *         我的技能presenter层接口
 *
 * @param <DATA>
 */
public interface IMySkillPresenter<DATA> extends IRefreshPresenter<DATA> {

	void delete(int id);
}
