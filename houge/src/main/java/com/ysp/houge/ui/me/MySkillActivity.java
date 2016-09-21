package com.ysp.houge.ui.me;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkilltAdapter;
import com.ysp.houge.adapter.SkilltAdapter.OnMoreCliclk;
import com.ysp.houge.app.CreateDialogUtil;
import com.ysp.houge.dialog.BottomThreeBtnDialog.OnThreeBtnClickListener;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor.ClickType;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.presenter.IMySkillPresenter;
import com.ysp.houge.presenter.impl.MySkillPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IMySkillPageView;
import com.ysp.houge.ui.publish.SkillPulishActivity;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;

import java.util.List;

/**
 * 描述： 我的技能
 *
 * @ClassName: MySkillActivity
 * @author: hx
 * @date: 2015年12月26日 下午12:51:18
 * <p/>
 * 版本: 1.0
 */
@SuppressLint("InflateParams")
public class MySkillActivity extends BaseFragmentActivity implements IMySkillPageView, OnClickListener, OnMoreCliclk {
    private View headView;
    private TextView addSkill;

    private SkilltAdapter adapter;

    /**
     * 下拉刷新listView
     */
    private PullToRefreshListView mRefreshListView;
    private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
    private IMySkillPresenter<List<GoodsDetailEntity>> iMySkillPresenter;

    public static void jumpIn(Context context) {
        context.startActivity(new Intent(context, MySkillActivity.class));
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_skill);
        check();
    }

    public void check() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    100);
        } else {
            //已赋予权限
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 允许
            } else {
                //无权限
                showToast("请打开对应的权限，否者会导致App无法正常运行！");
            }
        }
    }


    @Override
    protected void initActionbar() {
        MyActionBar actionBar = new MyActionBar(this);
        actionBar.setTitle(getString(R.string.me_skill));
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        headView = LayoutInflater.from(this).inflate(R.layout.head_my_skill, null);
        addSkill = (TextView) headView.findViewById(R.id.tv_add_skill);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dip2px(this, 48));
        addSkill.setLayoutParams(params);
        addSkill.setText("发布技能赚钱");
        addSkill.setOnClickListener(this);

        mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
        mRefreshListView.getRefreshableView().addHeaderView(headView);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        iMySkillPresenter = new MySkillPresenter(this);
        listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
        listViewHelper.setRefreshPresenter(iMySkillPresenter);
        adapter = new SkilltAdapter(this);
        adapter.setMoreClicl(this);
        listViewHelper.setAdapter(adapter);
        listViewHelper.refresh();
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
    public void onClick(View v) {
        switch (v.getId()) {
            // 添加
            case R.id.tv_add_skill:
                jumpToSkillPulish(null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(final GoodsDetailEntity detailEntity) {
        if (DoubleClickUtil.isFastClick()) {
            return;
        }

        BottomThreeBtnDescriptor descriptor = new BottomThreeBtnDescriptor("",
                getString(R.string.delete));
        new CreateDialogUtil().createBottomThreeBtnDialog(this, descriptor, new OnThreeBtnClickListener() {

            @Override
            public void onThreeBtnClick(ClickType clickType) {
                switch (clickType) {

                    case ClickOne:
                        showToast("暂不支持此操作");
                        // jumpToSkillPulish(detailEntity);
                        break;

                    case ClickTwo:
                        iMySkillPresenter.delete(detailEntity.id);
                        break;

                    case Cancel:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void jumpToSkillPulish(GoodsDetailEntity detailEntity) {
        Bundle bundle = null;
        if (null != detailEntity) {
            bundle = new Bundle();
            bundle.putSerializable(SkillPulishActivity.KEY, detailEntity);
        }
        SkillPulishActivity.JumpIn(this, bundle);
        overridePendingTransition(R.anim.slide_in_from_top, R.anim.activity_stay);

    }
}
