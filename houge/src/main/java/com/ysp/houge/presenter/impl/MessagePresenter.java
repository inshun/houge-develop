package com.ysp.houge.presenter.impl;

import java.util.List;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.IMessageModel;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.WebEntity;
import com.ysp.houge.model.entity.db.MessageEntity;
import com.ysp.houge.model.impl.MessageModelImpl;
import com.ysp.houge.presenter.IMessagePresenter;
import com.ysp.houge.ui.iview.IMessageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： 消息Presenter层
 *
 * @ClassName: MessagePresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月31日 下午7:10:01
 * 
 * 版本: 1.0
 */
public class MessagePresenter implements IMessagePresenter<List<MessageEntity>> {
    private boolean loadConversation;

	private IMessageView iMessageView;
	private IMessageModel iMessageModel;

	public MessagePresenter(IMessageView iMessageView) {
		super();
		this.iMessageView = iMessageView;
		iMessageModel = new MessageModelImpl();
	}

	@Override
	public void refresh() {
		iMessageModel.getMessageList(loadConversation,new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				iMessageView.refreshComplete((List<MessageEntity>) data);
                EventBus.getDefault().post(new MessageCountEntity());
			}

			@Override
			public void onError(int errorCode, String message) {
                iMessageView.refreshComplete(null);
			}
		});
	}

	@Override
	public void loadMore() {

	}

	@Override
	public boolean hasData() {
		return false;
	}

    @Override
    public void setNeedConversation(boolean loadConversation) {
        this.loadConversation=loadConversation;
    }

    @Override
    public void jumpToH5(final WebEntity webEntity) {
        iMessageView.showProgress();
        iMessageModel.getMessageList(3, 0, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                iMessageView.hideProgress();
                iMessageView.openH5(webEntity);

            }

            @Override
            public void onError(int errorCode, String message) {
                iMessageView.hideProgress();
                iMessageView.showToast(R.string.request_failed);
            }
        });
    }
}
