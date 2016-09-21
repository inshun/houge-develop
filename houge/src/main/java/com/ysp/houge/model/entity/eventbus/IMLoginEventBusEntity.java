package com.ysp.houge.model.entity.eventbus;

/**
 * IM登录成功的消息
 * <p/>
 * Created by it_huangxin on 2016/1/10.
 */
public class IMLoginEventBusEntity {
    private int result;

    /*0表示成功 1表示失败*/

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
