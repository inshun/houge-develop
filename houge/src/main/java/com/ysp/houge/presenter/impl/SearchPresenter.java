package com.ysp.houge.presenter.impl;

import com.ysp.houge.dbcontroller.SearchHistoryContorller;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ISearchPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.ISearchPageView;
import com.ysp.houge.ui.search.SearchActivity;

import android.text.TextUtils;

public class SearchPresenter extends BasePresenter<ISearchPageView> implements ISearchPresenter {
	private SearchHistoryEntity entity;
	private int searchType = SearchActivity.SEARCH_TYPE_USER;

	public SearchPresenter(ISearchPageView view) {
		super(view);
		mView.changeSearchType(searchType);
	}

	@Override
	public void clickSearchType() {
		mView.showSearchTypePop();
	}

	@Override
	public void initModel() {

	}

	@Override
	public void onTypeClick(int searchType) {
		this.searchType = searchType;
		mView.changeSearchType(searchType);
	}

	@Override
	public void search(String str, BaseFragment fragment) {
		if (TextUtils.isEmpty(str)) {
			mView.showToast("请输入搜索关键字");
		} else {
			if (entity != null && entity.searctType == searchType && entity.text == str) {
				// 重复搜索，直接返回(搜索实体、搜索类型以及文本全部一样)
				return;
			}

			// 记录搜索历史
			if (entity == null) {
				entity = new SearchHistoryEntity(str, searchType);
			} else {
				entity.searctType = searchType;
				entity.text = str;
			}
			//保存到搜索历史的数据库
			SearchHistoryContorller.create(entity);

			// 根据类型显示相应列表
			switch (searchType) {
			case SearchActivity.SEARCH_TYPE_SKILL:
				mView.showSkillList(fragment, str);
				break;
			case SearchActivity.SEARCH_TYPE_NEED:
				mView.showNeedList(fragment, str);
				break;
			case SearchActivity.SEARCH_TYPE_USER:
				mView.showUserList(fragment, str);
				break;
			}
		}
	}

}
