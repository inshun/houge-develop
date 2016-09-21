package com.ysp.houge.ui.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.OtherSkillAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.popwindow.ReportPopupWindow;
import com.ysp.houge.popwindow.ReportPopupWindow.OnReportLisenter;
import com.ysp.houge.presenter.ISkillDetailsPresneter;
import com.ysp.houge.presenter.impl.SkillDetailsPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ISkillDetailsPageView;
import com.ysp.houge.ui.login.LoginActivity;
import com.ysp.houge.ui.me.CommentListActivity;
import com.ysp.houge.ui.me.LoveMeListActivity;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.order.OrderActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.share.ShareUtils;
import com.ysp.houge.widget.CommentView;
import com.ysp.houge.widget.SkillDetailsView;

import java.util.List;

/**
 * @author hx
 * @version 1.0
 * @描述: 技能详情以及其他服务列表页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2015年10月16日上午10:29:41
 */
@SuppressLint("InflateParams")
public class SkillDetailsActivity extends BaseFragmentActivity
        implements ISkillDetailsPageView, OnItemClickListener, OnClickListener, OnReportLisenter {
    public static final String KEY = "skill_id";
    private View headView;
    private SkillDetailsView detiasView;
    private CommentView commentView;
    private ImageView share, finish, more;
    private boolean isZan = false;
    private RelativeLayout sell;
    private ImageView zan;// 赞
    private TextView money;// 显示金额的文本
    private Button order;// 预定
    private Button haveATalk;// 聊一聊
    private ReportPopupWindow reportPopupWindow;
    /**
     * 下拉刷新listView
     */
    private PullToRefreshListView mRefreshListView;
    private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
    private ISkillDetailsPresneter<List<GoodsDetailEntity>> iSkillDetailsPresneter;

    public static void jumpIn(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SkillDetailsActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_skill_details);
    }

    @Override
    protected void initActionbar() {
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
        headView = LayoutInflater.from(this).inflate(R.layout.head_skill_details, null);
        detiasView = (SkillDetailsView) headView.findViewById(R.id.dv_head_view_detail);
        commentView = (CommentView) headView.findViewById(R.id.cv_head_view_commend);

        finish = (ImageView) findViewById(R.id.iv_finish);
        share = (ImageView) findViewById(R.id.iv_share);
        more = (ImageView) findViewById(R.id.iv_more);

        sell = (RelativeLayout) findViewById(R.id.rela_buy);
        zan = (ImageView) findViewById(R.id.iv_zan);
        money = (TextView) findViewById(R.id.tv_skill_total_money);
        order = (Button) findViewById(R.id.btn_order_skill);
        haveATalk = (Button) findViewById(R.id.btn_have_a_talk);

        finish.setOnClickListener(this);
        more.setOnClickListener(this);
        share.setOnClickListener(this);

        zan.setOnClickListener(this);
        order.setOnClickListener(this);
        haveATalk.setOnClickListener(this);

        mRefreshListView.getRefreshableView().addHeaderView(headView);
        mRefreshListView.getRefreshableView().setDividerHeight(0);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        iSkillDetailsPresneter = new SkillDetailsPresenter(this);
        // 获取并设置技能ID
        if (getIntent().getExtras().containsKey(KEY)) {
            iSkillDetailsPresneter.loadSkillDetail(getIntent().getExtras().getInt(KEY));
        } else {
            LogUtil.setLogWithE("SkillDetailsActivity", "错误的技能ID");
            close();
        }
        commentView.setListener(iSkillDetailsPresneter);
        detiasView.setListener(iSkillDetailsPresneter);
        listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
        listViewHelper.setRefreshPresenter(iSkillDetailsPresneter);
        listViewHelper.setAdapter(new OtherSkillAdapter(this));
        listViewHelper.setOnItemClickListener(this);
        showZanStatus(isZan);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iSkillDetailsPresneter.clickItem(parent, view, position, id);
    }

    @Override
    public void refresh() {
        mRefreshListView.setRefreshing();
    }

    @Override
    public void refreshComplete(List<GoodsDetailEntity> t) {
        listViewHelper.refreshComplete(t);
        if (null == t || t.isEmpty()) {
            commentView.hasOtherSkill(false);
        } else {
            commentView.hasOtherSkill(true);
        }
    }

    @Override
    public void loadMoreComplete(List<GoodsDetailEntity> t) {
        listViewHelper.loadMoreDataComplete(t);
    }

    @Override
    public void loadDetailFinish(GoodsDetailEntity entity) {
        // TODO 技能详情加载完成后的页面逻辑
        if (entity != null) {
            //计算显示金额(默认数量是1，所以显示单价)
            calcAndShowTotalMoney(entity.price);
        }
        //未技能详情页面设置用户信息以及技能信息
        detiasView.setUserInfo(entity.userInfo);
        detiasView.setSkillDetails(entity, this);
    }

    @Override
    public void loadZanFinish(List<UserInfoEntity> list) {
        commentView.setLove(list);
    }

    @Override
    public void loadCommentFinish(List<CommentEntity> list) {
        commentView.setComment(list);
    }

    @Override
    public void calcAndShowTotalMoney(String money) {
        // TODO 显示从新计算的金额
        StringBuilder sb = new StringBuilder("应付:");
        sb.append("<font color='#ff6724'>");
        sb.append(money);
        sb.append("</font>");
        sb.append("元");
        this.money.setText(Html.fromHtml(sb.toString()));
    }

    @Override
    public void showZanStatus(boolean isZan) {
        if (isZan) {
            zan.setImageResource(R.drawable.icon_star_orange);
        } else {
            zan.setImageResource(R.drawable.icon_star_gray);
        }
    }

    @Override
    public void showMoreDialog() {
        // TODO 显示更多悬浮窗
        if (null == reportPopupWindow) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.popup_report, null);
            reportPopupWindow = new ReportPopupWindow(contentView, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        if (!reportPopupWindow.isShowing()) {
            reportPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            LayoutParams params = getWindow().getAttributes();
            params.alpha = 0.4f;
            getWindow().setAttributes(params);
        }
        reportPopupWindow.setLisenter(this);
        reportPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
    }

    @Override
    public void hideSell() {
        sell.setVisibility(View.GONE);
    }

    @Override
    public void haveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(this, chatPageEntity);
    }

    @Override
    public void share(GoodsDetailEntity detailEntity) {
        ShareUtils.share(this, detailEntity, 1);
    }

    @Override
    public void onReport(int reportType) {
        iSkillDetailsPresneter.requestReport(reportType);
    }

    @Override
    public void jumpToOrder(GoodsDetailEntity entity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.skillDetailEntity = entity;
        orderEntity.num = detiasView.getmBuyCount();
        double money = 0.0;
        try {
            money = Double.parseDouble(entity.price);
        } catch (Exception e) {
        }
        orderEntity.totalMoney = money * orderEntity.num;
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderActivity.ORDER_SKILL_KEY, orderEntity);
        OrderActivity.jumpIn(this, bundle);
    }

    @Override
    public void jumpToMoreLove(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(LoveMeListActivity.KEY, id);
        LoveMeListActivity.jumpIn(this, bundle);
    }

    @Override
    public void jumpToMoreComment(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(CommentListActivity.KEY, id);
        CommentListActivity.jumpIn(this, bundle);
    }

    @Override
    public void jumpToLogin() {
        LoginActivity.jumpIn(this);
    }

    @Override
    public void jumpUserInfo(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(UserDetailsActivity.KEY, id);
        UserDetailsActivity.jumpIn(this, bundle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.iv_finish) {
            if (null == MyApplication.getInstance().getUserInfo()) {
                jumpToLogin();
                return;
            }
        }
        switch (v.getId()) {
            // 退出
            case R.id.iv_finish:
                close();
                break;
            // 菜单栏更多
            case R.id.iv_more:
                iSkillDetailsPresneter.more();
                break;
            // 分享
            case R.id.iv_share:
                iSkillDetailsPresneter.onShare();
                break;
            // 赞
            case R.id.iv_zan:
                isZan = !isZan;
                showZanStatus(isZan);
                break;
            // 预约
            case R.id.btn_order_skill:
                iSkillDetailsPresneter.order();
                break;
            // 聊一聊
            case R.id.btn_have_a_talk:
                iSkillDetailsPresneter.haveATalk();
                break;
            default:
                break;
        }
    }
}