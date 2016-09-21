package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * 聊天页面进入时的实体
 * <p/>
 * Created by it_huangxin on 2016/1/8.
 */
public class ChatPageEntity implements Serializable {

    public static final int GOOD_TYPE_SKILL = 2;
    public static final int GOOD_TYPE_NEED = 1;
    public int toChatUserId;
    public GoodsDetailEntity chatGood;
    public int chatGoodType;

    public ChatPageEntity(String userName) {
        int toChatUserId = -1;
        try {
            toChatUserId = Integer.parseInt(userName);
        } catch (Exception e) {
        }
        if (toChatUserId > 0)
            this.toChatUserId = toChatUserId;
    }

    public ChatPageEntity(int toChatUserId, GoodsDetailEntity chatGood, int chatGoodType) {
        this.toChatUserId = toChatUserId;
        this.chatGood = chatGood;
        this.chatGoodType = chatGoodType;
    }
}
