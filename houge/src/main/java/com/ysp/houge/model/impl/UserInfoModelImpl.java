package com.ysp.houge.model.impl;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.MakeMoneyByShareEntity;
import com.ysp.houge.model.entity.bean.MyBuyEntity;
import com.ysp.houge.model.entity.bean.MyNeedEntity;
import com.ysp.houge.model.entity.bean.MySellEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.HashMap;
import java.util.Map;

public class UserInfoModelImpl implements IUserInfoModel {

    /**
     * 获取个人信息
     */
    @Override
    public void meInfoRequest(final boolean needSave, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.GET_USER_INFO), map,
                UserInfoEntity.class, BackType.BEAN, true);
        request.setType(Method.GET);
        /* 数据层操作 */
        HttpRequest.getDataFromLocal(false);
        HttpRequest.newInstance().request(request, "user", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    /**
     * 修改个人信息
     */
    @Override
    public void reviseUserInfo(String avatar, String nick, String sex, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        if (TextUtils.isEmpty(avatar) && TextUtils.isEmpty(nick) && TextUtils.isEmpty(sex)) {
            onNetResponseCallback.onError(ResponseCode.TIP_ERROR, "请至少控制一个修改项");
            return;
        }

        if (!TextUtils.isEmpty(avatar)) {
                map.put("avatar", avatar);
        }

        if (!TextUtils.isEmpty(nick)) {
            map.put("nick", nick);
        }

        if (!TextUtils.isEmpty(sex)) {
            map.put("sex", sex);
        }

        Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.EDIT_USERINFO), map, String.class,
                BackType.STRING, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "msg", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void getOtherUserInfo(int uid, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", String.valueOf(uid));
        Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.GET_OTHER_INFO),
                map, UserInfoEntity.class, BackType.BEAN, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "user", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                if (t != null && t instanceof UserInfoEntity) {
                    onNetResponseCallback.onSuccess(t);
                } else {
                    onNetResponseCallback.onError(ResponseCode.PARSER_ERROR, "");
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }

        });
    }

    @Override
    public void myBuyer(int page, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", String.valueOf(page));
        Request<MyBuyEntity> request = new Request<MyBuyEntity>(HttpApi.getAbsPathUrl(HttpApi.MY_BUY), map,
                MyBuyEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void getNeedList(int page, int id, String goods_info_ids, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", String.valueOf(page));
        map.put("id", String.valueOf(id));
        map.put("goods_info_ids", String.valueOf(goods_info_ids));
        LogUtil.setLogWithE("AA", goods_info_ids+"goods_info_ids");
        Request<MyNeedEntity> request = new Request<MyNeedEntity>(HttpApi.getAbsPathUrl(HttpApi.NEED_LIST),
                map, MyNeedEntity.class, BackType.BEAN, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void meSeller(int page, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", String.valueOf(page));
        Request<MySellEntity> request = new Request<MySellEntity>(HttpApi.getAbsPathUrl(HttpApi.MY_SELL), map,
                MySellEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void getSkillList(int page, int id, String goods_info_ids, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", String.valueOf(page));
        map.put("id", String.valueOf(id));
        map.put("goods_info_ids", String.valueOf(goods_info_ids));
        Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(HttpApi.getAbsPathUrl(HttpApi.SKILL_LIST), map,
                GoodsDetailEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void submitCashDeposit(final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.SUBMIT_CASH_DEPOSIT), map,
                String.class, BackType.STRING, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "order_query", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void getProtectedfee(final OnNetResponseCallback onNetResponseCallback) {

        Map<String, String> map = new HashMap<String, String>();
        Request<String> request = new Request<String>(HttpApi.getAbsPathUrl(HttpApi.PROTECTEDFEE_MONEY), map, String.class,
                BackType.STRING, true);
        request.setType(Method.GET);
        /* 数据层操作 */
        HttpRequest.newInstance().request(request, "fee", new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }
            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void unfreezeCashDeposit(final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        Request<MakeMoneyByShareEntity> request = new Request<MakeMoneyByShareEntity>(
                HttpApi.getAbsPathUrl(HttpApi.UNFREEZE_CASH_DEPOSIT), map, MakeMoneyByShareEntity.class, BackType.BEAN,
                true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });

    }

    @Override
    public void myFootprintSkill(int page, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf(1));// 1代表技能
        map.put("page", String.valueOf(page));
        Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(HttpApi.getAbsPathUrl(HttpApi.MY_FOOTPRINT),
                map, GoodsDetailEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }

        });
    }

    @Override
    public void myFootprintNeed(int page, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf(2));// 2代表需求
        map.put("page", String.valueOf(page));
        Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(HttpApi.getAbsPathUrl(HttpApi.MY_FOOTPRINT),
                map, GoodsDetailEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }

        });
    }

    @Override
    public void myFootprintUser(int page, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf(3));// 3代表用户
        map.put("page", String.valueOf(page));
        Request<UserInfoEntity> request = new Request<UserInfoEntity>(HttpApi.getAbsPathUrl(HttpApi.MY_FOOTPRINT), map,
                UserInfoEntity.class, BackType.LIST, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }

        });
    }

    @Override
    public void getShareInfo(final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        Request<MakeMoneyByShareEntity> request = new Request<MakeMoneyByShareEntity>(
                HttpApi.getAbsPathUrl(HttpApi.INVITE_CODE), map, MakeMoneyByShareEntity.class, BackType.BEAN, true);
        request.setType(Method.GET);
		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "data", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }

        });
    }
}
