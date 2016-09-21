package com.ysp.houge.ui.iview;

import android.content.Intent;

import java.util.List;

import com.ysp.houge.model.entity.db.ItemMessageEntity;

/**
 * @描述:聊天页面view层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午5:22:10
 * @version 1.0
 */
public interface IChatPageView extends IBaseView {

    void init(Intent intent);

    void loadDate();

    void deleteChatFromMessage();

    void getUserInfo(Intent intent);
}
