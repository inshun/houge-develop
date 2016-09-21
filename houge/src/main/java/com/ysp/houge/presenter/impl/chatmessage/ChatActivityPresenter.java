package com.ysp.houge.presenter.impl.chatmessage;

import com.ysp.houge.model.IReIMModel;
import com.ysp.houge.model.impl.ReIMModelImpl;
import com.ysp.houge.presenter.IChatActivityPresenter;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/11
 * Email: m13478943650@163.com
 */
public class ChatActivityPresenter implements IChatActivityPresenter {
    private IReIMModel model;

    public ChatActivityPresenter() {
        model = new ReIMModelImpl();
    }

    @Override
    public void reLoginIM() {
        model.login();
    }
}
