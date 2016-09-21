package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.ISearchModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SearchGoodResultEntity;
import com.ysp.houge.model.impl.SearchModelImpl;
import com.ysp.houge.presenter.ISearchNeedPresenter;
import com.ysp.houge.ui.iview.ISearchNeedPageView;
import com.ysp.houge.ui.search.SearchNeedFragment;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import android.text.TextUtils;

public class SearchNeedPresenter implements ISearchNeedPresenter<List<GoodsDetailEntity>> {
    private String text;

    private int page = 1;
    private boolean hasDate = true;

    private ISearchModel iSearchModel;
    private ISearchNeedPageView iSearchNeedPageView;

    public SearchNeedPresenter(ISearchNeedPageView iSearchNeedPageView) {
        super();
        iSearchModel = new SearchModelImpl();
        this.iSearchNeedPageView = iSearchNeedPageView;
    }

    @Override
    public void refresh() {
        if (TextUtils.isEmpty(text)) {
            iSearchNeedPageView.refreshComplete(null);
        }

        page = 1;
        iSearchNeedPageView.showProgress();
        iSearchModel.searchGood(page, text, 1, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                iSearchNeedPageView.hideProgress();
                if (data != null && data instanceof SearchGoodResultEntity) {
                    SearchGoodResultEntity resultEntity = (SearchGoodResultEntity) data;
                    if (null != resultEntity.getList() && !resultEntity.getList().isEmpty()) {
                        iSearchNeedPageView.refreshComplete(resultEntity.getList());
                        hasDate = resultEntity.isNext();
                    } else {
                        iSearchNeedPageView.refreshComplete(null);
                        iSearchNeedPageView.showToast("没有相关需求");
                        hasDate = false;
                    }
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                iSearchNeedPageView.hideProgress();
                iSearchNeedPageView.refreshComplete(null);
            }
        });
    }

    @Override
    public void loadMore() {
        page++;
        iSearchModel.searchGood(page, text, 1, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (data != null && data instanceof SearchGoodResultEntity) {
                    SearchGoodResultEntity resultEntity = (SearchGoodResultEntity) data;
                    if (null != resultEntity.getList() && !resultEntity.getList().isEmpty()) {
                        iSearchNeedPageView.loadMoreComplete(resultEntity.getList());
                        hasDate = resultEntity.isNext();
                    } else {
                        iSearchNeedPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
                        hasDate = false;
                    }

                }
            }

            @Override
            public void onError(int errorCode, String message) {
                iSearchNeedPageView.loadMoreComplete(new ArrayList<GoodsDetailEntity>());
            }
        });
    }

    @Override
    public boolean hasData() {
        return hasDate;
    }

    @Override
    public void OnClick(int position, int operation) {
        switch (operation) {
            case OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
                iSearchNeedPageView.jumpToNeedDetailPage(
                        ((SearchNeedFragment) iSearchNeedPageView).adapter.getData().get(position).id);
                break;
            case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
                iSearchNeedPageView.jumpToUserInfoDetailPage(
                        ((SearchNeedFragment) iSearchNeedPageView).adapter.getData().get(position).user_id);
                break;
            case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
                ChatPageEntity chatPageEntity = new ChatPageEntity(((SearchNeedFragment) iSearchNeedPageView).adapter.getData().get(position).user_id, ((SearchNeedFragment) iSearchNeedPageView).adapter.getData().get(position), ChatPageEntity.GOOD_TYPE_NEED);
                iSearchNeedPageView.jumpToHaveATalk(chatPageEntity);
                break;
            case OnItemClickListener.CLICK_OPERATION_SHARE:
                iSearchNeedPageView.share(((SearchNeedFragment) iSearchNeedPageView).adapter.getData().get(position));
                break;
        }
    }

    @Override
    public void setSearchText(String string) {
        this.text = string;
    }

}
