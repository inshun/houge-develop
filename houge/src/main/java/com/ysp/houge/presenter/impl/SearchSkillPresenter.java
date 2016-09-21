package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.ISearchModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SearchGoodResultEntity;
import com.ysp.houge.model.impl.SearchModelImpl;
import com.ysp.houge.presenter.ISearchSkillPresenter;
import com.ysp.houge.ui.iview.ISearchSkillPageView;
import com.ysp.houge.ui.search.SearchSkillFragment;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import android.text.TextUtils;

/**
 * 描述： 搜索页面Presenter层
 *
 * @ClassName: SearchSkillPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午11:33:49
 * 
 *        版本: 1.0
 */
public class SearchSkillPresenter implements ISearchSkillPresenter<List<GoodsDetailEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private String text;

	private ISearchModel iSearchModel;
	private ISearchSkillPageView iSearchSkillPageView;

	public SearchSkillPresenter(ISearchSkillPageView iSearchSkillPageView) {
		super();
		iSearchModel = new SearchModelImpl();
		this.iSearchSkillPageView = iSearchSkillPageView;
	}

	@Override
	public void refresh() {
		if (TextUtils.isEmpty(text)) {
			iSearchSkillPageView.refreshComplete(null);
		}

		page = 1;
		iSearchSkillPageView.showProgress();
		iSearchModel.searchGood(page, text, 2, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iSearchSkillPageView.hideProgress();
				if (data != null && data instanceof SearchGoodResultEntity) {
					SearchGoodResultEntity resultEntity = (SearchGoodResultEntity) data;
					if (null != resultEntity.getList() && !resultEntity.getList().isEmpty()) {
						iSearchSkillPageView.refreshComplete(resultEntity.getList());
						hasDate = resultEntity.isNext();
					} else {
						iSearchSkillPageView.refreshComplete(null);
                        iSearchSkillPageView.showToast("没有相关技能");
						hasDate = false;
					}

				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iSearchSkillPageView.hideProgress();
				iSearchSkillPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		page++;
		iSearchModel.searchGood(page, text, 2, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof SearchGoodResultEntity) {
					SearchGoodResultEntity resultEntity = (SearchGoodResultEntity) data;
					if (null != resultEntity.getList() && !resultEntity.getList().isEmpty()) {
						iSearchSkillPageView.loadMoreComplete(resultEntity.getList());
						hasDate = resultEntity.isNext();
					} else {
						iSearchSkillPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
						hasDate = false;
                        page--;
					}
				}
			}

			@Override
			public void onError(int errorCode, String message) {
                page--;
				iSearchSkillPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void setSearchText(String string) {
		this.text = string;
	}

	@Override
	public void OnClick(int position, int operation) {
		switch (operation) {
		case OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
			iSearchSkillPageView.jumpToSkillDetailPage(
					((SearchSkillFragment) iSearchSkillPageView).adapter.getData().get(position).id);
			break;
		case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
			iSearchSkillPageView.jumpToUserInfoDetailPage(
					((SearchSkillFragment) iSearchSkillPageView).adapter.getData().get(position).user_id);
			break;
		case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
            ChatPageEntity chatPageEntity = new ChatPageEntity(((SearchSkillFragment) iSearchSkillPageView).adapter.getData().get(position).user_id, ((SearchSkillFragment) iSearchSkillPageView).adapter.getData().get(position), ChatPageEntity.GOOD_TYPE_NEED);
			iSearchSkillPageView.jumpToHaveATalk(chatPageEntity);
			break;
		case OnItemClickListener.CLICK_OPERATION_SHARE:
			iSearchSkillPageView.share(((SearchSkillFragment) iSearchSkillPageView).adapter.getData().get(position));
			break;
		}
	}

}
