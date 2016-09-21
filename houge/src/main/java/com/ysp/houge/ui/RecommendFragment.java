package com.ysp.houge.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.PageAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.eventbus.ChangeLoginStatusEventBusEntity;
import com.ysp.houge.model.entity.eventbus.LoginEventBusEntity;
import com.ysp.houge.model.entity.eventbus.RecommendSettingFinishEantity;
import com.ysp.houge.popwindow.RecommandSortListPopupWindow;
import com.ysp.houge.popwindow.RecommandSortListPopupWindow.OnClickItemListener;
import com.ysp.houge.popwindow.RecommendListPopupWindow;
import com.ysp.houge.popwindow.RecommendListPopupWindow.OnClick;
import com.ysp.houge.presenter.IRecommendPresenter;
import com.ysp.houge.presenter.impl.recmmend.RecommendPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.IRecommandPageView;
import com.ysp.houge.ui.recommend.RecommendNeedFragment;
import com.ysp.houge.ui.recommend.RecommendNeedSettingActivity;
import com.ysp.houge.ui.recommend.RecommendSkillFragment;
import com.ysp.houge.ui.recommend.RecommendSkillSettingActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 关注外层页面(不包含列表的部分)
 *
 * @ClassName: RecommendFragment
 * @author: hx
 * @date: 2015年12月4日 下午2:24:50
 * <p/>
 * 版本: 1.0
 * 修改人： Ocean
 * 修改时间：2016/7/26 17:22
 * 修改信息 ：将viewpager中的getFragmentManager 改为 getFragmentChildManager （行号：165）
 */
