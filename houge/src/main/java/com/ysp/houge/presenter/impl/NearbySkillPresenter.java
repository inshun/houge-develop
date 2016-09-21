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
import com.ysp.houge.presenter.INearbySkillPresenter;
import com.ysp.houge.ui.iview.INearbySkillPageView;
import com.ysp.houge.ui.nearby.NearbySkillFragment;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 附近技能列表Presenter层
 *
 * @ClassName: NearbySkillPresenter
 * @author: hx
 * @date: 2015年12月4日 下午2:35:00
 * <p/>
 * 版本: 1.0
 */
public class NearbySkillPresenter implements INearbySkillPresenter<List<GoodsDetailEntity>> {
    private int page = 0;
    private boolean hasData = true;

    private StringBuilder gps;
    private WorkTypeEntity workTypeEntity;
    private SortTypeEntity sortTypeEntity;

    private INearbyModel iNearbyModel;
    private INearbySkillPageView iNearbySkillPageView;

    private NewJoinEntity newJoinEntity;
    // Banner集合
    private List<BannerEntity> bannerList = new ArrayList<BannerEntity>();
    private List<GoodsDetailEntity> topSkill = new ArrayList<GoodsDetailEntity>();

    public NearbySkillPresenter(INearbySkillPageView iNearbyListPageView) {
        super();
        iNearbyModel = new NearbyModelImpl();
        this.iNearbySkillPageView = iNearbyListPageView;
    }

