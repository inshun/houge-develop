package com.ysp.houge.model.impl;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.IRecommendModel;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hx
 * @version 1.0
 * @描述: 关注Model层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2015年11月5日下午4:11:42
 */
public class RecommendModleImpl implements IRecommendModel {
    private final String TAG = getClass().getSimpleName();
    List<SortTypeEntity> entities = new ArrayList<SortTypeEntity>();

    @Override
    public void getWorkTypeList(final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        Request<WorkTypeEntity> request = new Request<WorkTypeEntity>(Method.GET,
                HttpApi.getAbsPathUrl(HttpApi.CATE_IWATCH), map, WorkTypeEntity.class, BackType.LIST, true, "", true);

        /* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object t) {

                WorkTypeEntity entity = new WorkTypeEntity(0, "热门", "yes");
                List<WorkTypeEntity> list = null;
                if (t == null) {
                    list = new ArrayList<WorkTypeEntity>();
                    list.add(entity);
                } else if (t instanceof List<?>) {
                    list = (List<WorkTypeEntity>) t;
                    list.add(0, entity);
                }

                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void getSorTypetList(OnNetResponseCallback onNetResponseCallback) {
        // TODO 排序的网络请求，这里暂时写死
        if (!entities.isEmpty()) {
            entities.clear();
        }

        String[] string = new String[]{"默认排序", "距离排序", "价格优先", "时间优先"};
        for (int i = 0; i < string.length; i++) {
            SortTypeEntity entity = new SortTypeEntity();
            entity.id = i;
            entity.sortStr = string[i];
            entities.add(entity);
        }
        onNetResponseCallback.onSuccess(entities);
    }

    @Override
    public void getRecommendList(int id, int page, final int skil_id, int order, String gps,
                                 final OnNetResponseCallback onNetResponseCallback) {
        // TODO 买家身份列表数据请求
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));
        map.put("page", String.valueOf(page));
        map.put("skil_id", String.valueOf(skil_id));
        if (order != 0) {
            map.put("order", String.valueOf(order));
        }
        if (!TextUtils.isEmpty(gps)) {
            map.put("gps", gps);
        } else {
            if (order == 0 || skil_id == 0) {
                onNetResponseCallback.onError(ResponseCode.SYSTEM_ERROR, "");
                return;
            }
        }
        Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(Method.GET,
                HttpApi.getAbsPathUrl(HttpApi.FEED_WATHC), map, GoodsDetailEntity.class,
                BackType.LIST, true, "", true);
//        request.setType(Method.POST);

		/* 数据层操作*/
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object a) {
                LogUtil.deBug(TAG,"获取数据成功",1);
                onNetResponseCallback.onSuccess(a);
            }

            @Override
            public void onError(int errorCode, String message) {
                LogUtil.deBug(TAG,"获取数据失败",1);
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    @Override
    public void getRecommendListFromLocal(int id, int page, int skil_id, int order, String gps,
                                          final OnNetResponseCallback onNetResponseCallback) {
        // TODO 买家身份列表数据请求
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));
        map.put("page", String.valueOf(page));
        map.put("skil_id", String.valueOf(skil_id));
        if (order != 0) {
            map.put("order", String.valueOf(order));
        }
        if (!TextUtils.isEmpty(gps)) {
            map.put("gps", gps);
        } else {
            if (order == 0 || skil_id == 0) {
                onNetResponseCallback.onError(ResponseCode.SYSTEM_ERROR, "");
                return;
            }
        }
        Request<GoodsDetailEntity> request = new Request<GoodsDetailEntity>(Method.GET,
                HttpApi.getAbsPathUrl(HttpApi.FEED_WATHC), map, GoodsDetailEntity.class,
                BackType.LIST, true, "", true);
//        request.setType(Method.POST);

		/* 数据层操作*/
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object a) {
                LogUtil.deBug(TAG,"获取数据成功",1);
                onNetResponseCallback.onSuccess(a);
            }

            @Override
            public void onError(int errorCode, String message) {
                LogUtil.deBug(TAG,"获取数据失败",1);
                onNetResponseCallback.onError(errorCode, message);
            }
        },true);
    }

}
