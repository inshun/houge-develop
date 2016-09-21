package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.INeedFootprintPresenter;
import com.ysp.houge.ui.iview.INeedFootprintPageView;
import com.ysp.houge.ui.me.footprint.NeedFootprintFragment;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @author it_huang
 *
 *         需求足迹presenter层
 */
public class NeedFootprintPresenter implements INeedFootprintPresenter<List<GoodsDetailEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private IUserInfoModel iUserInfoModel;
	private INeedFootprintPageView iNeedFootprintPageView;
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

	public NeedFootprintPresenter(INeedFootprintPageView iNeedFootprintPageView) {
		super();
		iUserInfoModel = new UserInfoModelImpl();
		this.iNeedFootprintPageView = iNeedFootprintPageView;
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iUserInfoModel.myFootprintNeed(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					list = (List<GoodsDetailEntity>) data;
					if (list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iNeedFootprintPageView.refreshComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				iNeedFootprintPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		page++;
		list.clear();
		iUserInfoModel.myFootprintNeed(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof List<?>) {
					list = (List<GoodsDetailEntity>) data;
					if (list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iNeedFootprintPageView.loadMoreComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				page--;
				iNeedFootprintPageView.refreshComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void OnClick(int position, int operation) {
		switch (operation) {
		case OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
			iNeedFootprintPageView.jumpToNeedDetails(
					(int) ((NeedFootprintFragment) iNeedFootprintPageView).adapter.getItemId(position));
			break;
		case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
			iNeedFootprintPageView.jumpToUserDetails(
					((NeedFootprintFragment) iNeedFootprintPageView).adapter.getData().get(position).user_id);
			break;
		case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
            ChatPageEntity chatPageEntity = new ChatPageEntity(
                    ((NeedFootprintFragment) iNeedFootprintPageView).adapter.getData().get(position).user_id,
                    ((NeedFootprintFragment) iNeedFootprintPageView).adapter.getData().get(position),
                    ChatPageEntity.GOOD_TYPE_NEED);
			iNeedFootprintPageView.jumpToHaveATalk(chatPageEntity);
			break;
		case OnItemClickListener.CLICK_OPERATION_SHARE:
			iNeedFootprintPageView
					.share(((NeedFootprintFragment) iNeedFootprintPageView).adapter.getData().get(position));
			break;
		}
	}

}
