package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.bean.CommentResultEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.presenter.ICommentListPresenter;
import com.ysp.houge.ui.iview.ICommentListPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

public class CommentListPresenter implements ICommentListPresenter<List<CommentEntity>> {
	private int page = 1;
	private boolean hasDate = true;
	private List<CommentEntity> list = new ArrayList<CommentEntity>();

	private int orderId;
	private IGoodsDetailsModel iSkillDetailsModel;

	private ICommentListPageView iCommentListPageView;

	public CommentListPresenter(ICommentListPageView iCommentListPageView) {
		this.iCommentListPageView = iCommentListPageView;
		iSkillDetailsModel = new GoodsDetailsModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();
		iSkillDetailsModel.requestCommentList(orderId, page, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof CommentResultEntity) {
					CommentResultEntity resultEntity = (CommentResultEntity) data;
					hasDate = resultEntity.isNext();
					list = resultEntity.getList();
					iCommentListPageView.refreshComplete(list);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				hasDate = false;
				iCommentListPageView.refreshComplete(list);
			}
		});
	}

	@Override
	public void loadMore() {
        page++;
        list.clear();
		iSkillDetailsModel.requestCommentList(orderId, page, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof CommentResultEntity) {
					CommentResultEntity resultEntity = (CommentResultEntity) data;
					hasDate = resultEntity.isNext();
					list = resultEntity.getList();
					iCommentListPageView.loadMoreComplete(list);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				hasDate = false; page--;
				iCommentListPageView.loadMoreComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
