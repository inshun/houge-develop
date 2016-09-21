package com.ysp.houge.presenter.impl;

import android.view.View;
import android.widget.AdapterView;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserContorller;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.MyNeedEntity;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.IUserDetailsPresenter;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IUserDetailsPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 用户详情以及技能列表页面Presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月15日下午2:38:32
 * @version 1.0
 */
public class UserDetailsPresenter implements IUserDetailsPresenter<List<GoodsDetailEntity>> {
	private int page = 1;
	private boolean hasData = false;

	private int user_id = -1;
	private int TAG;
	private boolean isReport = false;
	private UserInfoEntity infoEntity;
	private IUserInfoModel iUserInfoModel;
	private IGoodsDetailsModel iGoodsDetailsModel;
	private IUserDetailsPageView iUserDetailsPageView;
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

	public UserDetailsPresenter(IUserDetailsPageView iUserDetailsPageView) {
		super();
		iUserInfoModel = new UserInfoModelImpl();
		iGoodsDetailsModel = new GoodsDetailsModelImpl();
		this.iUserDetailsPageView = iUserDetailsPageView;
	}

	@Override
	public void refresh() {
		if (user_id == -1)
			iUserDetailsPageView.close();
		switch (TAG) {
		case MyApplication.LOG_STATUS_BUYER:
			UserSkill(true);
			break;

		case MyApplication.LOG_STATUS_SELLER:
			UserNeed(true);
			break;
		}
	}

	@Override
	public void loadMore() {
		switch (TAG) {
		case MyApplication.LOG_STATUS_BUYER:
			UserSkill(false);
			break;

		case MyApplication.LOG_STATUS_SELLER:
			UserNeed(false);
			break;
		}
	}

	@Override
	public boolean hasData() {
		return hasData;
	}

	@Override
	public void setTAG(int TAG) {
		iUserDetailsPageView.swithListType(TAG);
		this.TAG = TAG;
		refresh();
	}

    @Override
    public void shareUser() {
        iUserDetailsPageView.share(infoEntity);
    }

    @Override
	public void setUserId(int id) {
		this.user_id = id;
	}

	@Override
	public void LoadUserInfo() {
		iUserDetailsPageView.showProgress();
		iUserInfoModel.getOtherUserInfo(user_id, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iUserDetailsPageView.hideProgress();
				infoEntity = (UserInfoEntity) data;
				if (null != infoEntity) {
					iUserDetailsPageView.showUserInfo(infoEntity);

					// 保存用户信息
					UserEntity user = new UserEntity(infoEntity.id, infoEntity.nick, infoEntity.avatar, infoEntity.sex);
					UserContorller.createOrUpdate(user);
				} else {
					iUserDetailsPageView.showToast("没有相关用户信息");
					iUserDetailsPageView.close();
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iUserDetailsPageView.hideProgress();
                iUserDetailsPageView.close();
				iUserDetailsPageView.showToast(R.string.request_failed);
			}
		});
	}

	@Override
	public void requestReport(final int reportType) {
		if (isReport) {
			iUserDetailsPageView.showToast("请勿重复操作");
			return;
		}
		iGoodsDetailsModel.requestReport(iGoodsDetailsModel.REPORT_TYPE_USER, user_id, reportType, infoEntity.username, new OnNetResponseCallback() {
			@Override
			public void onSuccess(Object data) {
				isReport = true;
				iUserDetailsPageView.showToast("举报成功");
			}
			@Override
			public void onError(int errorCode, String message) {
				iUserDetailsPageView.showToast(R.string.request_failed);
			}
		});
	}

	/** 用户技能 */
	private void UserSkill(final boolean isRefresh) {
		hasData = true;
		list.clear();
		if (isRefresh) {
			page = 1;
		} else {
			page++;
		}

		iUserInfoModel.getSkillList(page, user_id, "", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>) {
                    list = (List<GoodsDetailEntity>) data;
                    if (list.isEmpty()) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }

                    if (isRefresh) {
                        iUserDetailsPageView.refreshComplete(list);
                    } else {
                        iUserDetailsPageView.loadMoreComplete(list);
                    }
                }
			}

			@Override
			public void onError(int errorCode, String message) {
				if (isRefresh) {
					iUserDetailsPageView.refreshComplete(list);
				} else {
					iUserDetailsPageView.loadMoreComplete(list);
				}
			}
		});
	}

	/** 用户的需求 */
	private void UserNeed(final boolean isRefresh) {
		hasData = true;
		list.clear();
		if (isRefresh) {
			page = 1;
		} else {
			page++;
		}

		iUserInfoModel.getNeedList(page, user_id, "", new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
                if (null != data && data instanceof MyNeedEntity) {
                    list = ((MyNeedEntity) data).getList();
                    if (list.isEmpty()) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }

                    if (isRefresh) {
                        iUserDetailsPageView.refreshComplete(list);
                    } else {
                        iUserDetailsPageView.loadMoreComplete(list);
                    }
                }
			}

			@Override
			public void onError(int errorCode, String message) {
				if (isRefresh) {
					iUserDetailsPageView.refreshComplete(list);
				} else {
					iUserDetailsPageView.loadMoreComplete(list);
				}
			}
		});
	}

	@Override
	public void clickItem(AdapterView<?> parent, View view, int position, long id) {
		if (id < 0) {
			return;
		}

		switch (TAG) {
		case MyApplication.LOG_STATUS_BUYER:
			iUserDetailsPageView.jumpToSkillDetails((int) id);
			break;

		case MyApplication.LOG_STATUS_SELLER:
			iUserDetailsPageView.jumpToNeedDetails((int) id);
			break;
		}
	}

	@Override
	public void OnClick(int position, int operation) {
		GoodsDetailEntity entity = null;
        int goodType=-1;
		switch (TAG) {
		case MyApplication.LOG_STATUS_BUYER:
            goodType=ChatPageEntity.GOOD_TYPE_SKILL;
			entity = ((UserDetailsActivity) iUserDetailsPageView).skillAdapter.getData().get(position);
			break;
		case MyApplication.LOG_STATUS_SELLER:
            goodType=ChatPageEntity.GOOD_TYPE_NEED;
			entity = ((UserDetailsActivity) iUserDetailsPageView).needAdapter.getData().get(position);
			break;
		default:
			break;
		}

		switch (operation) {
		case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
            ChatPageEntity chatPageEntity=new ChatPageEntity(entity.user_id,entity,goodType);
            iUserDetailsPageView.haveATalk(chatPageEntity);
            break;
            case OnItemClickListener.CLICK_OPERATION_SHARE:
                iUserDetailsPageView.share(entity);
                break;
		default:
			break;
		}
	}

}
