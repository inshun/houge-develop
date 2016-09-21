package com.ysp.houge.presenter.impl;

import com.ysp.houge.R;
import com.ysp.houge.model.IMessageModel;
import com.ysp.houge.model.entity.bean.SystemMessageEntity;
import com.ysp.houge.model.impl.MessageModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ISystemMessagePresenter;
import com.ysp.houge.ui.iview.ISystemMessagePageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:系统消息页面的presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午2:41:22
 * @version 1.0
 */
public class SystemMessagePresenter extends
		BasePresenter<ISystemMessagePageView> implements
        ISystemMessagePresenter {
    private int page;
    private boolean hasDate;
    private List<SystemMessageEntity> list = new ArrayList<SystemMessageEntity>();

	private IMessageModel iMessageModel;
	public SystemMessagePresenter(ISystemMessagePageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iMessageModel = new MessageModelImpl();
	}

	@Override
	public void refresh() {
        page = 1;
        hasDate = true;
        list.clear();

        iMessageModel.getMessageList(2, page, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>){
                    list = (List<SystemMessageEntity>) data;

                    if (list.isEmpty() || list.size() < 10){
                        hasDate = false;
                    }else {
                        hasDate = true;
                    }
                    mView.refreshComplete(list);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                switch (errorCode){
                    case ResponseCode.TIP_ERROR:
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;
                }
                mView.refreshComplete(list);
            }
        });
	}

	@Override
	public void loadMore() {
        page++;
        hasDate = true;
        list.clear();

        iMessageModel.getMessageList(2, page, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>){
                    list = (List<SystemMessageEntity>) data;

                    if (list.isEmpty() || list.size() < 10){
                        hasDate = false;
                    }else {
                        hasDate = true;
                    }
                    mView.loadMoreComplete(list);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                switch (errorCode){
                    case ResponseCode.TIP_ERROR:
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;
                }
                mView.loadMoreComplete(list);
            }
        });
	}

	@Override
	public boolean hasData() {
		return hasDate;
	}

}
