package com.ysp.houge.model.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by it_huangxin on 2016/1/28.
 */
public class Count implements Serializable {
    @SerializedName(value = "count(*)")
    public int count;
}
