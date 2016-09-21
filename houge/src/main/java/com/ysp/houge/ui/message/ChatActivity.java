package com.ysp.houge.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.model.GoodChatRowEventBusEntity;
import com.easemob.easeui.ui.EaseChatFragment;
import com.easemob.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.CreateDialogUtil;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserContorller;
import com.ysp.houge.dialog.BottomThreeBtnDialog;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.entity.eventbus.IMLoginEventBusEntity;
import com.ysp.houge.model.impl.GoodsDetailsModelImpl;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.impl.chatmessage.ChatActivityPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IChatPageView;
import com.ysp.houge.ui.login.LoginActivity;
import com.ysp.houge.ui.order.OrderActivity;
import com.ysp.houge.utility.ChangeUtils;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.MyActionBar;

/**
 * 描述： 聊一聊聊天页面
 *
 * @ClassName: ChatActivity
 * @author: hx
 * @date: 2015年12月31日 下午3:35:31
 * <p/>
 * 版本: 1.0
 */
public class ChatActivity extends BaseFragmentActivity implements IChatPageView, EaseChatFragment.EaseChatFragmentListener, View.OnClickListener {
    public static final String CHAT_PAGE_ENTITY = "ChatPageEntity";

    private boolean isVisible = false;
    private ChatPageEntity entity;
    private UserEntity chatUser;

    //头部Title
    private MyActionBar actionBar;
    private LinearLayout good;
    private ImageView img, icon;
    private TextView title, unit, op;

    private EaseChatFragment chatFragment;
    //重置环信聊天的用户名和密码
    private ChatActivityPresenter presenter;
    private int stopResetPassWord = 0;//0只要用户无法登陆后台都重置环信聊天的密码 1 后台不重置

