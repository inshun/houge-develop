package com.ysp.houge.presenter.impl.recmmend;

import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.IRecommendModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.impl.RecommendModleImpl;
import com.ysp.houge.presenter.IRecommandNeedPresenter;
import com.ysp.houge.ui.iview.IRecommandNeedPageView;
import com.ysp.houge.ui.recommend.RecommendNeedFragment;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 卖家推荐列表presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午4:05:47
 * @version 1.0
 */
public class RecommendNeedPresenter implements IRecommandNeedPresenter<List<GoodsDetailEntity>> {
	private int id = 0;
    private int page=0;
	private boolean hasData = true;
	private IRecommendModel iRecommendModel;

	private StringBuilder gps;
	private WorkTypeEntity workTypeEntity;// 当前页面的关注点实体
	private SortTypeEntity sortTypeEntity;// 当前排序类型的实体
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
	private IRecommandNeedPageView iRecommandNeedPageView;

	public RecommendNeedPresenter(IRecommandNeedPageView iSellerRecommandListPageView) {
		super();
		this.iRecommandNeedPageView = iSellerRecommandListPageView;
		iRecommendModel = new RecommendModleImpl();
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
            iRecommandNeedPageView.hideProgress();
        }else {
            //等待定位消息发送过来
            iRecommandNeedPageView.showProgress("定位中");
            iRecommandNeedPageView.refreshComplete(null);
            return;
        }

		id = 0;
        page = 1;
		list.clear();

        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5)
            return;
		iRecommendModel.getRecommendList(id,page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
				new OnNetResponseCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof List<?>) {
							list.addAll((ArrayList<GoodsDetailEntity>) data);
							iRecommandNeedPageView.refreshComplete(list);
							if (list.isEmpty() || list.size() < 10) {
								hasData = false;
							} else {
								hasData = true;
								// 记录分页的id
								id = list.get(list.size() - 1).id;
							}
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						hasData = false;
						iRecommandNeedPageView.refreshComplete(null);
					}
				});
	}

	@Override
	public void loadMore() {
        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5)
            return;
        page++;
		list.clear();
		iRecommendModel.getRecommendList(id,page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
				new OnNetResponseCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object data) {
						if (data != null && data instanceof List<?>) {
							list.addAll((ArrayList<GoodsDetailEntity>) data);
							iRecommandNeedPageView.loadMoreComplete(list);
							if (list.isEmpty() || list.size() < 10) {
								hasData = false;
							} else {
								hasData = true;
								// 记录分页的id
								id = list.get(list.size() - 1).id;
							}
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						hasData = true;
                        page--;
						iRecommandNeedPageView.refreshComplete(list);
					}
				});
	}

	@Override
	public boolean hasData() {
		return hasData;
	}

	@Override
	public void setRecommendAndSortTypeEntity(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity) {
		// TODO 设置当前页面的关注点、排序类型
		if (workTypeEntity != null) {
			this.workTypeEntity = workTypeEntity;
		}
		if (sortTypeEntity != null) {
			this.sortTypeEntity = sortTypeEntity;
			if (sortTypeEntity != null && workTypeEntity == null) {
				refresh();
			}
		}
	}

	@Override
	public void OnClick(int position, int operation) {
		switch (operation) {
		case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
			iRecommandNeedPageView.jumpToUserInfoDetailPage(
					((RecommendNeedFragment) iRecommandNeedPageView).adapter.getData().get(position).user_id);
			break;
		case OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
			iRecommandNeedPageView.jumpToNeedDetailPage(
					((RecommendNeedFragment) iRecommandNeedPageView).adapter.getData().get(position).id);
			break;
		case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
            ChatPageEntity chatPageEntity=new ChatPageEntity(
                    ((RecommendNeedFragment) iRecommandNeedPageView).adapter.getData().get(position).user_id
            ,((RecommendNeedFragment) iRecommandNeedPageView).adapter.getData().get(position)
            ,ChatPageEntity.GOOD_TYPE_NEED);
			iRecommandNeedPageView.jumpToHaveATalk(chatPageEntity);
			break;
		case OnItemClickListener.CLICK_OPERATION_SHARE:
			iRecommandNeedPageView
					.share(((RecommendNeedFragment) iRecommandNeedPageView).adapter.getData().get(position));
			break;
		}
	}
}
