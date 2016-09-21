package com.ysp.houge.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.UserNeedAdapter;
import com.ysp.houge.adapter.UserSkillAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.ShareEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.popwindow.ReportPopupWindow;
import com.ysp.houge.popwindow.ReportPopupWindow.OnReportLisenter;
import com.ysp.houge.presenter.IUserDetailsPresenter;
import com.ysp.houge.presenter.impl.UserDetailsPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IUserDetailsPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.photoview.PhotoViewActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.share.ShareUtils;
import com.ysp.houge.widget.UserDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 用户详情以及技能列表页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月12日下午8:38:52
 * @version 1.0
 */
public class UserDetailsActivity extends BaseFragmentActivity
		implements IUserDetailsPageView, OnItemClickListener, OnReportLisenter, OnClickListener {
	public static final String KEY = "user_id";
	public UserNeedAdapter needAdapter;
	public UserSkillAdapter skillAdapter;
	private ImageView finish, share, more;
	private View headView;
    private UserDetailView userDetailView;
    private int type = UserDetailView.TYPE_SKILL;
	private ReportPopupWindow reportPopupWindow;
	private int TAG = MyApplication.LOG_STATUS_BUYER;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	public ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
	private IUserDetailsPresenter<List<GoodsDetailEntity>> iUserDetailsPresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, UserDetailsActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_user_info_details);
	}

	@Override
	protected void initActionbar() {
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);

		finish = (ImageView) findViewById(R.id.iv_finish);
		share = (ImageView) findViewById(R.id.iv_share);
		more = (ImageView) findViewById(R.id.iv_more);

		finish.setOnClickListener(this);
		share.setOnClickListener(this);
		more.setOnClickListener(this);

        switch (MyApplication.getInstance().getLoginStaus()){
           case  MyApplication.LOG_STATUS_BUYER:
               finish.setImageResource(R.drawable.icon_back_orange_66);
               share.setImageResource(R.drawable.icon_share_orange_66);
               more.setImageResource(R.drawable.icon_more_orange_66);
               break;
           case  MyApplication.LOG_STATUS_SELLER:
               finish.setImageResource(R.drawable.icon_back_blue_66);
               share.setImageResource(R.drawable.icon_share_blue_66);
               more.setImageResource(R.drawable.icon_more_blue_66);
               break;
        }

		// 加载listView的headView
		headView = View.inflate(this,R.layout.head_user_info,null);
        userDetailView = (UserDetailView) headView.findViewById(R.id.user_detail_view);
        userDetailView.setClickLisenter(this);
        userDetailView.setType(type);

        mRefreshListView.getRefreshableView().setDividerHeight(0);
        mRefreshListView.getRefreshableView().addHeaderView(headView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iUserDetailsPresenter = new UserDetailsPresenter(this);
		// 获取用户ID
		if (getIntent().getExtras().containsKey(KEY)) {
			iUserDetailsPresenter.setUserId(getIntent().getExtras().getInt(KEY));
		}else {
            LogUtil.setLogWithE("UserDetailsActivity","错误的用户信息");
            close();
        }
		iUserDetailsPresenter.LoadUserInfo();// 加载用户信息
		listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);

		listViewHelper.setRefreshPresenter(iUserDetailsPresenter);
		listViewHelper.setOnItemClickListener(this);
		// 设置TAG并刷新
		iUserDetailsPresenter.setTAG(TAG);
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
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<GoodsDetailEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<GoodsDetailEntity> t) {
		listViewHelper.loadMoreDataComplete(t);
	}

	@Override
	public void swithListType(int type) {
		switch (type) {
		case MyApplication.LOG_STATUS_BUYER:
			if (null == skillAdapter) {
				skillAdapter = new UserSkillAdapter(this, iUserDetailsPresenter);
			}
			listViewHelper.setAdapter(skillAdapter);
            if (null != userDetailView)
                userDetailView.setType(UserDetailView.TYPE_SKILL);
			break;

		case MyApplication.LOG_STATUS_SELLER:
			if (null == needAdapter) {
				needAdapter = new UserNeedAdapter(this, iUserDetailsPresenter);
			}
			listViewHelper.setAdapter(needAdapter);
            if (null != userDetailView)
                userDetailView.setType(UserDetailView.TYPE_NEED);
			break;
		}
        listViewHelper.getAdapter().notifyDataSetChanged();
	}

	@SuppressWarnings("null")
	@Override
	public void showUserInfo(UserInfoEntity infoEntity) {
        if (null != userDetailView)
            userDetailView.setUserInfo(infoEntity);
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
	public void jumpToSkillDetails(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, id);
		SkillDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToNeedDetails(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, id);
		NeedDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void share(GoodsDetailEntity entity){
        ShareUtils.share(this, entity, 0);
	}

	@Override
	public void haveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(this, chatPageEntity);
	}

	@Override
	public void share(UserInfoEntity entity) {
        //分享牛人
        List<String> image = new ArrayList<>();
        if (!TextUtils.isEmpty(entity.avatar))
            image.add(entity.avatar);
        ShareUtils.creteShare(new ShareEntity(this, entity.nick + ":特别厉害的牛人哦,快来看看吧","接活咯APP，各种牛人，应有尽有，等你来雇佣",image,"http://www.jiehuolou.com"));
	}

	@Override
	public void onReport(int reportType) {
		iUserDetailsPresenter.requestReport(reportType);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		iUserDetailsPresenter.clickItem(parent, view, position, id);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 结束
		case R.id.iv_finish:
			close();
			break;

		// 分享
		case R.id.iv_share:
            iUserDetailsPresenter.shareUser();
			break;

		// 更多
		case R.id.iv_more:
            showMoreDialog();
			break;

		// 大图查看头像
		case R.id.iv_user_avatar:
            if (null != userDetailView) {
                if (null == userDetailView.avatarList || userDetailView.avatarList.isEmpty())
                    return;
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.KEY, new Gson().toJson(userDetailView.avatarList));
                PhotoViewActivity.jumpIn(this, bundle);
            }
			break;

		// 技能
		case R.id.tv_skill:
			if (TAG == MyApplication.LOG_STATUS_BUYER) {
				return;
			}
			TAG = MyApplication.LOG_STATUS_BUYER;
			iUserDetailsPresenter.setTAG(TAG);
			break;

		// 需求
		case R.id.tv_need:
			if (TAG == MyApplication.LOG_STATUS_SELLER) {
				return;
			}
			TAG = MyApplication.LOG_STATUS_SELLER;
			iUserDetailsPresenter.setTAG(TAG);
			break;
        case R.id.iv_user_info_head_bg:
            //Bundle b = new Bundle();
            //b.putString(PhotoViewActivity.KEY, new Gson().toJson(avatarBgList));
            //PhotoViewActivity.jumpIn(this, b);
            break;
		default:
			break;
		}
	}

}
