package com.ysp.houge.ui.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.OtherNeedAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.popwindow.ReportPopupWindow;
import com.ysp.houge.popwindow.ReportPopupWindow.OnReportLisenter;
import com.ysp.houge.presenter.INeedDetailsPresenter;
import com.ysp.houge.presenter.impl.NeedDetailsPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.INeedDetailsPageView;
import com.ysp.houge.ui.login.LoginActivity;
import com.ysp.houge.ui.me.LoveMeListActivity;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.order.CommentActivity;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.share.ShareUtils;
import com.ysp.houge.widget.CommentView;
import com.ysp.houge.widget.NeedDetailsView;

import java.util.List;

//import com.umeng.socialize.UMShareListener;

/**
 * 描述： 需求详情
 *
 * @ClassName: NeedDetailsActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月19日 下午7:54:53
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class NeedDetailsActivity extends BaseFragmentActivity
		implements INeedDetailsPageView, OnItemClickListener, OnClickListener, OnReportLisenter {
	public static final String KEY = "need_id";

	private View headView;
	private NeedDetailsView detiasView;
	private CommentView commentView;

	private ImageView finish, more, share;
	private LinearLayout buy;
	private Button callPhone, take, haveATalk;

	private ReportPopupWindow reportPopupWindow;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private INeedDetailsPresenter<List<GoodsDetailEntity>> iNeedDetailsPresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, NeedDetailsActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void initActionbar() {
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_need_details);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);

		headView = LayoutInflater.from(this).inflate(R.layout.head_need_details, null);
		detiasView = (NeedDetailsView) headView.findViewById(R.id.dv_head_view_detail);
		commentView = (CommentView) headView.findViewById(R.id.cv_head_view_commend);
		mRefreshListView.getRefreshableView().addHeaderView(headView);

		finish = (ImageView) findViewById(R.id.iv_finish);
		share = (ImageView) findViewById(R.id.iv_share);
		more = (ImageView) findViewById(R.id.iv_more);

		buy = (LinearLayout) findViewById(R.id.line_buy);
		callPhone = (Button) findViewById(R.id.btn_call_phone);
		take = (Button) findViewById(R.id.btn_take);
		haveATalk = (Button) findViewById(R.id.btn_have_a_talk);
		finish.setOnClickListener(this);
		share.setOnClickListener(this);
		more.setOnClickListener(this);

		callPhone.setOnClickListener(this);
		take.setOnClickListener(this);
		haveATalk.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {

		iNeedDetailsPresenter = new NeedDetailsPresenter(this);
		// 获取并设置技能ID
		if (getIntent().getExtras().containsKey(KEY)) {
			iNeedDetailsPresenter.loadSkillDetail(getIntent().getExtras().getInt(KEY));
		}else {
            LogUtil.setLogWithE("NeedDetailsActivity","错误的需求ID");
            close();
        }
//		detiasView.setListener(iNeedDetailsPresenter);
		commentView.setListener(iNeedDetailsPresenter);
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iNeedDetailsPresenter);
		listViewHelper.setAdapter(new OtherNeedAdapter(this));
		listViewHelper.setOnItemClickListener(this);
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<GoodsDetailEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<GoodsDetailEntity> t) {
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
		iNeedDetailsPresenter.clickItem((int) id);
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
	public void loadDitailFinish(GoodsDetailEntity entity) {
		detiasView.setUserInfo(entity.userInfo);
		detiasView.setNeedDetails(entity, this);
		if(entity.is_system == 1){
			haveATalk.setVisibility(View.GONE);
		}
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
	public void hideSell() {
		buy.setVisibility(View.GONE);
	}

	@Override
	public void jumpToMoreLove(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(LoveMeListActivity.KEY, id);
		LoveMeListActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToCallPhone(String phoneNum) {
		GetUri.openCallTelephonePage(this, phoneNum);
	}

	@Override
	public void jumpToMoreComment(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(CommentActivity.KEY, id);
		CommentActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToUserInfo(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, id);
		UserDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void haveATalk(ChatPageEntity chatPageEntity, int op) {
        ChatActivity.haveATalk(this,chatPageEntity);
	}

	@Override
	public void jumpToLogin() {
		LoginActivity.jumpIn(this);
	}

	@Override
	public void share(GoodsDetailEntity detailEntity) {
        ShareUtils.share(this,detailEntity, 2);
	}

	@Override
	public void onReport(int reportType) {
		iNeedDetailsPresenter.requestReport(reportType);
	}

	@Override
	public void onClick(View v) {
        if (v.getId() != R.id.iv_finish && null == MyApplication.getInstance().getUserInfo()) {
            jumpToLogin();
            return;
        }

		switch (v.getId()) {
		// 结束页面
		case R.id.iv_finish:
			close();
			break;

		// 更多
		case R.id.iv_more:
			iNeedDetailsPresenter.more();
			break;

		//分享
		case R.id.iv_share:
			iNeedDetailsPresenter.share();
			break;

		// 打电话
		case R.id.btn_call_phone:
			iNeedDetailsPresenter.callPhone();
			break;

		// 接活
		case R.id.btn_take:
			iNeedDetailsPresenter.take();
			break;

		// 接活
		case R.id.btn_have_a_talk:
			iNeedDetailsPresenter.haveATalk();
			break;

		default:
			break;
		}
	}
}
