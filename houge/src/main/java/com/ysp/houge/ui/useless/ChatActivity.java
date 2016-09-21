package com.ysp.houge.ui.useless;

import java.util.List;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.ChatMessageAdapter;
import com.ysp.houge.model.entity.db.ItemMessageEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IChatPagePresenter;
import com.ysp.houge.presenter.impl.ChatPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IChatPageView;
import com.ysp.houge.utility.ControlKeyBoardLayout;
import com.ysp.houge.widget.ChatSendView;
import com.ysp.houge.widget.ChatSendView.ChatSendViewListener;
import com.ysp.houge.widget.MyActionBar;

/**
 * @author tyn
 * @version 1.0
 * @描述:聊天页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2015年8月2日下午4:54:30
 */
public class ChatActivity extends BaseFragmentActivity {
    //implements IChatPageView
    public static final String FRIEND_USERINFO = "friend_userinfo";
    private PullToRefreshListView mRefreshListView;
    private ChatSendView mChatSendView;
    private IChatPagePresenter iChatPagePresenter;
    private ChatMessageAdapter mAdapter;

    private LinearLayout root;
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    @Override
    protected void initializeState(Bundle savedInstanceState) {
        super.initializeState(savedInstanceState);
        if (getIntent().getExtras().containsKey(FRIEND_USERINFO)) {
//			iChatPagePresenter = new ChatPagePresenter(this,
//					(UserInfoEntity) getIntent().getExtras().getSerializable(FRIEND_USERINFO));
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_chat_un);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        root = (LinearLayout) findViewById(R.id.root_chat);
        mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
        mRefreshListView.setMode(Mode.PULL_FROM_START);
        mRefreshListView.getRefreshableView().setDivider(new ColorDrawable(Color.TRANSPARENT));
        mChatSendView = (ChatSendView) findViewById(R.id.mChatSendView);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ControlKeyBoardLayout.control(root, mChatSendView, false);
//		TODO 添加监听事件 解决部分机型软键盘遮挡输入框的问题
        mChatSendView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                controlKeyBoardLayout(root, mChatSendView);
            }
        });
        mAdapter = new ChatMessageAdapter(this);
        mRefreshListView.setAdapter(mAdapter);
        mChatSendView.setChatSendViewListener(new ChatSendViewListener() {

            @Override
            public void onClickSendButton(String content) {
                iChatPagePresenter.sendTxtMessage(content);
            }

            @Override
            public void onClikEditText() {
                showListViewLastPosition();
            }
        });
        mRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                iChatPagePresenter.getMessageList();
            }
        });
        iChatPagePresenter.getMessageList();

    }

    /*
    * 解决软键盘遮挡的问题
    * */
    private void controlKeyBoardLayout(final LinearLayout root, final ChatSendView mChatSendView) {
        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                root.getWindowVisibleDisplayFrame(rect);
                int rooeHeight = root.getRootView().getHeight() - rect.bottom;
                if (rooeHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    mChatSendView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
//                    int srollHeight = (location[1] + mSubmit.getHeight()) - rect.bottom;
                    root.scrollTo(0, rooeHeight);


                } else {
                    root.scrollTo(0, 0);
                }
            }
        };
        root.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    @Override
    protected void initActionbar() {
        MyActionBar actionBar = new MyActionBar(this);
        actionBar.setTitle(getString(R.string.system_message));
        actionBar.setLeftEnable(true);
        RelativeLayout actionbar = (RelativeLayout) this.findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

    //	@Override
    public void addOneItemToList(ItemMessageEntity itemMessageEntity) {
        mAdapter.getData().add(itemMessageEntity);
        mAdapter.notifyDataSetChanged();
        showListViewLastPosition();
    }

    //	@Override
    public void loadMore(final List<ItemMessageEntity> itemMessageEntities) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mAdapter.getData().addAll(0, itemMessageEntities);
                mAdapter.notifyDataSetChanged();
                mRefreshListView.onRefreshComplete();
                mRefreshListView.getRefreshableView().setSelection(itemMessageEntities.size());
            }
        }, 500);

    }

    //	@Override
    public void updateOneSentMessage(ItemMessageEntity itemMessageEntity) {
        int index = mAdapter.getData().indexOf(itemMessageEntity);
        if (index >= 0) {
            mAdapter.getData().get(index).status = itemMessageEntity.status;
            mAdapter.notifyDataSetChanged();
        }
        showListViewLastPosition();
    }

    //	@Override
    public void clearInputEdit() {
        mChatSendView.clearEdit();
    }

    //	@Override
    public void showListViewLastPosition() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshListView.getRefreshableView()
                        .setSelection(mRefreshListView.getRefreshableView().getCount() - 1);
                mRefreshListView.getRefreshableView()
                        .smoothScrollToPosition(mRefreshListView.getRefreshableView().getCount() - 1);
            }
        }, 500);
    }

    public void onEventMainThread(ItemMessageEntity itemMessageEntity) {
        iChatPagePresenter.getChatMessageFromPush(itemMessageEntity);
    }

    @Override
    protected void onPause() {
        iChatPagePresenter.clearConversationUnreadCount();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= 16 && listener != null) {
            root.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
        EventBus.getDefault().unregister(this);
    }
}
