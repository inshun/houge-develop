package com.ysp.houge.presenter;

import java.util.List;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.model.entity.bean.SystemMessageEntity;

/**
 * @描述:系统消息的presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日上午11:23:32
 * @version 1.0
 */
public interface ISystemMessagePresenter extends
		IRefreshPresenter<List<SystemMessageEntity>> {
}
