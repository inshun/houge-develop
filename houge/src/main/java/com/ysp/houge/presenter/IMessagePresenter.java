package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.model.entity.bean.WebEntity;

/**
 * 描述： 消息页面Presenter层接口
 *
 * @ClassName: IMessagePresenter
 * 
 * @author: hx
 * 
 * @date: 2016年1月2日 下午2:21:49
 * 
 *        版本: 1.0
 */
public interface IMessagePresenter<DATA> extends IRefreshPresenter<DATA> {

    /**是否需要加载回话列表*/
    void setNeedConversation(boolean loadConversation);

    void jumpToH5(WebEntity webEntity);
}