    public static void haveATalk(Context context, ChatPageEntity chatPageEntity) {
        if (null == MyApplication.getInstance().getUserInfo()) {
            LoginActivity.jumpIn(context);
            return;
        }

        if (chatPageEntity.toChatUserId == MyApplication.getInstance().getCurrentUid()) {
            //不能跟自己聊天
            Toast.makeText(context, "不能和自己聊天", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.CHAT_PAGE_ENTITY, chatPageEntity);
        ChatActivity.jumpIn(context, bundle);
    }


    public static void jumpIn(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ChatActivity.class);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void initActionbar() {
        actionBar = new MyActionBar(this);
        actionBar.setTitle("···");
        switch (MyApplication.getInstance().getLoginStaus()) {
            case MyApplication.LOG_STATUS_BUYER:
                actionBar.setRightIcon(R.drawable.im_more_orange);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                actionBar.setRightIcon(R.drawable.im_more_blue);
                break;
        }
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
        actionBar.setRightClickListenner(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showMoreDialog();
            }
        });
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        good = (LinearLayout) findViewById(R.id.line_good_info);
        img = (ImageView) findViewById(R.id.iv_good_img);
        icon = (ImageView) findViewById(R.id.iv_icon);
        title = (TextView) findViewById(R.id.tv_title);
        unit = (TextView) findViewById(R.id.tv_unit);
        op = (TextView) findViewById(R.id.tv_op);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        presenter = new ChatActivityPresenter();
        init(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
        MobclickAgent.onPause(this);
        EventBus.getDefault().post(new MessageCountEntity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (null != chatFragment) {
            chatFragment.onBackPressed();
        }
    }

    /**
     * IM登陆成功的消息
     */
    public void onEventMainThread(IMLoginEventBusEntity imLoginEventBusEntity) {
        Log.d("ChatActivity", "EVENT收到");
        if (imLoginEventBusEntity != null) {
            if (isVisible || imLoginEventBusEntity.getResult() == 0) {
                hideProgress();
                loadDate();
            } else if (imLoginEventBusEntity.getResult() == 1) {
                Log.d("ChatActivity", "后台数据失败");
            }
        }

    }

    /**
     * 商品点击事件
     */
    public void onEventMainThread(GoodChatRowEventBusEntity busEntity) {
        switch (busEntity.getOperation()) {
            case GoodChatRowEventBusEntity.OPERATION_SKILL:
                //跳转技能页面
                Bundle skill = new Bundle();
                skill.putInt(SkillDetailsActivity.KEY, busEntity.getId());
                SkillDetailsActivity.jumpIn(this, skill);
                break;
            case GoodChatRowEventBusEntity.OPERATION_NEED:
                //跳转需求页面
                Bundle need = new Bundle();
                need.putInt(NeedDetailsActivity.KEY, busEntity.getId());
                NeedDetailsActivity.jumpIn(this, need);
                break;
            case GoodChatRowEventBusEntity.OPERATION_SURE:
                //同意接活
                chatFragment.sendTextMessage("我同意了您的接活请求，请准时为我服务");
                //根据id去请求技能详情，然后创建订单
                requestNeedDetail(busEntity.getId());
                break;
            case GoodChatRowEventBusEntity.OPERATION_REFUSE:
                //拒绝接活
                chatFragment.sendTextMessage("抱歉，我不能让您接活");
                break;

        }
    }

    @Override
    public void init(Intent intent) {
        if (null == intent) {
            showToast("错误的信息");
            return;
        } else {
            //先获取用户信息
            getUserInfo(intent);
            //是否已经登录，如果未登陆，则等待登录
            if (EMChat.getInstance().isLoggedIn()) {
                loadDate();
            } else {
                showProgress("聊天登录中");
                //如果IM 3秒钟内登陆不上的话我们就将IM密码重置
                resetIMPassWord();

            }
        }
    }

    private void resetIMPassWord() {
        new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                //如果还未登陆成功就重置密码
                if (stopResetPassWord == 0 && !EMChat.getInstance().isLoggedIn()) {
                    presenter.reLoginIM();
                }
            }
        }.start();

    }

    private void resetIMPassWord(int id) {
        final Button button = (Button) findViewById(id);
//        禁止发送按钮再次点击避免多次开启timer
        button.setEnabled(false);
        new CountDownTimer(2 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                //如果还未登陆成功就重置密码
                if (stopResetPassWord == 0 && !EMChat.getInstance().isLoggedIn()) {
                    presenter.reLoginIM();
                }
//                回复发送按钮
                button.setEnabled(true);
            }
        }.start();


    }

    @Override
    public void loadDate() {
        if (null != chatUser) {
            chatFragment = new EaseChatFragment();
            //传入参数
            Bundle args = new Bundle();
            args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
            args.putString(EaseConstant.EXTRA_USER_ID, String.valueOf(chatUser.id));
            chatFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
            chatFragment.setChatFragmentListener(this);
        } else {
            showToast("错误的信息");
            close();
        }
    }

    @Override
    public void deleteChatFromMessage() {
        //删除和某个user的整个的聊天记录(包括本地)
        EMChatManager.getInstance().deleteConversation(String.valueOf(chatUser.id));
        //删除当前会话的某条聊天记录
        //EMConversation conversation = EMChatManager.getInstance().getConversation(String.valueOf(chatUser.id));
        //conversation.removeMessage(deleteMsg.msgId);
        showToast("删除成功");
        finish();
    }

    @Override
    public void getUserInfo(Intent intent) {
        //获取用户信息
        try {
            entity = (ChatPageEntity) intent.getExtras().getSerializable(CHAT_PAGE_ENTITY);
        } catch (Exception e) {
            LogUtil.setLogC(this, "无法获取用户信息");
        }

        if (null == entity) {
            showToast("错误的用户信息");
            return;
        } else {
            GoodsDetailEntity goodsDetailEntity = entity.chatGood;
            if (null == goodsDetailEntity) {
                good.setVisibility(View.GONE);
            } else {
                good.setVisibility(View.VISIBLE);
                op.setClickable(true);

                //图片
                if (null != goodsDetailEntity.image && !goodsDetailEntity.image.isEmpty()) {
                    MyApplication.getInstance().getImageLoaderManager().loadNormalImage(img, goodsDetailEntity.image.get(0), ImageLoaderManager.LoadImageType.NormalImage);
                }

                //title
                if (TextUtils.isEmpty(goodsDetailEntity.title)) {
                    title.setText("未知的商品名称");
                } else {
                    title.setText(goodsDetailEntity.title);
                }

                //icon ADN op ADN UNIT
                switch (entity.chatGoodType) {
                    case ChatPageEntity.GOOD_TYPE_SKILL:
                        icon.setImageResource(R.drawable.goods_title_orange);

                        StringBuffer sb = new StringBuffer();
                        sb.append(goodsDetailEntity.price);
                        if (TextUtils.isEmpty(goodsDetailEntity.unit))
                            unit.setText(sb.toString());
                        else {
                            sb.append("/");
                            sb.append(goodsDetailEntity.unit);
                            unit.setText(sb.toString());
                        }

                        //操作
                        op.setText("发送");
                        op.setBackgroundResource(R.drawable.shapa_border_orange);
                        op.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                        op.setOnClickListener(this);
                        break;
                    case ChatPageEntity.GOOD_TYPE_NEED:
                        icon.setImageResource(R.drawable.goods_title_blue);

                        unit.setText(goodsDetailEntity.price);

                        //操作
                        op.setText("接活");
                        op.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                        op.setBackgroundResource(R.drawable.shapa_round_border_blue);
                        op.setOnClickListener(this);
                        break;
                    default:
                        good.setVisibility(View.GONE);
                        break;
                }
            }
        }

        final String avatar = "drawable://" + R.drawable.user_small;

        //根据用户ID去数据库查询

        UserEntity userEntity = UserContorller.get(entity.toChatUserId);
        if (null == userEntity) {
            chatUser = new UserEntity(entity.toChatUserId, String.valueOf(entity.toChatUserId), avatar, UserInfoEntity.SEX_DEF);
            new UserInfoModelImpl().getOtherUserInfo(entity.toChatUserId, new OnNetResponseCallback() {
                @Override
                public void onSuccess(Object data) {
                    if (null != data && data instanceof UserInfoEntity) {
                        UserInfoEntity entity = (UserInfoEntity) data;
                        chatUser = new UserEntity(entity.id, entity.nick, entity.avatar, entity.sex);
                        UserContorller.createOrUpdate(chatUser);
                        actionBar.setTitle(chatUser.nick);
                        if (null != chatFragment) {
                            chatFragment.messageList.refresh();
                            chatFragment.messageList.refreshSeekTo(0);
                            chatFragment.messageList.refreshSelectLast();
                        }
                    } else {
                        chatUser = new UserEntity(entity.toChatUserId, String.valueOf(entity.toChatUserId), avatar, UserInfoEntity.SEX_DEF);
                        actionBar.setTitle(chatUser.nick);
                    }
                }

                @Override
                public void onError(int errorCode, String message) {
                    switch (errorCode) {
                        case ResponseCode.TIP_ERROR:
                            showToast(message);
                            break;
                        default:
                            showToast(R.string.request_failed);
                            break;
                    }
                    chatUser = new UserEntity(entity.toChatUserId, String.valueOf(entity.toChatUserId), avatar, UserInfoEntity.SEX_DEF);
                    actionBar.setTitle(chatUser.nick);
                }
            });
        } else {
            chatUser = userEntity;
            actionBar.setTitle(chatUser.nick);
        }
    }

    private void requestNeedDetail(int id) {
        showProgress();
        new GoodsDetailsModelImpl().requestNeedDetaisl(id, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                hideProgress();
                if (data != null && data instanceof GoodsDetailEntity) {
                    GoodsDetailEntity entity = (GoodsDetailEntity) data;
                    if (null == entity.address) {
                        showToast("订单生成失败code:地址信息错误!");
                        return;
                    }
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.skillDetailEntity = entity;
                    orderEntity.num = 1;
                    double money = ChangeUtils.toDouble(entity.price, 0);
                    orderEntity.totalMoney = money;
                    Bundle bundle = new Bundle();
                    orderEntity.user_id = ChatActivity.this.entity.toChatUserId;
                    bundle.putBoolean(OrderActivity.KEY, true);
                    bundle.putSerializable(OrderActivity.ORDER_SKILL_KEY, orderEntity);
                    OrderActivity.jumpIn(ChatActivity.this, bundle);
                } else {
                    showToast("订单生成失败code:" + 10002);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                hideProgress();
                showToast("订单生成失败code:" + 10003);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //操作
            case R.id.tv_op:
                if (EMChat.getInstance().isLoggedIn()) {
                    op.setText("已发送");
                    v.setClickable(false);
                    sendGoodMessage();
                } else {
                    showToast("聊天登录中，请稍等...");
                    //如果IM 3秒钟内登陆不上的话我们就将IM密码重置
                    resetIMPassWord(R.id.tv_op);
                }
                break;
            case R.id.tv_title:
                if (entity.chatGoodType == ChatPageEntity.GOOD_TYPE_NEED) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(SkillDetailsActivity.KEY, entity.GOOD_TYPE_SKILL);
                    SkillDetailsActivity.jumpIn(this, bundle);
                }
//                    txtBody = new TextMessageBody("希望能接您的活");
                else {
//                    txtBody = new TextMessageBody("希望能就这个技能跟您聊一聊");
                }
//                Bundle bundle = new Bundle();
//                bundle.putInt(SkillDetailsActivity.KEY, id);
//                SkillDetailsActivity.jumpIn(getActivity(), bundle);

                //geng
        }
    }

    private void sendGoodMessage() {
        if (null == entity.chatGood) {
            showToast("商品信息异常");
            return;
        }
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        TextMessageBody txtBody = null;
        if (entity.chatGoodType == ChatPageEntity.GOOD_TYPE_NEED) {
            txtBody = new TextMessageBody("希望能接您的活");
        } else {
            txtBody = new TextMessageBody("希望能就这个技能跟您聊一聊");
        }
        message.addBody(txtBody);

        // 增加自己特定的属性,目前sdk支持int,boolean,String这三种属性，可以设置多个扩展属性
        message.setAttribute("goods_id", String.valueOf(entity.chatGood.id));
        message.setAttribute("name", entity.chatGood.title);
        StringBuilder sb = new StringBuilder();
//        if (TextUtils.isEmpty(entity.chatGood.price)){
//            sb.append("免薪实习");
//        }else{
        sb.append(entity.chatGood.price);
        if (!TextUtils.isEmpty(entity.chatGood.unit)) {
            sb.append(entity.chatGood.unit);
        }
//        }
        message.setAttribute("price", sb.toString());
        message.setAttribute("state", "0");
        String imageurl = "";
        try {
            imageurl = entity.chatGood.image.get(0);
        } catch (Exception e) {
        }
        message.setAttribute("imageurl", imageurl);
        message.setAttribute("custometype", String.valueOf(entity.chatGoodType));

        message.setReceipt(String.valueOf(entity.toChatUserId));
        if (message == null) {
        } else {
            chatFragment.sendMessage(message);
        }
    }

    private void showMoreDialog() {
        if (DoubleClickUtil.isFastClick())
            return;
        BottomThreeBtnDescriptor descriptor = new BottomThreeBtnDescriptor(
                "进入Ta的主页"
                , "删除当前记录"
                , getString(R.string.cancel));
        new CreateDialogUtil().createBottomThreeBtnDialog(this, descriptor, new BottomThreeBtnDialog.OnThreeBtnClickListener() {

            @Override
            public void onThreeBtnClick(BottomThreeBtnDescriptor.ClickType clickType) {
                switch (clickType) {
                    case ClickOne:
                        jumpToUserDetails(String.valueOf(entity.toChatUserId));
                        break;
                    case ClickTwo:
                        deleteChatFromMessage();
                        break;
                    case Cancel:
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
    }

    @Override
    public void onEnterToChatDetails() {
    }

    @Override
    public void onAvatarClick(String username) {
        jumpToUserDetails(username);
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

    private void jumpToUserDetails(String username) {
        int id = -1;
        try {
            id = Integer.parseInt(username);
        } catch (Exception e) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(UserDetailsActivity.KEY, id);
        UserDetailsActivity.jumpIn(this, bundle);
    }
}
