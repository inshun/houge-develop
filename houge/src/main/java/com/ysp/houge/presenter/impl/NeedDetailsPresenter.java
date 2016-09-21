package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.CommentResultEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.NeedEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.presenter.INeedDetailsPresenter;
import com.ysp.houge.ui.iview.INeedDetailsPageView;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

public class NeedDetailsPresenter implements INeedDetailsPresenter<List<GoodsDetailEntity>> {
	private int page = 1;
	private boolean hasDate = true;

	// 当前页面显示的详情对象requestReport
	private GoodsDetailEntity entity;
	private NeedEntity needEntity;
	private boolean isComment, isReport;
	private IGoodsDetailsModel iGoodsDetailsModel;
	private INeedDetailsPageView iNeedDetailsPageView;
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

	public NeedDetailsPresenter(INeedDetailsPageView iNeedDetailsPageView) {
		this.iNeedDetailsPageView = iNeedDetailsPageView;
		iGoodsDetailsModel = new GoodsDetailsModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		hasDate = true;
		list.clear();
		iGoodsDetailsModel.requestOtherList(page, entity.user_id, entity.id,
				new OnNetResponseCallback() {
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
						iNeedDetailsPageView.refreshComplete(list);
					}
					@Override
					public void onError(int errorCode, String message) {
						iNeedDetailsPageView.refreshComplete(null);
					}
				});
	}
	@Override
	public void loadMore() {
		page++;
		list.clear();
		iGoodsDetailsModel.requestOtherList(page, entity.user_id, entity.id,
				new OnNetResponseCallback() {
					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object data) {
						if (null != data && data instanceof List<?>) {
							list = (List<GoodsDetailEntity>) data;
							if (list.isEmpty()) {
								hasDate = false;
								LogUtil.setLogWithE("AA","false");
							} else {
								LogUtil.setLogWithE("AA","true");
								hasDate = true;
							}
						}
						iNeedDetailsPageView.loadMoreComplete(list);
					}
					@Override
					public void onError(int errorCode, String message) {
						page--;
						iNeedDetailsPageView.loadMoreComplete(list);
					}
				});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void more() {
		iNeedDetailsPageView.showMoreDialog();
	}

	@Override
	public void share() {
		iNeedDetailsPageView.share(entity);
	}


	@Override
	public void onMoreLoveClick() {
		iNeedDetailsPageView.jumpToMoreLove(entity.id);

	}

	@Override
	public void onMoreCommentClick() {
		iNeedDetailsPageView.jumpToMoreComment(entity.id);
	}

	@Override
	public void take() {
		//iNeedDetailsPageView.haveATalk(entity.user_id, 0);
	}

	@Override
	public void haveATalk() {
        ChatPageEntity chatPageEntity=new ChatPageEntity(entity.user_id,entity,ChatPageEntity.GOOD_TYPE_NEED);
		iNeedDetailsPageView.haveATalk(chatPageEntity, 1);
	}

	@Override
	public void callPhone() {
		iNeedDetailsPageView.jumpToCallPhone(entity.mobile);
	}

	@Override
	public void onShare() {
		iNeedDetailsPageView.share(entity);
	}

	@Override
	public void clickUser() {
		iNeedDetailsPageView.jumpToUserInfo(entity.user_id);
	}

	@Override
	public void clickItem(int id) {
		loadSkillDetail(id);
	}

	@Override
	public void loadSkillDetail(int id) {
		// TODO 加载需求详情
        iNeedDetailsPageView.showProgress();
		iGoodsDetailsModel.requestNeedDetaisl(id, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iNeedDetailsPageView.hideProgress();
				if (data != null && data instanceof GoodsDetailEntity) {
					entity = (GoodsDetailEntity) data;
					iNeedDetailsPageView.loadDitailFinish(entity);

                    //自己的需求隐藏售卖功能
					if (entity.user_id == MyApplication.getInstance().getCurrentUid()) {
						iNeedDetailsPageView.hideSell();
					}

                    //加载到需求详情之后
                    //      加载用户其他需求(需要用户id)
                    //      加载喜欢的列表(需要技能id)
                    //      加载留言列表(需要节能功能id)
					refresh();
                    loadLoveList(entity.id);
                    loadMessageList(entity.id);
				} else {
					iNeedDetailsPageView.showToast("没有相关需求信息");
					iNeedDetailsPageView.close();
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				iNeedDetailsPageView.hideProgress();
				iNeedDetailsPageView.showToast(R.string.request_failed);
				iNeedDetailsPageView.close();
			}
		});
	}

	@Override
	public void loadLoveList(int id) {
		 // 大于0才请求,节约网络消耗
		 if (entity.view_count == 0) {
		    iNeedDetailsPageView.loadZanFinish(null);
		    return;
		 }

		iNeedDetailsPageView.showProgress();
		iGoodsDetailsModel.requestLoveList(id, 0, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iNeedDetailsPageView.hideProgress();
                if (null != data && data instanceof List<?>) {
                    iNeedDetailsPageView.loadZanFinish((List<UserInfoEntity>) data);
                    return;
                }
				iNeedDetailsPageView.loadZanFinish(null);
			}

			@Override
			public void onError(int errorCode, String message) {
				iNeedDetailsPageView.hideProgress();
				iNeedDetailsPageView.loadZanFinish(null);
			}
		});
	}

	@Override
	public void loadMessageList(int id) {
		 // 大于0才请求,节约网络消耗
		 if (entity.comment_count == 0) {
		    iNeedDetailsPageView.loadCommentFinish(null);
		    return;
		 }

		iNeedDetailsPageView.showProgress();
		iGoodsDetailsModel.requestCommentList(id, 0, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				iNeedDetailsPageView.hideProgress();
				if (null != data && data instanceof CommentResultEntity) {
					CommentResultEntity resultEntity = (CommentResultEntity) data;
					iNeedDetailsPageView.loadCommentFinish(resultEntity.getList());
					return;
				}
				iNeedDetailsPageView.loadCommentFinish(null);
			}

			@Override
			public void onError(int errorCode, String message) {
				iNeedDetailsPageView.hideProgress();
				iNeedDetailsPageView.loadCommentFinish(null);
			}
		});
	}

	@Override
	public void onLevelMessage(String message) {
		if (TextUtils.isEmpty(message)) {
			iNeedDetailsPageView.showToast("请输入留言内容");
            return;
		} else if (null == MyApplication.getInstance().getUserInfo()) {
			iNeedDetailsPageView.jumpToLogin();
            return;
		} else {
			if (isComment) {
				iNeedDetailsPageView.showToast("请勿重复操作");
                return;
			}
			// 发布留言
			iNeedDetailsPageView.showProgress();
			iGoodsDetailsModel.requestMessageAdd(entity.id, message, null, new OnNetResponseCallback() {

				@Override
				public void onSuccess(Object data) {
					iNeedDetailsPageView.hideProgress();
					isComment = true;
					iNeedDetailsPageView.showToast("评论成功");
                    entity.comment_count += 1;
                    loadMessageList(entity.id);
				}

				@Override
				public void onError(int errorCode, String message) {
					iNeedDetailsPageView.hideProgress();
					iNeedDetailsPageView.showToast("网络错误");
				}
			});
		}
	}

	@Override
	public void requestReport(int reportType) {
		if (isReport) {
			iNeedDetailsPageView.showToast("请勿重复操作");
            return;
		} else if (null == MyApplication.getInstance().getUserInfo()) {
			iNeedDetailsPageView.jumpToLogin();
            return;
		}

		iNeedDetailsPageView.showProgress();
		iGoodsDetailsModel.requestReport(IGoodsDetailsModel.REPORT_TYPE_NEED, entity.id, reportType, entity.title, new OnNetResponseCallback()
		{
			@Override
			public void onSuccess(Object data) {
				isReport = true;
				iNeedDetailsPageView.hideProgress();
				iNeedDetailsPageView.showToast("举报成功");
			}

			@Override
			public void onError(int errorCode, String message) {
				iNeedDetailsPageView.hideProgress();
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					iNeedDetailsPageView.showToast(message);
					break;
				default:
					iNeedDetailsPageView.showToast(R.string.request_failed);
					break;
				}
			}
		});
	}

	@Override
	public void onClickRecord(boolean isRecord) {
		// 录音接口回调
	}
}