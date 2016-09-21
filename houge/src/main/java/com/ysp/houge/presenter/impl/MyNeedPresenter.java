package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.MyNeedEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.IMyNeedPresenter;
import com.ysp.houge.ui.iview.IMyNeedPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class MyNeedPresenter implements IMyNeedPresenter<List<GoodsDetailEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private IUserInfoModel iUserInfoModel;
	private IMyNeedPageView iMyNeedPageView;
	private IGoodsDetailsModel iGoodsDetailsModel;
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

	public MyNeedPresenter(IMyNeedPageView iMyNeedPageView) {
		super();
		this.iMyNeedPageView = iMyNeedPageView;
		iUserInfoModel = new UserInfoModelImpl();
		iGoodsDetailsModel = new GoodsDetailsModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iUserInfoModel.getNeedList(page, MyApplication.getInstance().getCurrentUid(), "", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof MyNeedEntity) {
                    MyNeedEntity entity = (MyNeedEntity) data;
                    list = entity.getList();
                    if (list.isEmpty()) {
                        hasDate = false;
                    } else {
                        hasDate = true;
                    }
                }
                iMyNeedPageView.refreshComplete(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                iMyNeedPageView.refreshComplete(null);
            }
        });
	}

	@Override
	public void loadMore() {
		page++;
		list.clear();
		iUserInfoModel.getNeedList(page, MyApplication.getInstance().getCurrentUid(), "",new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof MyNeedEntity) {
					MyNeedEntity entity = (MyNeedEntity) data;
					list = entity.getList();
					if (null == list || list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iMyNeedPageView.loadMoreComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				hasDate = true;
				page--;
				iMyNeedPageView.loadMoreComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void serviceClick() {
		iMyNeedPageView.showCallPhonePop();
	}

	@Override
	public void OnClick(int position, int operation) {
		if (operation == OnItemClickListener.CLICK_OPERATION_DELETE) {
			iMyNeedPageView.showProgress();
			iGoodsDetailsModel.requestDeleteGoods(position, new OnNetResponseCallback() {

				@Override
				public void onSuccess(Object data) {
					iMyNeedPageView.hideProgress();
					refresh();
					iMyNeedPageView.showToast("删除成功");
				}

				@Override
				public void onError(int errorCode, String message) {
					iMyNeedPageView.hideProgress();
					iMyNeedPageView.showToast(R.string.request_failed);
				}
			});
		}else if (operation == OnItemClickListener.CLICK_OPERATION_NEED_DETAIL){
            iMyNeedPageView.jumpToNeedDetails(position);
        }
	}
}
