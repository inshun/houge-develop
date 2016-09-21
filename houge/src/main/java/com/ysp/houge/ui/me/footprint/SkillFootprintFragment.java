package com.ysp.houge.ui.me.footprint;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillDetailstAdapter;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.presenter.ISkillFootprintPresenter;
import com.ysp.houge.presenter.impl.SkillFootprintPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.ISkillFootprintPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.share.ShareUtils;

import java.util.List;

/**
 * @author it_hu
 *         <p/>
 *         我的足迹技能列表
 */
public class SkillFootprintFragment extends BaseFragment implements ISkillFootprintPageView {
    public SkillDetailstAdapter adapter;

    /**
     * 下拉刷新listView
     */
    private PullToRefreshListView mRefreshListView;
    private ListViewHelper<List<GoodsDetailEntity>> listViewHelper;
    private ISkillFootprintPresenter<List<GoodsDetailEntity>> iSkillFootprintPresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_skill_footprint;
    }

    @Override
    protected void initActionbar(View view) {
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        iSkillFootprintPresenter = new SkillFootprintPresenter(this);
        listViewHelper = new ListViewHelper<List<GoodsDetailEntity>>(mRefreshListView);
        listViewHelper.setRefreshPresenter(iSkillFootprintPresenter);
        adapter = new SkillDetailstAdapter(getActivity(), SizeUtils.getScreenWidth(getActivity()));
        listViewHelper.setAdapter(adapter);
        adapter.setListener(iSkillFootprintPresenter);
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void jumpToSkillDetailPage(int id) {
        // TODO 跳转到详情页面
        Bundle bundle = new Bundle();
        bundle.putInt(SkillDetailsActivity.KEY, id);
        SkillDetailsActivity.jumpIn(getActivity(), bundle);
    }

    @Override
    public void jumpToUserInfo(int id) {
        // TODO 跳转到个人详情
        Bundle bundle = new Bundle();
        bundle.putInt(UserDetailsActivity.KEY, id);
        UserDetailsActivity.jumpIn(getActivity(), bundle);
    }

    @Override
    public void jumpToHaveATalk(ChatPageEntity chatPageEntity) {
        ChatActivity.haveATalk(getActivity(), chatPageEntity);
    }

    @Override
    public void share(GoodsDetailEntity detailEntity) {
        ShareUtils.share(getActivity(), detailEntity, 1);
    }
}
