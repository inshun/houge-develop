package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.IMySkillPresenter;
import com.ysp.houge.ui.iview.IMySkillPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class MySkillPresenter implements IMySkillPresenter<List<GoodsDetailEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	private IUserInfoModel iUserInfoModel;
	private IMySkillPageView iMySkillPageView;
	private IGoodsDetailsModel iGoodsDetailsModel;
	private UserInfoEntity userInfoEntity;

	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

	public MySkillPresenter(IMySkillPageView iMySkillPageView) {
		super();
		iUserInfoModel = new UserInfoModelImpl();
		this.iMySkillPageView = iMySkillPageView;
		 userInfoEntity = new UserInfoEntity();
		iGoodsDetailsModel = new GoodsDetailsModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();

		iUserInfoModel.getSkillList(page, MyApplication.getInstance().getCurrentUid(),"",  new OnNetResponseCallback() {

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
                iMySkillPageView.refreshComplete(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                hasDate = true;
                iMySkillPageView.refreshComplete(null);
            }
        });
	}

	@Override
	public void loadMore() {
		page++;
		list.clear();

		iUserInfoModel.getSkillList(page, MyApplication.getInstance().getCurrentUid(),"",  new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>) {
                    list = (List<GoodsDetailEntity>) data;
                    if (list.isEmpty()) {
                        hasDate = false;
                        page--;
                    } else {
                        hasDate = true;
                    }
                }
                iMySkillPageView.loadMoreComplete(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                page--;
                hasDate = true;
                iMySkillPageView.loadMoreComplete(null);
            }
        });
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void delete(int id) {
		iMySkillPageView.showProgress();
		iGoodsDetailsModel.requestDeleteGoods(id, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iMySkillPageView.hideProgress();
				iMySkillPageView.showToast("删除成功");
				refresh();
			}

			@Override
			public void onError(int errorCode, String message) {
				iMySkillPageView.hideProgress();
				iMySkillPageView.showToast(R.string.request_failed);
			}
		});
	}
}
