package com.ysp.houge.presenter.impl.recmmend;

import android.text.TextUtils;
import android.util.Log;

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
import com.ysp.houge.presenter.IRecommandSkillPresenter;
import com.ysp.houge.ui.iview.IRecommandSkillPageView;
import com.ysp.houge.ui.recommend.RecommendSkillFragment;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tyn
 * @version 1.0
 * @描述:买家推荐列表presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * @date 2015年7月13日下午5:37:55
 */
public class RecommendSkillPresenter implements IRecommandSkillPresenter<List<GoodsDetailEntity>> {
    private final String TAG = getClass().getSimpleName();
    private int id = 0;
    private int page = 0;
    private boolean hasData = true;
    private IRecommendModel iRecommendModel;

    private StringBuilder gps;
    private WorkTypeEntity workTypeEntity;// 当前页面的关注点实体
    private SortTypeEntity sortTypeEntity;// 当前排序类型的实体
    private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
    private IRecommandSkillPageView iBuyerRecommandPageView;

    public RecommendSkillPresenter(IRecommandSkillPageView iBuyerRecommandPageView) {
        super();
        this.iBuyerRecommandPageView = iBuyerRecommandPageView;
        iRecommendModel = new RecommendModleImpl();
    }


    @Override
    public void loadData() {
        gps = new StringBuilder();
        // 拼接经纬度信息
        final String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)) {
            BDLocation location = new Gson().fromJson(local, BDLocation.class);
            gps.append(location.getLatitude());
            gps.append(",");
            gps.append(location.getLongitude());
            iBuyerRecommandPageView.hideProgress();
        } else {
            //等待定位消息发送过来
            iBuyerRecommandPageView.showProgress("定位中");
            iBuyerRecommandPageView.refreshComplete(null);
            return;
        }

        id = 0;
        page = 1;
        list.clear();

        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5)
            return;
        iRecommendModel.getRecommendListFromLocal(id, page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
                new OnNetResponseCallback() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(Object data) {
                        boolean b = data instanceof List<?>;
                        LogUtil.deBug(TAG, "data != null:" + (data != null), 1);
                        LogUtil.deBug(TAG, "data instance of list:" + b, 1);

                        if (data != null && data instanceof List<?>) {
                            LogUtil.deBug(TAG, "走本地方法",1);

                            list.addAll((ArrayList<GoodsDetailEntity>) data);
                            iBuyerRecommandPageView.refreshComplete(list);
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
                        LogUtil.deBug(TAG, message, 1);
                        hasData = false;
                        iBuyerRecommandPageView.refreshComplete(null);
                    }
                });
    }

    @Override
    public void refresh() {

        gps = new StringBuilder();
        // 拼接经纬度信息
        final String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)) {
            BDLocation location = new Gson().fromJson(local, BDLocation.class);
            gps.append(location.getLatitude());
            gps.append(",");
            gps.append(location.getLongitude());
            iBuyerRecommandPageView.hideProgress();
        } else {
            //等待定位消息发送过来
            iBuyerRecommandPageView.showProgress("定位中");
            iBuyerRecommandPageView.refreshComplete(null);
            return;
        }

        id = 0;
        page = 1;
        list.clear();

        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5)
            return;
        iRecommendModel.getRecommendList(id, page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
                new OnNetResponseCallback() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(Object data) {
                        if (data != null && data instanceof List<?>) {
                            list.addAll((ArrayList<GoodsDetailEntity>) data);
                            iBuyerRecommandPageView.refreshComplete(list);
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
                        LogUtil.deBug(TAG, message, 1);
                        hasData = false;
                        iBuyerRecommandPageView.refreshComplete(null);
                    }
                });
    }

    @Override
    public void loadMore() {
        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5)
            return;
        page++;
        list.clear();
        iRecommendModel.getRecommendList(id, page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
                new OnNetResponseCallback() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(Object data) {
                        if (data != null && data instanceof List<?>) {
                            list.addAll((ArrayList<GoodsDetailEntity>) data);
                            iBuyerRecommandPageView.loadMoreComplete(list);
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
                        iBuyerRecommandPageView.refreshComplete(null);
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
        List<GoodsDetailEntity> list = ((RecommendSkillFragment) iBuyerRecommandPageView).adapter.getData();
        // 列表的点击操作
        switch (operation) {
            // 用户详情
            case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
                iBuyerRecommandPageView.jumpToUserInfoDetailPage(list.get(position).user_id);
                break;
            // 技能详情
            case OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
                iBuyerRecommandPageView.jumpToSkillDetailPage(list.get(position).id);
                break;
            // 聊一聊
            case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
                ChatPageEntity entity = new ChatPageEntity(list.get(position).user_id, list.get(position), ChatPageEntity.GOOD_TYPE_SKILL);
                iBuyerRecommandPageView.jumpToHaveATalk(entity);
                break;
            // 分享
            case OnItemClickListener.CLICK_OPERATION_SHARE:
                iBuyerRecommandPageView.share(list.get(position));
                break;
        }
    }
}
