package com.ysp.houge.model.impl;

import android.util.Log;

import com.android.volley.Request.Method;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.model.ISkillMoneyUnitModel;
import com.ysp.houge.model.entity.bean.SkillMoneyUnitEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;
import com.ysp.houge.utility.volley.Request.BackType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/7/28
 * Email: m13478943650@163.com
 */
public class SkillMoneyUnitModelImpl implements ISkillMoneyUnitModel {

    @Override
    public void getMoneyUnit(final OnNetResponseCallback callback) {

        Map<String, String> map = new HashMap<>();
        Request<SkillMoneyUnitEntity> request = new Request<SkillMoneyUnitEntity>(Method.GET
                , HttpApi.getAbsPathUrl(HttpApi.SKILL_MONEY_UNIT)
                , map, SkillMoneyUnitEntity.class,BackType.LIST, true);

        HttpRequest.newInstance().request(request, "lists", new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                Log.d("SkillMoneyUnitModelImpl", "data:" + data);
                List <SkillMoneyUnitEntity> entities = new ArrayList<SkillMoneyUnitEntity>();
                entities = (List<SkillMoneyUnitEntity>) data;
                callback.onSuccess(entities);
            }

            @Override
            public void onError(int errorCode, String message) {

                callback.onError(errorCode, message);
            }
        });


    }
}
