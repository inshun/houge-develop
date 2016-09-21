package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.ISkillFootprintPresenter;
import com.ysp.houge.ui.iview.ISkillFootprintPageView;
import com.ysp.houge.ui.me.footprint.SkillFootprintFragment;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

public class SkillFootprintPresenter implements ISkillFootprintPresenter<List<GoodsDetailEntity>> {
	private int page = 0;
	private boolean hasDate = true;
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

	private ISkillFootprintPageView iSkillFootprintPageView;
	private IUserInfoModel iUserInfoModel;

	public SkillFootprintPresenter(ISkillFootprintPageView iSkillFootprintPageView) {
		super();
		this.iSkillFootprintPageView = iSkillFootprintPageView;
		iUserInfoModel = new UserInfoModelImpl();
	}

	@Override
	public void refresh() {
		page = 1;
		list.clear();
		hasDate = true;
		iUserInfoModel.myFootprintSkill(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					list = (List<GoodsDetailEntity>) data;
					if (list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iSkillFootprintPageView.refreshComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				iSkillFootprintPageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {
		list.clear();
		page++;

		iUserInfoModel.myFootprintSkill(page, new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					list = (List<GoodsDetailEntity>) data;
					if (list.isEmpty()) {
						hasDate = false;
					} else {
						hasDate = true;
					}
				}
				iSkillFootprintPageView.loadMoreComplete(list);
			}

			@Override
			public void onError(int errorCode, String message) {
				iSkillFootprintPageView.loadMoreComplete(list);
			}
		});
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

	@Override
	public void OnClick(int position, int operation) {
		// 列表的点击操作
		switch (operation) {
		// 用户详情
		case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
			iSkillFootprintPageView.jumpToUserInfo(
					((SkillFootprintFragment) iSkillFootprintPageView).adapter.getData().get(position).user_id);
			break;
		// 技能详情
		case OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
			iSkillFootprintPageView.jumpToSkillDetailPage(
					(int) ((SkillFootprintFragment) iSkillFootprintPageView).adapter.getItemId(position));
			break;
		// 聊一聊
		case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
            ChatPageEntity chatPageEntity = new ChatPageEntity(
                    ((SkillFootprintFragment) iSkillFootprintPageView).adapter.getData().get(position).user_id,
                    ((SkillFootprintFragment) iSkillFootprintPageView).adapter.getData().get(position),
                    ChatPageEntity.GOOD_TYPE_SKILL);
			iSkillFootprintPageView.jumpToHaveATalk(chatPageEntity);
			break;
		// 分享
		case OnItemClickListener.CLICK_OPERATION_SHARE:
			iSkillFootprintPageView
					.share( ((SkillFootprintFragment) iSkillFootprintPageView).adapter.getData().get(position));
			break;
		}
	}

}