    @Override
    public void refresh() {
        gps = new StringBuilder();
        // 拼接经纬度信息
        String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)) {
            BDLocation location = new Gson().fromJson(local, BDLocation.class);
            gps.append(location.getLatitude());
            gps.append(",");
            gps.append(location.getLongitude());
            iNearbySkillPageView.hideProgress();
        } else {
            //等待定位消息发送过来
            iNearbySkillPageView.showProgress("定位中");
            iNearbySkillPageView.refreshComplete(null);
            return;
        }

        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5) {
            iNearbySkillPageView.refreshComplete(null);
            iNearbySkillPageView.showToast("信息异常");
            return;
        }

        page = 1;
        hasData = true;
        topSkill.clear();
        bannerList.clear();

        iNearbyModel.getNearbySkillList(page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
                new OnNetResponseCallback() {

                    @Override
                    public void onSuccess(Object data) {
                        if (data != null && data instanceof NearbyListEntity) {
                            NearbyListEntity entity = (NearbyListEntity) data;
                            bannerList.addAll(entity.getBanner());
                            List<GoodsDetailEntity> list = entity.getGoodsList();

                            // 得到Banner集合,直接去刷新Banner
                            iNearbySkillPageView.initBanner(bannerList);

                            //顶部隐藏改写 start
                            iNearbySkillPageView.initNearby(null);

                            if (null == list || list.isEmpty()) {
                                hasData = false;
                                iNearbySkillPageView.refreshComplete(null);
                            } else {
                                if (list.size() < 10)
                                    hasData = false;
                                iNearbySkillPageView.refreshComplete(list);
                            }
                            //顶部隐藏改写 end

//							// 返回的集合为空那么顶部的是个以及列表都刷新为空
//							if (null == list || list.isEmpty()) {
//                                hasData = false;
//								iNearbySkillPageView.initNearby(null);
//								iNearbySkillPageView.refreshComplete(null);
//							} else {
//                                if (list.size() < 10 )
//                                    hasData = false;
//								// 小于5代表只有四个(只刷新上方网格列表)
//								List<GoodsDetailEntity> lists = new ArrayList<GoodsDetailEntity>();
//
//								if (list.size() <= 4) {
//                                    //小于四个的话，不刷新
//									//topSkill.addAll(list);
//									//iNearbySkillPageView.initNearby(topSkill);
//									iNearbySkillPageView.refreshComplete(null);
//									// 分页的ID
//									//page = topSkill.get(topSkill.size() - 1).id;
//								} else {
//									// 多于四个的处理情况(前四个是顶部网格，后面的是底部的列表)
//									for (int i = 0; i < list.size(); i++) {
//										// 0,1,2,3
//										if (i < 4) {
//											topSkill.add(list.get(i));
//										} else {
//											lists.add(list.get(i));
//                                        }
//									}
//									// 分页的ID
//									page++;
//									iNearbySkillPageView.initNearby(topSkill);
//									iNearbySkillPageView.refreshComplete(lists);
//								}
//							}
                        } else {
                            hasData = false;
                            iNearbySkillPageView.initBanner(null);
                            iNearbySkillPageView.refreshComplete(null);
                        }
                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        hasData = false;
                        iNearbySkillPageView.initBanner(null);
                        iNearbySkillPageView.initNearby(null);
                        iNearbySkillPageView.refreshComplete(null);
                    }
                });
    }

    @Override
    public void loadMore() {
        if (null == workTypeEntity || null == sortTypeEntity || gps.length() < 5) {
            iNearbySkillPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
            iNearbySkillPageView.showToast("信息异常");
            return;
        }

        page++;
        iNearbyModel.getNearbySkillList(page, workTypeEntity.getId(), sortTypeEntity.id, gps.toString(),
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
                                iNearbySkillPageView.loadMoreComplete(list);
                                if (list.size() < 10)
                                    hasData = false;
                                else
                                    hasData = true;
                            } else {
                                hasData = false;
                                iNearbySkillPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        page--;
                        hasData = true;
                        iNearbySkillPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
                    }
                });
    }

    @Override
    public boolean hasData() {
        return hasData;
    }

    @Override
    public void loadHeadData() {
        // TODO 加载头部数据
        if (workTypeEntity != null) {
            loadNewJoin(workTypeEntity.getId());

        }
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
        iNearbySkillPageView.jumpToNewJoin(workTypeEntity);
    }

    @Override
    public void genius(int index) {
        // 跳转对应页面(遵循推送协议的)
        iNearbySkillPageView.jumpToBannerInfo(bannerList.get(index));
    }

    @Override
    public void recommned(int index) {
        if (topSkill.size() > index) {
            iNearbySkillPageView.jumpToSkillDetailPage(topSkill.get(index).id);
        }
    }

    /**
     * 最新加入的网络请求
     */
    private void loadNewJoin(int id) {

        // 先获取缓存
        newJoinEntity = new Gson().fromJson(MyApplication.getInstance().getPreferenceUtils().getNewJoinSkill(),
                NewJoinEntity.class);
        // 不需要再次加载的情况（30分钟内加载过最新加入）
        if (null != newJoinEntity && !TextUtils.isEmpty(newJoinEntity.getDate())) {
            if (DateUtil.getDiff(newJoinEntity.getDate(), DateUtil.getCurrentDateTime()) > 60 * 30) {
                iNearbySkillPageView.initNewJion(newJoinEntity.getCount());
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
                                .setNewJoinSkill(new Gson().toJson(newJoinEntity));
                    } catch (Exception e) {
                        newJoinEntity = new NewJoinEntity("", 0);
                    }
                } else {
                    newJoinEntity = new NewJoinEntity("", 0);
                }
                iNearbySkillPageView.initNewJion(newJoinEntity.getCount());
            }

            @Override
            public void onError(int errorCode, String message) {
                newJoinEntity = new NewJoinEntity("", 0);
                iNearbySkillPageView.initNewJion(newJoinEntity.getCount());
            }
        });
    }

    @Override
    public void OnClick(int position, int operation) {
        // 列表的点击操作
        switch (operation) {
            // 用户详情
            case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
                iNearbySkillPageView.jumpToUserInfo(
                        ((NearbySkillFragment) iNearbySkillPageView).adapter.getData().get(position).user_id);
                break;
            // 技能详情
            case OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
                iNearbySkillPageView.jumpToSkillDetailPage(
                        (int) ((NearbySkillFragment) iNearbySkillPageView).adapter.getItemId(position));
                break;
            // 聊一聊
            case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
                ChatPageEntity chatPageEntity = new ChatPageEntity(
                        ((NearbySkillFragment) iNearbySkillPageView).adapter.getData().get(position).user_id
                        , ((NearbySkillFragment) iNearbySkillPageView).adapter.getData().get(position)
                        , ChatPageEntity.GOOD_TYPE_SKILL);
                iNearbySkillPageView.jumpToHaveATalk(chatPageEntity);
                break;
            // 分享
            case OnItemClickListener.CLICK_OPERATION_SHARE:
                iNearbySkillPageView.share(((NearbySkillFragment) iNearbySkillPageView).adapter.getData().get(position));
                break;
        }
    }
}
