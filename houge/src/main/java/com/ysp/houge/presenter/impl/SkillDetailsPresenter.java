package com.ysp.houge.presenter.impl;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IGoodsDetailsModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.CommentResultEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SkillEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.presenter.ISkillDetailsPresneter;
import com.ysp.houge.ui.iview.ISkillDetailsPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hx
 * @version 1.0
 * @描述: 技能详情以及其他服务页面Presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2015年10月16日上午10:46:03
 */
public class SkillDetailsPresenter implements ISkillDetailsPresneter<List<GoodsDetailEntity>> {
    private int page = 1;
    private boolean hasDate = true;

    // 当前页面显示的详情对象
    private GoodsDetailEntity entity;
    private SkillEntity skillEntity;
    private boolean isComment, isReport;
    private IGoodsDetailsModel iGoodsDetailsModel;
    private ISkillDetailsPageView iSkillDetailsPageView;



    private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

    public SkillDetailsPresenter(ISkillDetailsPageView iSkillDetailsPageView) {
        super();
        this.iSkillDetailsPageView = iSkillDetailsPageView;
        iGoodsDetailsModel = new GoodsDetailsModelImpl();

    }

    @Override
    public void refresh() {
        page = 1;
        hasDate = true;
        list.clear();

        if (null == entity) {
            iSkillDetailsPageView.refreshComplete(null);
            return;
        }

        iGoodsDetailsModel.requestOtherList(page, entity.user_id, entity.id, new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>) {
                    list = (List<GoodsDetailEntity>) data;
                    if (list.isEmpty()) {
                        hasDate = false;
                    } else {
                        hasDate = true;
                    }
                    iSkillDetailsPageView.refreshComplete(list);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                iSkillDetailsPageView.refreshComplete(null);
            }
        });
    }

    @Override
    public void loadMore() {
        page++;
        list.clear();

        if (null == entity) {
            hasDate = false;
            iSkillDetailsPageView.loadMoreComplete(list);
            return;
        }
        iGoodsDetailsModel.requestOtherList(page, entity.user_id, entity.id, new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>) {
                    list = (List<GoodsDetailEntity>) data;
                    if (list.isEmpty()) {
                        hasDate = false;
                    } else {
                        hasDate = true;
                    }

                    iSkillDetailsPageView.loadMoreComplete(list);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                page--;
                iSkillDetailsPageView.refreshComplete(list);
            }
        });
    }

    @Override
    public boolean hasData() {
        return hasDate;
    }

    @Override
    public void more() {
        iSkillDetailsPageView.showMoreDialog();
    }

    @Override
    public void shareUser() {
        iSkillDetailsPageView.share(entity);
    }

    @Override
    public void onShare() {
        // TODO 分享
        iSkillDetailsPageView.share(entity);
    }

    @Override
    public void order() {
        // TODO order
        iSkillDetailsPageView.jumpToOrder(this.entity);
    }

    @Override
    public void haveATalk() {
        // TODO 聊一聊
        ChatPageEntity chatPageEntity = new ChatPageEntity(entity.user_id, entity, ChatPageEntity.GOOD_TYPE_SKILL);
        iSkillDetailsPageView.haveATalk(chatPageEntity);
    }

    @Override
    public void onMoreLoveClick() {
        // TODO 跳转更多喜欢
        iSkillDetailsPageView.jumpToMoreLove(entity.id);
    }

    @Override
    public void onMoreCommentClick() {
        // TODO 跳转更多评论
        if (null == iSkillDetailsPageView)
            return;
        iSkillDetailsPageView.jumpToMoreComment(entity.id);
    }

    @Override
    public void clickItem(AdapterView<?> parent, View view, int position, long id) {
        loadSkillDetail((int) id);
    }

    @Override
    public void clickUser() {
        iSkillDetailsPageView.jumpUserInfo(entity.id);
    }

    @Override
    public void requestReport(int reportType) {
        if (isReport) {
            iSkillDetailsPageView.showToast("请勿重复操作");
            return;
        } else if (null == MyApplication.getInstance().getUserInfo()) {
            iSkillDetailsPageView.jumpToLogin();
            return;
        }

        iSkillDetailsPageView.showProgress();
        iGoodsDetailsModel.requestReport(iGoodsDetailsModel.REPORT_TYPE_SKILL, entity.id, reportType, entity.title, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                isReport = true;
                iSkillDetailsPageView.hideProgress();
                iSkillDetailsPageView.showToast("举报成功");
            }

            @Override
            public void onError(int errorCode, String message) {
                iSkillDetailsPageView.hideProgress();
                switch (errorCode) {
                    case ResponseCode.TIP_ERROR:
                        iSkillDetailsPageView.showToast(message);
                        break;
                    default:
                        iSkillDetailsPageView.showToast(R.string.request_failed);
                        break;
                }
            }
        });
    }



    @Override
    public void onBuyCountChange(int buyCount) {
        // TODO 价格更改
        double money = 0.0;
        try {
            money = Double.parseDouble(entity.price);
        } catch (Exception e) {
        }
        money = money * buyCount;
        iSkillDetailsPageView.calcAndShowTotalMoney(String.valueOf(money));
    }

    @Override
    public void loadSkillDetail(int id) {
        // TODO 加载技能详情
        iSkillDetailsPageView.showProgress();
        iGoodsDetailsModel.requestSkillDetaisl(id, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                iSkillDetailsPageView.hideProgress();
                if (data != null && data instanceof GoodsDetailEntity) {
                    entity = (GoodsDetailEntity) data;
                    iSkillDetailsPageView.loadDetailFinish(entity);

                    // 如果是自己的订单，隐藏售卖栏
                    if (entity.user_id == MyApplication.getInstance().getCurrentUid()) {
                        iSkillDetailsPageView.hideSell();
                    }

                    //加载到技能详情之后
                    //      加载用户其他技能(需要用户id)
                    //      加载喜欢的列表(需要技能id)
                    //      加载留言列表(需要节能功能id)
                    refresh();
                    loadLoveList(entity.id);
                    loadMessageList(entity.id);
                } else {
                    iSkillDetailsPageView.close();
                    iSkillDetailsPageView.showToast("没有对应技能信息");
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                iSkillDetailsPageView.hideProgress();
                iSkillDetailsPageView.showToast("网络错误");
                iSkillDetailsPageView.close();
            }
        });
    }

    @Override
    public void loadLoveList(final int id) {
        // 大于0才请求,节约网络消耗
        if (entity.view_count == 0) {
            iSkillDetailsPageView.loadZanFinish(null);
            return;
        }

        iSkillDetailsPageView.showProgress();
        iGoodsDetailsModel.requestLoveList(id, 0, new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                iSkillDetailsPageView.hideProgress();
                if (null != data && data instanceof List<?>) {
                    iSkillDetailsPageView.loadZanFinish((List<UserInfoEntity>) data);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                iSkillDetailsPageView.hideProgress();
                iSkillDetailsPageView.loadZanFinish(null);
            }
        });
    }

    @Override
    public void loadMessageList(int id) {
        // 大于0才请求,节约网络消耗
        if (entity.comment_count == 0) {
            iSkillDetailsPageView.loadCommentFinish(null);
            return;
        }

        iSkillDetailsPageView.showProgress();
        iGoodsDetailsModel.requestCommentList(id, 0, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                iSkillDetailsPageView.hideProgress();
                if (null != data && data instanceof CommentResultEntity) {
                    CommentResultEntity resultEntity = (CommentResultEntity) data;
                    iSkillDetailsPageView.loadCommentFinish(resultEntity.getList());
                    return;
                }
                iSkillDetailsPageView.loadCommentFinish(null);
            }

            @Override
            public void onError(int errorCode, String message) {
                iSkillDetailsPageView.hideProgress();
                iSkillDetailsPageView.loadCommentFinish(null);
            }
        });
    }

    @Override
    public void onLevelMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            iSkillDetailsPageView.showToast("请输入留言内容");
            return;
        } else if (null == MyApplication.getInstance().getUserInfo()) {
            iSkillDetailsPageView.jumpToLogin();
            return;
        } else {
            if (isComment) {
                iSkillDetailsPageView.showToast("请勿重复操作");
                return;
            }
            // 发布留言
            iSkillDetailsPageView.showProgress();
            iGoodsDetailsModel.requestMessageAdd(entity.id, message, null, new OnNetResponseCallback() {

                @Override
                public void onSuccess(Object data) {
                    iSkillDetailsPageView.hideProgress();
                    isComment = true;
                    iSkillDetailsPageView.showToast("评论成功");
                    entity.comment_count += 1;
                    loadMessageList(entity.id);
                }

                @Override
                public void onError(int errorCode, String message) {
                    iSkillDetailsPageView.hideProgress();
                    iSkillDetailsPageView.showToast("网络错误");
                }
            });
        }
    }
}