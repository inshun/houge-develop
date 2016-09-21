package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.INearbyModel;
import com.ysp.houge.model.entity.bean.BannerEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.NearbyListEntity;
import com.ysp.houge.model.entity.bean.NewJoinEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.impl.NearbyModelImpl;
import com.ysp.houge.presenter.INearbyNeedPresenter;
import com.ysp.houge.ui.iview.INearbyNeedPageView;
import com.ysp.houge.ui.nearby.NearbyNeedFragment;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class NearbyNeedPresenter implements INearbyNeedPresenter<List<GoodsDetailEntity>> {
	private int page = 0;
	private boolean hasData = true;

	private StringBuilder gps;
	private WorkTypeEntity workTypeEntity;
	private SortTypeEntity sortTypeEntity;
	private NewJoinEntity newJoinEntity;

	private INearbyModel iNearbyModel;
	// Banner集合
	private List<BannerEntity> bannerList = new ArrayList<BannerEntity>();

	private INearbyNeedPageView iNearbyNeedPageView;

	public NearbyNeedPresenter(INearbyNeedPageView iNearbyNeedPageView) {
		super();
		iNearbyModel = new NearbyModelImpl();
		this.iNearbyNeedPageView = iNearbyNeedPageView;
	}

	@Override
	public void refresh() {
		gps = new StringBuilder();
        // 拼接经纬度信息
        String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)){
            BDLocation location = new Gson().fromJson(local,BDLocation.class);
            gps.append(location.getLatitude());
            gps.append(",");
            gps.append(location.getLongitude());
            iNearbyNeedPageView.hideProgress();
        }else {
            //等待定位消息发送过来
            iNearbyNeedPageView.showProgress("定位中");
            iNearbyNeedPageView.refreshComplete(null);
            return;
        }

        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5){
            iNearbyNeedPageView.refreshComplete(null);
            iNearbyNeedPageView.showToast("信息异常");
            return;
        }

		page = 1;
        hasData = true;
		bannerList.clear();

		iNearbyModel.getNearbyNeedList(page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof NearbyListEntity) {
							NearbyListEntity entity = (NearbyListEntity) data;
							bannerList.addAll(entity.getBanner());
							List<GoodsDetailEntity> list = entity.getGoodsList();

							// 得到Banner集合,直接去刷新Banner
                            if (null != list && !list.isEmpty()){
							    iNearbyNeedPageView.initBanner(bannerList);
                            }
                            if (list.size() < 10)
								hasData = false;
                            else
                                hasData = true;
							iNearbyNeedPageView.refreshComplete(list);
						} else {
							hasData = false;
							iNearbyNeedPageView.initBanner(null);
							iNearbyNeedPageView.refreshComplete(null);
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						hasData = false;
						iNearbyNeedPageView.initBanner(null);
						iNearbyNeedPageView.refreshComplete(null);
					}
				});
	}

	@Override
	public void loadMore() {
        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5)
            return;
        page++;
		iNearbyModel.getNearbyNeedList(page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof NearbyListEntity) {
							NearbyListEntity entity = (NearbyListEntity) data;
							List<GoodsDetailEntity> list = entity.getGoodsList();
							// 得到了刷新的集合，可以直接处理
							if (null != list && !list.isEmpty()) {
								// 分页的ID
								page++;
								hasData = true;
								if (list.size() < 10)
									hasData = false;
								iNearbyNeedPageView.loadMoreComplete(list);
							} else {
                                page--;
								hasData = false;
								iNearbyNeedPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
							}
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						hasData = true;
                        page--;
						iNearbyNeedPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
					}
				});
	}

	@Override
	public boolean hasData() {
		return hasData;
	}

	@Override
	public void loadHeadData() {
		loadNewJoin(workTypeEntity.getId());
	}

	@Override
	public void setNearbyEntity(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity) {
		// TODO 设置当前页面关注点对象
		if (workTypeEntity != null) {
			this.workTypeEntity = workTypeEntity;
		}
		if (sortTypeEntity != null) {
			this.sortTypeEntity = sortTypeEntity;
			if (workTypeEntity == null) {
				refresh();
			}
		}
	}

	@Override
	public void newJion() {
		iNearbyNeedPageView.jumpToNewJoin(workTypeEntity);
	}

	@Override
	public void bannerClick(int index) {
		// 跳转页面
		iNearbyNeedPageView.jumpToBannerInfo(bannerList.get(index));
	}

	/** 最新加入的网络请求 */
	private void loadNewJoin(int id) {

		// 先获取缓存
		newJoinEntity = new Gson().fromJson(MyApplication.getInstance().getPreferenceUtils().getNewJoinNeed(),
				NewJoinEntity.class);
		// 不需要再次加载的情况（30分钟内加载过最新加入）
		if (null != newJoinEntity && !TextUtils.isEmpty(newJoinEntity.getDate())) {
			if (DateUtil.getDiff(newJoinEntity.getDate(), DateUtil.getCurrentDateTime()) > 60 * 30) {
				iNearbyNeedPageView.initNewJion(newJoinEntity.getCount());
				return;
			}
		}

		iNearbyModel.getNewJoinSkillCount(id, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (data != null) {
					try {
						int count = Integer.parseInt((String) data);
						// 成功了 缓存起来
						newJoinEntity = new NewJoinEntity(DateUtil.getCurrentDateTime(), count);

						MyApplication.getInstance().getPreferenceUtils()
								.setNewJoinNeed(new Gson().toJson(newJoinEntity));
					} catch (Exception e) {
						newJoinEntity = new NewJoinEntity("", 0);
					}
				} else {
					newJoinEntity = new NewJoinEntity("", 0);
				}
				iNearbyNeedPageView.initNewJion(newJoinEntity.getCount());
			}

			@Override
			public void onError(int errorCode, String message) {
				newJoinEntity = new NewJoinEntity("", 0);
				iNearbyNeedPageView.initNewJion(newJoinEntity.getCount());
			}
		});
	}

	@Override
	public void OnClick(int position, int operation) {
		// 列表的点击操作
		switch (operation) {
		// 用户详情
		case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
			iNearbyNeedPageView.jumpToUserInfo(
					((NearbyNeedFragment) iNearbyNeedPageView).adapter.getData().get(position).user_id);
			break;
		// 需求详情
		case OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
			iNearbyNeedPageView.jumpToNeedDetailPage(
					(int) ((NearbyNeedFragment) iNearbyNeedPageView).adapter.getItemId(position));
			break;
		// 聊一聊
		case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
            ChatPageEntity chatPageEntity=new ChatPageEntity(
                    ((NearbyNeedFragment) iNearbyNeedPageView).adapter.getData().get(position).user_id
                    ,((NearbyNeedFragment) iNearbyNeedPageView).adapter.getData().get(position)
                    ,ChatPageEntity.GOOD_TYPE_NEED);
            iNearbyNeedPageView.jumpToHaveATalk(chatPageEntity);
			break;
		// 分享
		case OnItemClickListener.CLICK_OPERATION_SHARE:
			iNearbyNeedPageView.share(((NearbyNeedFragment) iNearbyNeedPageView).adapter.getData().get(position));
			break;
		}
	}
}
