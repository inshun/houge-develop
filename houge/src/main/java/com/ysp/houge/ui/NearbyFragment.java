package com.ysp.houge.ui;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.MapWorkTypeAdapter;
import com.ysp.houge.adapter.PageAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.eventbus.ChangeLoginStatusEventBusEntity;
import com.ysp.houge.popwindow.RecommandSortListPopupWindow;
import com.ysp.houge.popwindow.RecommandSortListPopupWindow.OnClickItemListener;
import com.ysp.houge.popwindow.RecommendListPopupWindow;
import com.ysp.houge.presenter.INearByPresenter;
import com.ysp.houge.presenter.impl.NearByPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.INearByPageView;
import com.ysp.houge.ui.nearby.NearbyMapFragment;
import com.ysp.houge.ui.nearby.NearbyNeedFragment;
import com.ysp.houge.ui.nearby.NearbySkillFragment;
import com.ysp.houge.ui.search.SearchActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.widget.HorizontalListView;
import com.ysp.houge.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 附近外层(不包含列表)页面View层
 *
 * @ClassName: NearbyFragment
 * @author: hx
 * @date: 2015年12月4日 下午2:28:57
 * <p/>
 * 版本: 1.0
 */
public class NearbyFragment extends BaseFragment
        implements INearByPageView, OnClickListener, com.ysp.houge.widget.PagerSlidingTabStrip.OnClickItemListener, AdapterView.OnItemClickListener {
    private View view;// 显示瀑布流关注点，或者排序类型的横线的对象
    private TextView ivChangeStatus;// 更换登陆身份
    private ImageView ivMapOrListChange;// 切换地图或者列表页面
    private ImageView ivSearch;// 搜索


    private TextView tvNearby;

    private ImageView ivSpreadRecommend;// 展开收藏列表
    private ImageView ivSortType;// 排序类型

    private PagerSlidingTabStrip pstsTitleNearby;// 顶部切换的关注title
    private int blue_table = R.drawable.tab_blue_bg;
    private int orange_table = R.drawable.tab_orange_bg;

    private LinearLayout line;
    private HorizontalListView hlv;
    private MapWorkTypeAdapter adapter;

    private List<BaseFragment> listFragment; // 显示的页面的集合
    private PageAdapter pageAdapter;
    private ViewPager vpRecommendPage;// 底部显示具体列表的ViewPager

    private LinearLayout lineMap;// 显示地图页面的布局
    private NearbyMapFragment mapFragment;// 地图的列表

    private List<WorkTypeEntity> listRecommend; // 工种的集合
    private List<SortTypeEntity> sortRecommend; // 排序方式的集合
    private int loginStatus;// 当前登录状态
    private int index = 0; // 当前页面选中的下标
    private String pageTag = "list";// 地图以及列表切换的标记

    private INearByPresenter iNearByPresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void initActionbar(View view) {
//        if (TextUtils.equals(pageTag, "map")){
//
//        }
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        ivChangeStatus = (TextView) view.findViewById(R.id.iv_change_status);
        ivChangeStatus.setOnClickListener(this);


        tvNearby = (TextView) view.findViewById(R.id.tv_nearby);

        ivMapOrListChange = (ImageView) view.findViewById(R.id.iv_list_map_change);
        ivSearch = (ImageView) view.findViewById(R.id.iv_neary_search);
        ivMapOrListChange.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        vpRecommendPage = (ViewPager) view.findViewById(R.id.vp_nearby);

        lineMap = (LinearLayout) view.findViewById(R.id.line_map);

        ivSpreadRecommend = (ImageView) view.findViewById(R.id.iv_spread_recommend);
        ivSpreadRecommend.setOnClickListener(this);
        ivSortType = (ImageView) view.findViewById(R.id.iv_sort_type);
        ivSortType.setOnClickListener(this);

        pstsTitleNearby = (PagerSlidingTabStrip) view.findViewById(R.id.psts_recommned_list);
        pstsTitleNearby.setOnClickItemListener(this);

        line = (LinearLayout) view.findViewById(R.id.line_recommend_list);
        hlv = (HorizontalListView) view.findViewById(R.id.hlv_list);
        hlv.setVisibility(View.GONE);
        hlv.setOnItemClickListener(this);

        this.view = view.findViewById(R.id.line);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        iNearByPresenter = new NearByPresenter(this);
        loginStatus = MyApplication.getInstance().getLoginStaus();
        // 初始化视图
        if (loginStatus != 0) {
            changeLoginStatus();
        }

        listFragment = new ArrayList<BaseFragment>();
        listRecommend = new ArrayList<WorkTypeEntity>();
        pageAdapter = new PageAdapter(getFragmentManager(), listFragment, listRecommend);
        vpRecommendPage.setAdapter(pageAdapter);

        // 加载关注数据
        iNearByPresenter.loadListDate();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && null != iNearByPresenter) {
            //==============================================
            // 刷新条件一共有两个：
            // 一个是登录的身份在其他页面改变了
            // 一个是初次进入页面时没有网络，再次进入需要判断并刷新
            //==============================================

            if (loginStatus != MyApplication.getInstance().getLoginStaus()) {
                // 获取新的登陆身份
                changeLoginStatus();
                iNearByPresenter.loadListDate();
                loginStatus = MyApplication.getInstance().getLoginStaus();
                /**显示地图的时候吧更换身份改成返回按钮*/
                if (TextUtils.equals(pageTag, "map") && ivChangeStatus != null) {
                    changeMapOrList(0);
                }
            } else if (null == listRecommend || listRecommend.isEmpty()) {
                iNearByPresenter.loadListDate();
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void loadListFinish(List<WorkTypeEntity> list, List<SortTypeEntity> sort) {
        // TODO 用户关注点加载完成后的操作
        this.listRecommend = list;
        this.sortRecommend = sort;

        // 循环添加所有的关注点Fragment
        listFragment.clear();
        for (int i = 0; i < list.size(); i++) {
            if (MyApplication.getInstance().getPreferenceUtils().getLoginStatus() == MyApplication.LOG_STATUS_BUYER) {
                NearbySkillFragment skillFragment = new NearbySkillFragment();
                skillFragment.setDate(list.get(i), sort.get(0));
                this.listFragment.add(skillFragment);
            } else {
                NearbyNeedFragment needFragment = new NearbyNeedFragment();
                needFragment.setDate(list.get(i), sort.get(0));
                this.listFragment.add(needFragment);
            }
        }
        pageAdapter.setList(this.listFragment);
        pageAdapter.setTitle(this.listRecommend);
        pstsTitleNearby.setViewPager(vpRecommendPage);
        vpRecommendPage.setAdapter(pageAdapter);
    }

    @Override
    public void showChangeLoginStatusDialog() {
        // TODO 显示更换登陆身份的pop
        StringBuilder sb = new StringBuilder();
        switch (MyApplication.getInstance().getPreferenceUtils().getLoginStatus()) {
            case MyApplication.LOG_STATUS_BUYER:
                sb.append("去看需求?");
                break;
            case MyApplication.LOG_STATUS_SELLER:
                sb.append("去看技能?");
                break;
        }
        YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity("", sb.toString(), "取消", "确定");
        YesOrNoDialog dialog = new YesOrNoDialog(getActivity(), dialogEntity, new OnYesOrNoDialogClickListener() {

            @Override
            public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
                if (yesOrNoType == YesOrNoType.BtnOk) {
                    iNearByPresenter.changeLoginStatus();
                }
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void changeLoginStatus() {
        // TODO 更换登陆身份的初始化操作
        // 获取新的登陆身份
        loginStatus = MyApplication.getInstance().getLoginStaus();
        switch (loginStatus) {
            case MyApplication.LOG_STATUS_BUYER:
                initBuyer();
                break;
            case MyApplication.LOG_STATUS_SELLER:
                initSeller();
                break;
        }
        if (null != adapter && hlv.getVisibility() == View.VISIBLE) {
            adapter.notifyDataSetChanged();
        }
        EventBus.getDefault().post(new ChangeLoginStatusEventBusEntity(1));
    }

    private void initSeller() {
        // TODO 卖家初始化
//        ivChangeStatus.setImageResource(R.drawable.change_login_status_sell);
        ivChangeStatus.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
        ivChangeStatus.setText("找牛人");

        if (TextUtils.equals(pageTag, "list")) {
            // 地图或者列表的
            ivMapOrListChange.setImageResource(R.drawable.map_blue);
        } else {
            // 地图或者列表的
            ivMapOrListChange.setImageResource(R.drawable.list_blue);
        }
        tvNearby.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
        // 搜索
        ivSearch.setBackgroundResource(R.drawable.search_blue);
        pstsTitleNearby.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
        pstsTitleNearby.setTabBackground(blue_table);
        pstsTitleNearby.setIndicatorColorResource(R.color.color_app_theme_blue_bg);
    }

    private void initBuyer() {
        // TODO 买家初始化
//        ivChangeStatus.setImageResource(R.drawable.change_login_status_buy);
        ivChangeStatus.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
        ivChangeStatus.setText("找外包");

        if (TextUtils.equals(pageTag, "list")) {
            // 地图或者列表的
            ivMapOrListChange.setImageResource(R.drawable.map_orange);
        } else {
            // 地图或者列表的
            ivMapOrListChange.setImageResource(R.drawable.list_orange);
        }
        tvNearby.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
        // 搜索
        ivSearch.setBackgroundResource(R.drawable.search_orange);
        pstsTitleNearby.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
        pstsTitleNearby.setTabBackground(orange_table);
        pstsTitleNearby.setIndicatorColorResource(R.color.color_app_theme_orange_bg);
    }

    @Override
    public void changeMapOrList(int viewType) {
        // TODO 切换地图或者列表
        switch (viewType) {
            // 显示列表
            case INearByPresenter.VIEW_TYPE_LIST:
                vpRecommendPage.setCurrentItem(adapter.getIndex());
                switch (loginStatus) {
                    case MyApplication.LOG_STATUS_BUYER:
                        ivMapOrListChange.setImageResource(R.drawable.map_orange);
                        if (Build.VERSION.SDK_INT >= 16) {
                            ivChangeStatus.setBackground(null);
                        }

                        ivChangeStatus.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                        ivChangeStatus.setText("找外包");

//                        ivChangeStatus.setImageResource(R.drawable.change_login_status_buy);
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        ivMapOrListChange.setImageResource(R.drawable.map_blue);

                        if (Build.VERSION.SDK_INT >= 16) {
                            ivChangeStatus.setBackground(null);
                        }
                        ivChangeStatus.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                        ivChangeStatus.setText("找牛人");
//                        ivChangeStatus.setImageResource(R.drawable.change_login_status_sell);

                        break;
                }
                pageTag = "list";
                if (vpRecommendPage != null) {
                    vpRecommendPage.setVisibility(View.VISIBLE);
                }
                lineMap.setVisibility(View.INVISIBLE);
                line.setVisibility(View.VISIBLE);
                hlv.setVisibility(View.GONE);
                break;

            // 显示地图
            case INearByPresenter.VIEW_TYPE_MAP:
                // 判断需不需要实例化adapter对象
                if (null == adapter)
                    adapter = new MapWorkTypeAdapter(getActivity(), this.listRecommend);

                // 判断需不需要实例化Map页面对象(map里面的数据需要根据身份不同去不同加载)
                if (mapFragment == null) {
                    mapFragment = new NearbyMapFragment();
                    // 替换显示
                    getFragmentManager().beginTransaction().add(R.id.line_map, mapFragment).commit();
                }
                if (mapFragment != null && null != adapter && null != listRecommend && null != vpRecommendPage && listRecommend.size() > 0) {
                    mapFragment.setWorkTypeEntity(listRecommend.get(vpRecommendPage.getCurrentItem()));
                    adapter.setIndex(vpRecommendPage.getCurrentItem());
                    adapter.setList(listRecommend);
//                    adapter.notifyDataSetChanged();
                    hlv.setAdapter(adapter);
                }

                switch (loginStatus) {
                    case MyApplication.LOG_STATUS_BUYER:
                        ivMapOrListChange.setImageResource(R.drawable.list_orange);
                        //显示地图的时候吧更换身份改成返回按钮
//                        ivChangeStatus.setBackground(R.drawable.menu_left_orange);
                        ivChangeStatus.setText("");
                        if (Build.VERSION.SDK_INT >= 16) {
                            ivChangeStatus.setBackground(getResources().getDrawable(R.drawable.menu_left_orange_selected));

                        }
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        ivMapOrListChange.setImageResource(R.drawable.list_blue);
                        //显示地图的时候吧更换身份改成返回按钮
//                        ivChangeStatus.setImageResource(R.drawable.menu_left_blue);
                        ivChangeStatus.setText("");
                        if (Build.VERSION.SDK_INT >= 16) {
                            ivChangeStatus.setBackground(getResources().getDrawable(R.drawable.menu_left_blue_selected));
                        }

                        break;
                }
                pageTag = "map";
                if (vpRecommendPage != null) {
                    vpRecommendPage.setVisibility(View.INVISIBLE);
                }
                lineMap.setVisibility(View.VISIBLE);
                line.setVisibility(View.GONE);
                hlv.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void jumpToSearch() {
        SearchActivity.jumpIn(getActivity());
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
        vpRecommendPage.setCurrentItem(index);
    }

    @Override
    public void onClick(int position) {
        sendWorkTypeToMap(position);
    }

    /**
     * 发送消息告诉地图页面,关注点的改变
     */
    private void sendWorkTypeToMap(int position) {
        EventBus.getDefault().post(listRecommend.get(position));
    }

    @Override
    public void showMoreRecommendPop() {
        // TODO 展开其他的关注选项
        // TODO 展开其他的关注选项
        switch (loginStatus) {
            case MyApplication.LOG_STATUS_BUYER:
                // 改变展开图片
                ivSpreadRecommend.setImageResource(R.drawable.recommend_more_orange);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                // 改变展开图片
                ivSpreadRecommend.setImageResource(R.drawable.recommend_more_blue);
                break;
        }

        RecommendListPopupWindow pop = new RecommendListPopupWindow(getActivity(), listRecommend);
        pop.showAsDropDown(view);

        pop.setOnClick(new RecommendListPopupWindow.OnClick() {

            @Override
            public void click(int id) {
                // TODO 列表的单击事件
                iNearByPresenter.ListPopCliclk(id);
            }
        });

        pop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                ivSpreadRecommend.setImageResource(R.drawable.recommend_more_def);
            }
        });
    }

    @Override
    public void showSortTypePop(final SortTypeEntity chooseSortType) {
        // TODO 显示排序类型选项
        switch (loginStatus) {
            case MyApplication.LOG_STATUS_BUYER:
                // 改变展开图片
                ivSortType.setImageResource(R.drawable.sort_orange);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                // 改变展开图片
                ivSortType.setImageResource(R.drawable.sort_blue);
                break;
        }

        final List<SortTypeEntity> sort = new ArrayList<SortTypeEntity>();
        sort.addAll(sortRecommend);
        final RecommandSortListPopupWindow pop = new RecommandSortListPopupWindow(getActivity(), sort);
        pop.showAsDropDown(view);

        pop.setListener(new OnClickItemListener() {

            @Override
            public void clickItem(int position, SortTypeEntity entity) {
                // TODO 排序类型列表的单击事件
                if (!entity.equals(chooseSortType)) {
                    iNearByPresenter.SortPopCliclk(position, entity);
                }
            }
        });

        pop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                ivSortType.setImageResource(R.drawable.sort_def);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO 点击事件处理
        switch (v.getId()) {
            // 更换登录身份
            case R.id.iv_change_status:
                if (TextUtils.equals(pageTag, "map")) {
                    changeMapOrList(INearByPresenter.VIEW_TYPE_LIST);
                    return;
                }
                iNearByPresenter.showChangeStatusDialog();
                break;

            // 切换地图或者列表
            case R.id.iv_list_map_change:
                iNearByPresenter.changeMapOrList();
                break;

            // 搜索
            case R.id.iv_neary_search:
                iNearByPresenter.search();
                break;

            // 展开所有的关注点
            case R.id.iv_spread_recommend:
                iNearByPresenter.spreadRecommendList();
                break;

            // 展开排序类型
            case R.id.iv_sort_type:
                iNearByPresenter.spreadRecommendSort();
                break;

            default:
                LogUtil.setLogWithE("", "Error View ID.");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sendWorkTypeToMap(position);
        adapter.setIndex(position);
        adapter.notifyDataSetChanged();
    }
}
