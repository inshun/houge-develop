package com.ysp.houge.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.easemob.chat.EMChat;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.MessageAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.WebEntity;
import com.ysp.houge.model.entity.db.MessageEntity;
import com.ysp.houge.model.entity.eventbus.IMLoginEventBusEntity;
import com.ysp.houge.presenter.IMessagePresenter;
import com.ysp.houge.presenter.impl.MessagePresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.IMessageView;
import com.ysp.houge.ui.me.CommentListActivity;
import com.ysp.houge.ui.me.LoveMeListActivity;
import com.ysp.houge.ui.me.WebViewActivity;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.message.SystemMessageActivity;
import com.ysp.houge.utility.ChangeUtils;
import com.ysp.houge.widget.MyActionBar;

import java.util.List;

/**
 * @描述:消息页面
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月20日上午10:45:17
 * @version 1.0
 */
public class MessageFragment extends BaseFragment implements IMessageView,AdapterView.OnItemClickListener {
	private ListViewHelper<List<MessageEntity>> listViewHelper;
	private PullToRefreshListView mRefreshListView;
	private MessageAdapter mAdapter;
	private IMessagePresenter iMessagePresenter;

    private MyActionBar actionBar;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	protected void initActionbar(View view) {
        actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("消息");
		actionBar.setLeftEnable(false);
		RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_message;
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mRefreshListView);
        mRefreshListView.setOnItemClickListener(this);
		mAdapter = new MessageAdapter(getActivity());
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iMessagePresenter = new MessagePresenter(this);
		listViewHelper = new ListViewHelper<List<MessageEntity>>(mRefreshListView);
        listViewHelper.setRefreshPresenter(iMessagePresenter);
		listViewHelper.setAdapter(mAdapter);

        if (EMChat.getInstance().isLoggedIn()){
            iMessagePresenter.setNeedConversation(true);
        }else {
            iMessagePresenter.setNeedConversation(false);
        }
		listViewHelper.refresh();
	}

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<MessageEntity> t) {
		if (isAdded()) {
            mAdapter.getData().clear();
			listViewHelper.refreshComplete(t);
		}
	}

	@Override
	public void loadMoreComplete(List<MessageEntity> t) {
	}

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && null != iMessagePresenter){
            if (EMChat.getInstance().isLoggedIn()){
                iMessagePresenter.setNeedConversation(true);
            }else {
                iMessagePresenter.setNeedConversation(false);
            }
            iMessagePresenter.refresh();
            if (null != actionBar)
                actionBar.initDefult();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null!=mAdapter.getData().get(position-1).conversation){
            ChatPageEntity chatPageEntity=new ChatPageEntity(mAdapter.getData().get(position-1).conversation.getUserName());
            ChatActivity.haveATalk(getActivity(), chatPageEntity);
        }else {
            MessageEntity messageEntity = mAdapter.getData().get(position-1);
            if (null == messageEntity)
                return;

            int itemCount = ChangeUtils.toInt(messageEntity.unReadCount,0);
            MessageCountEntity countEntity = MyApplication.getInstance().getMessageCountEntity();
            countEntity.count = String.valueOf(ChangeUtils.toInt(countEntity.count,0) - itemCount);
            MyApplication.getInstance().setMessageCountEntity(countEntity);
            EventBus.getDefault().post(new MessageCountEntity());

            messageEntity.unReadCount = "0";
            mAdapter.getData().remove(position -1);
            mAdapter.getData().add(position -1,messageEntity);
            mAdapter.notifyDataSetChanged();
            switch (messageEntity.type){

                //订单消息
                case 1:
                    break;

                //2系统消息
                case 2:
                    SystemMessageActivity.jumpIn(getActivity(),null);
                    break;

                //3活动消息
                case 3:
                    //跳转H5
                    WebEntity webEntity = new WebEntity(messageEntity.url,"活动详情");
                    iMessagePresenter.jumpToH5(webEntity);
                    break;

                //4评论消息
                case 4:
                    CommentListActivity.jumpIn(getActivity(), null);
                    break;

                //5喜欢消息
                case 5:
                    LoveMeListActivity.jumpIn(getActivity(), null);
                    break;
                default:
                    refresh();
                    break;
            }
        }
    }

    //暂时无用的
	public void onEventMainThread(IMLoginEventBusEntity imLoginEventBusEntity) {
        if (EMChat.getInstance().isLoggedIn()){
            iMessagePresenter.setNeedConversation(true);
        }else {
            iMessagePresenter.setNeedConversation(false);
        }
        listViewHelper.refresh();
	}

     public void refreshMessage(){
         listViewHelper.refresh();

     }

	@Override
	public void onResume() {
		super.onResume();
        //统计页面，"MainScreen"为页面名称，可自定义
        MobclickAgent.onPageStart(getClass().getSimpleName());
        if(null != iMessagePresenter)
            iMessagePresenter.refresh();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

    @Override
    public void openH5(WebEntity webEntity) {
         Bundle bundle = new Bundle();
         bundle.putSerializable(WebViewActivity.KEY,webEntity);
         WebViewActivity.jumpIn(getActivity(),bundle);
    }


}
