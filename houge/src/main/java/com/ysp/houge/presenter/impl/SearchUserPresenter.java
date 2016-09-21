package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.model.ISearchModel;
import com.ysp.houge.model.entity.bean.SearchUserResultEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.SearchModelImpl;
import com.ysp.houge.presenter.ISearchUserPresenter;
import com.ysp.houge.ui.iview.ISearchUserPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import android.text.TextUtils;

/**
 * 描述： 搜索用户Presenter层
 *
 * @ClassName: SearchUserPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午10:31:12
 * 
 *        版本: 1.0
 */
public class SearchUserPresenter implements ISearchUserPresenter<List<UserInfoEntity>> {
	private String text;
	private int page = 1;
	private boolean hasNext = true;

	private ISearchModel iSearchModel;
	private ISearchUserPageView iSearchUserPageView;

	public SearchUserPresenter(ISearchUserPageView iSearchUserPageView) {
		super();
		iSearchModel = new SearchModelImpl();
		this.iSearchUserPageView = iSearchUserPageView;
	}

	@Override
	public void refresh() {
		// 一个空字段的搜索，跳过，实际上View层会拦截，这里进行二次拦截
		if (TextUtils.isEmpty(text)) {
			iSearchUserPageView.refreshComplete(null);
			return;
		}

		page = 1;
		iSearchUserPageView.showProgress();
		iSearchModel.searchUser(page, text, new OnNetResponseCallback() {
			@Override
			public void onSuccess(Object data) {
				iSearchUserPageView.hideProgress();
				if (data != null && data instanceof SearchUserResultEntity) {
					SearchUserResultEntity entity = (SearchUserResultEntity) data;
					if (null != entity.getList() && !entity.getList().isEmpty()) {
						iSearchUserPageView.refreshComplete(entity.getList());
						hasNext = ((SearchUserResultEntity) data).isNext();
					} else {
						iSearchUserPageView.refreshComplete(null);
                        iSearchUserPageView.showToast("没有相关用户");
						hasNext = false;
					}
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iSearchUserPageView.hideProgress();
				iSearchUserPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		page++;
		iSearchModel.searchUser(page, text, new OnNetResponseCallback() {
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof SearchUserResultEntity) {
					SearchUserResultEntity entity = (SearchUserResultEntity) data;
					if (null != entity.getList() && !entity.getList().isEmpty()) {
						iSearchUserPageView.loadMoreComplete(entity.getList());
						hasNext = ((SearchUserResultEntity) data).isNext();
					} else {
						iSearchUserPageView.loadMoreComplete(new ArrayList<UserInfoEntity>());
						hasNext = false;
					}
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iSearchUserPageView.loadMoreComplete(new ArrayList<UserInfoEntity>());
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasNext;
	}

	@Override
	public void setSearchText(String text) {
		this.text = text;
	}

}