public class RecommendFragment extends BaseFragment
        implements IRecommandPageView, OnClickListener, OnPageChangeListener {
    private View view;// 显示PopWindow的Anthor

    private TextView tvRecomment;//关注的文本
    private TextView ivChangeStatus;// 更换登陆身份
    private ImageView ivRecommendSetting;// 设置下方的三角
    private LinearLayout lineSettingRecommend;// 设置关注

    private ImageView ivSortType;// 排序类型图标
    private ImageView ivSpreadRecommend;// 展开收藏列表图标

    private PagerSlidingTabStrip pstsTitleRecommend;// 顶部切换的关注title

    private PageAdapter pageAdapter;//滑动的关注点适配器
    private ViewPager vpRecommendPage;// 底部的ViewPager
    private List<BaseFragment> listFragment; // 显示的页面的集合


    private List<WorkTypeEntity> listRecommend; // 关注的工种的集合
    private List<SortTypeEntity> sortRecommend; // 排序方式的集合


    //记录当前下方列表显示的是第几个页面
    public static int tag;

    /**
     * 1代表接收到定位消息
     * 2代表页面已经创建完成
     * 3代表消息已经发送
     */
    private int local;
    //登录身份
    private int loginStatus;
    //当前的位置
    private BDLocation location;
    //蓝色主题
    private int blue_table = R.drawable.tab_blue_bg;
    //橙色主题
    private int orange_table = R.drawable.tab_orange_bg;

    private IRecommendPresenter iRecommendPresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initActionbar(View view) {
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        this.view = view.findViewById(R.id.line);
        /*技能 or 需求 设置模块*/
        ivChangeStatus = (TextView) view.findViewById(R.id.iv_change_status);
        ivChangeStatus.setOnClickListener(this);

		/*关注模块*/
        lineSettingRecommend = (LinearLayout) view.findViewById(R.id.line_setting_recommend);
        tvRecomment = (TextView) view.findViewById(R.id.tv_recommend_txt);
        ivRecommendSetting = (ImageView) view.findViewById(R.id.iv_recommend_img);

        lineSettingRecommend.setOnClickListener(this);

		/*顶部导航栏模块*/
        pstsTitleRecommend = (PagerSlidingTabStrip) view.findViewById(R.id.psts_recommned_list);

		/*overflow*/
        ivSpreadRecommend = (ImageView) view.findViewById(R.id.iv_spread_recommend);
        ivSpreadRecommend.setOnClickListener(this);

		/*条件排序模块*/
        ivSortType = (ImageView) view.findViewById(R.id.iv_sort_type);
        ivSortType.setOnClickListener(this);

		/*recommend展示模块*/
        vpRecommendPage = (ViewPager) view.findViewById(R.id.vp_recommend);

    }


    @Override
    protected void initializeData(Bundle savedInstanceState) {
        local = 1;//定位默认设置为成功
        /*初始化P层*/
        iRecommendPresenter = new RecommendPresenter(this);
        location = MyApplication.getInstance().getBdLocation();
        loginStatus = MyApplication.getInstance().getLoginStaus();

	    /*实例化展示区数据及顶部导航栏*/

        //初始化顶部导航栏名称
        listRecommend = new ArrayList<WorkTypeEntity>();

        //初始化展示页面fragment
        listFragment = new ArrayList<BaseFragment>();

        //初始化ViewPager适配器
        pageAdapter = new PageAdapter(getChildFragmentManager(), listFragment, listRecommend);
//        TODO 加快viewpager滑动速度 此处可以将viewpager的缓存页面增加 暂时取消
//        int size = 0;
//        if (listFragment != null && listFragment.size() >0){
//             size = listFragment.size();
//        }
//        Log.d("RecommendFragment", "size " +size);
//        vpRecommendPage.setOffscreenPageLimit(size);
        vpRecommendPage.setAdapter(pageAdapter);

        // 加载关注数据
        iRecommendPresenter.loadListDate();

        //加载身份主题
        if (loginStatus != 0) {
            changeLoginStatus();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        // Fragment再次现身的时候判断某些必要条件下需要刷新
        if (!hidden && null != iRecommendPresenter) {
            //==============================================
            // 刷新条件一共有两个：
            // 一个是登录的身份在其他页面改变了
            // 一个是初次进入页面时没有网络，再次进入需要判断并刷新
            //==============================================

            if (loginStatus != MyApplication.getInstance().getLoginStaus()) {
                // 获取新的登陆身份
                changeLoginStatus();
                iRecommendPresenter.loadListDate();
                loginStatus = MyApplication.getInstance().getLoginStaus();
            } else if (null == listRecommend || listRecommend.isEmpty()) {
                iRecommendPresenter.loadListDate();
            }
        }

        //================================================================
        //=						这里下方保存以前的处理方法                                                         =
        //=			具体原因就是loadListDate()执行的时候会清空关注集合                                 =
        //= 			而listRecommend正是对Presenter层关注集合的引用                        =
        //=		   所以判断listRecommend为空的时候是true，然后造成了二次刷新                          =
        //================================================================
        // 重新显示的时候判断登陆身份是否一样，不一样就加载新数据
        //if (!hidden && iRecommendPresenter != null && loginStatus != MyApplication.getInstance().getLoginStaus()) {
        //// 获取新的登陆身份
        //loginStatus = MyApplication.getInstance().getLoginStaus();
        //changeLoginStatus();
        //// 加载关注数据(在别的页面切换了身份)
        //iRecommendPresenter.loadListDate();
        //
        //// 如果关注列表为空或者，那么需要重新加载(前面进来的时候没有加载到数据的情况)
        //if (!hidden && null == listRecommend || listRecommend.isEmpty()) {
        //iRecommendPresenter.loadListDate();
        //}
        //}
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void loadListFinish(List<WorkTypeEntity> list, List<SortTypeEntity> sort/*排序类型实体*/) {
        // TODO 数据加载完成后的操作
        this.listRecommend = list;
        this.sortRecommend = sort;

        //清空Fragment集合
        listFragment.clear();
        // 循环添加所有的关注点Fragment
        int listSize = list.size();

        //不同的身份往下面添加不同的Fragment
        if (loginStatus == MyApplication.LOG_STATUS_BUYER) {
            for (int i = 0; i < listSize; i++) {
                RecommendSkillFragment skillFragment = new RecommendSkillFragment();
                //为Fragment添加初始化数据（之前是在构造方法中）
                skillFragment.setDate(list.get(i), sort.get(0));
                //添加进集合
                this.listFragment.add(skillFragment);
            }
        } else {
            for (int i = 0; i < listSize; i++) {
                RecommendNeedFragment needFragment = new RecommendNeedFragment();
                //为Fragment添加初始化数据（之前是在构造方法中）
                needFragment.setDate(list.get(i), sort.get(0));
                //添加进集合
                this.listFragment.add(needFragment);
            }
        }


        //设置适配器的数据
        pageAdapter.setList(this.listFragment);
        //设置适配器的title
        pageAdapter.setTitle(this.listRecommend);
        //设置适配器
        vpRecommendPage.setAdapter(pageAdapter);
        //TODO  增加缓存页面 使滑动更顺畅
//        vpRecommendPage.setOffscreenPageLimit(2);
        //对关注点进行空判断
        if (list != null && list.size() > 0) {
            tag = 0;//默认第0个被选中
            //将两个控件进行绑定
            pstsTitleRecommend.setViewPager(vpRecommendPage);
            //设置点击事件
            vpRecommendPage.addOnPageChangeListener(this);
        }

        //如果页面创建完成，并且接收到定位信息，则发送消息
        if (null != location) {
            EventBus.getDefault().post(local);
            local = 3;
        } else {
            local = 2;
        }

        // 这里记录一下，貌似不写这句就能直接刷新了
        // pageAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChangeLoginStatusDialog() {
        // TODO 更换身份弹窗
        StringBuilder sb = new StringBuilder();
        switch (loginStatus) {
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
                    iRecommendPresenter.changeLoginStutus();
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
        EventBus.getDefault().post(new ChangeLoginStatusEventBusEntity(0));
    }

    //重新初始化买家
    private void initSeller() {

//        ivChangeStatus.setImageResource(R.drawable.change_login_status_sell);
        ivChangeStatus.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
        ivChangeStatus.setText("找牛人");
        ivRecommendSetting.setImageResource(R.drawable.top_arrow_sub_blue);
        tvRecomment.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
        pstsTitleRecommend.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
        pstsTitleRecommend.setTabBackground(blue_table);
        pstsTitleRecommend.setIndicatorColorResource(R.color.color_app_theme_blue_bg);
    }

    // 重新初始化买家
    private void initBuyer() {

//        ivChangeStatus.setImageResource(R.drawable.change_login_status_buy);
        ivChangeStatus.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
        ivChangeStatus.setText("找外包");

        ivRecommendSetting.setImageResource(R.drawable.top_arrow_sub_orange);
        tvRecomment.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
        pstsTitleRecommend.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
        pstsTitleRecommend.setTabBackground(orange_table);
        pstsTitleRecommend.setIndicatorColorResource(R.color.color_app_theme_orange_bg);
    }

    @Override
    public void jumpToRecommendSettingPage() {
        // 如果没有登录的话不允许设置，提示登录
        if (MyApplication.getInstance().getUserInfo() == null) {
            ((HomeActivity) getActivity()).createLoginDialog();
            return;
        }

        // TODO 跳转到收藏设置页面
        switch (loginStatus) {
            case MyApplication.LOG_STATUS_BUYER:
                Bundle bundle = new Bundle();
                bundle.putString(RecommendSkillSettingActivity.WORK_TYPE_KEY, createRecommendString());
                RecommendSkillSettingActivity.jumpIn(getActivity(), bundle);
                getActivity().overridePendingTransition(R.anim.slide_in_from_top, R.anim.activity_stay);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                Bundle b = new Bundle();
                b.putString(RecommendNeedSettingActivity.WORK_TYPE_KEY, createRecommendString());
                RecommendNeedSettingActivity.jumpIn(getActivity(), b);
                getActivity().overridePendingTransition(R.anim.slide_in_from_top, R.anim.activity_stay);
                break;
            default:
                break;
        }
    }

    @Override
    public void setIndex(int index) {
        vpRecommendPage.setCurrentItem(index);
    }

    @Override
    public void showMoreRecommendPop() {
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

        RecommendListPopupWindow pop;
        pop = new RecommendListPopupWindow(getActivity(), listRecommend) {
            @Override
            public void onDismiss() {
                super.onDismiss();
                ivSpreadRecommend.setImageResource(R.drawable.recommend_more_def);
            }
        };
        pop.showAsDropDown(view);


            pop.setOnClick(new OnClick() {
                @Override
                public void click(int id) {
                    // TODO 列表的单击事件
                    iRecommendPresenter.ListPopCliclk(id);
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

        List<SortTypeEntity> sort = new ArrayList<SortTypeEntity>();
        if (null != sortRecommend) {
            sort.addAll(sortRecommend);
        }
        RecommandSortListPopupWindow pop = new RecommandSortListPopupWindow(getActivity(), sort);
        pop.showAsDropDown(view);

        pop.setListener(new OnClickItemListener() {

            @Override
            public void clickItem(int position, SortTypeEntity entity) {
                // TODO 排序类型列表的单击事件
                if (!entity.equals(chooseSortType)) {
                    iRecommendPresenter.SortPopCliclk(position, entity);
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

    /**
     * 创建关注字符串，进入关注设置时候用
     */
    private String createRecommendString() {
        if (listRecommend != null && listRecommend.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < listRecommend.size(); i++) {
                if (listRecommend.get(i).getId() == -1) {
                    continue;
                }

                sb.append(listRecommend.get(i).getName());
                sb.append(" 丨 ");
            }

            // 删除最后一个竖线
            sb.delete(sb.length() - 3, sb.length());
            return sb.toString();
        } else {
            return "";
        }
    }

    @Override
    public void onClick(View v) {
        // TODO 点击事件处理
        switch (v.getId()) {
            // 更换登录身份
            case R.id.iv_change_status:
                iRecommendPresenter.showChangeStatusPop();
                break;

            // 设置关注点
            case R.id.line_setting_recommend:
                iRecommendPresenter.settingRecommend();
                break;

            // 展开所有的关注点
            case R.id.iv_spread_recommend:
                iRecommendPresenter.spreadRecommendList();
                break;

            // 展开排序类型
            case R.id.iv_sort_type:
                iRecommendPresenter.spreadRecommendSort();
                break;

            default:
                LogUtil.setLogWithE("RecommendFragment", "Error View ID.");
                break;
        }
    }

    /**
     * 关注设置页面返回,接收刷新要求
     */
    public void onEventMainThread(RecommendSettingFinishEantity eantity) {
        iRecommendPresenter.loadListDate();
    }

    /**
     * 登陆返回，刷新
     */
    public void onEventMainThread(LoginEventBusEntity entity) {
        iRecommendPresenter.loadListDate();
    }

    @Override  //ViewPager滑动结束，重新赋值tag
    public void onPageSelected(int arg0) {
        tag = arg0;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
}
