package com.ysp.houge.model.impl;

import android.text.TextUtils;
import android.util.Pair;

import com.android.volley.Request.Method;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserContorller;
import com.ysp.houge.model.IMessageModel;
import com.ysp.houge.model.entity.bean.SystemMessageEntity;
import com.ysp.houge.model.entity.db.MessageEntity;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

//import com.easemob.chat.EMMessage;
;

//import com.easemob.chat.EMMessage;

/**
 * 描述：
 *
 * @ClassName: MessageModelImpl
 * @author: hx
 * @date: 2016年1月2日 下午2:42:45
 * <p/>
 * 版本: 1.0
 */
public class MessageModelImpl implements IMessageModel {
    List<MessageEntity> lists = new ArrayList<MessageEntity>();

    @Override
    public void getMessageList(boolean loadConversation, OnNetResponseCallback onNetResponseCallback) {
        // TODO 获取消息列表
        lists.clear();
        //加载系统的消息
        loadMessageList(loadConversation, onNetResponseCallback);
    }

    @Override
    public void getMessageList(int type, int page, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf(type));
        map.put("page", String.valueOf(page));
        Request<SystemMessageEntity> request = new Request<SystemMessageEntity>(HttpApi.getAbsPathUrl(HttpApi.MESSAGE_LIST_LIST), map, SystemMessageEntity.class,
                Request.BackType.LIST, true);
        request.setType(Method.GET);

		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                onNetResponseCallback.onSuccess(t);
            }

            @Override
            public void onError(int errorCode, String message) {
                onNetResponseCallback.onError(errorCode, message);
            }
        });
    }

    private void loadMessageList(final boolean loadConversation, final OnNetResponseCallback onNetResponseCallback) {
        Map<String, String> map = new HashMap<String, String>();
        Request<MessageEntity> request = new Request<MessageEntity>(HttpApi.getAbsPathUrl(HttpApi.MESSAGE_LIST_INDEX), map, MessageEntity.class,
                Request.BackType.LIST, true);

		/* 数据层操作 */
        HttpRequest.newInstance().request(request, "list", new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object t) {
                lists.clear();
                try {
                    loadMessage(t);
                } catch (Exception e) {
                }
                if (loadConversation) {
                    loadConversationList(onNetResponseCallback);
                } else {
                    onNetResponseCallback.onSuccess(lists);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                if (loadConversation) {
                    loadConversationList(onNetResponseCallback);
                } else {
                    onNetResponseCallback.onError(errorCode, message);
                }
            }
        });
    }

    private void loadMessage(Object t) {
        if (null != t && t instanceof List<?>) {
            List<MessageEntity> list = (ArrayList<MessageEntity>) t;
            if (list.isEmpty()) return;
            for (int i = 0; i < list.size(); i++) {
                MessageEntity messageEntity = list.get(i);
                //未读消息数量
                messageEntity.unReadCount = messageEntity.count;

                //消息内容
                messageEntity.message = messageEntity.content;

                //消息时间
                if (!TextUtils.isEmpty(messageEntity.created_at) && messageEntity.created_at.length() == 10) {
                    try {
                        messageEntity.time = Long.parseLong(messageEntity.created_at + "000");
                    } catch (Exception e) {
                        messageEntity.time = -1l;
                    }
                } else {
                    messageEntity.time = -1l;
                }

                if (null != messageEntity)
                    lists.add(messageEntity);
            }
        }
    }

    private void loadConversationList(OnNetResponseCallback onNetResponseCallback) {
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
        // 先把所有的对话 变成MessageEntity对象
        for (EMConversation conversation : conversations.values()) {
            if (conversation.getAllMessages().size() != 0) {
                int id = 0;
                try {
                    id = Integer.parseInt(conversation.getUserName());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                UserEntity userEntity = UserContorller.get(id);
                String icon = "";
                String title = "";
                if (null != userEntity) {
                    icon = userEntity.avatar;
                    title = userEntity.nick;
                }

                EMMessage message = conversation.getLastMessage();
                MessageEntity entity = new MessageEntity(icon, String.valueOf(conversation.getUnreadMsgCount()), title, EaseCommonUtils
                        .getMessageDigest(message, (MyApplication.getInstance().getApplicationContext())),
                        message.getMsgTime());
                entity.conversation = conversation;
                lists.add(entity);
            }
        }

        //排序(按时间)
        List<Pair<Long, MessageEntity>> sortList = new ArrayList<Pair<Long, MessageEntity>>();
        synchronized (conversations) {
            for (int i = 0; i < lists.size(); i++) {
                sortList.add(new Pair<Long, MessageEntity>(lists.get(i).time, lists.get(i)));
            }
        }

        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lists.clear();
        for (Pair<Long, MessageEntity> sortItem : sortList) {
            lists.add(sortItem.second);
        }
        onNetResponseCallback.onSuccess(lists);
    }

    /**
     * 根据最后一条消息的时间排序
     *
     * @param messageEntise
     */
    private void sortConversationByLastChatTime(List<Pair<Long, MessageEntity>> messageEntise) {
        Collections.sort(messageEntise, new Comparator<Pair<Long, MessageEntity>>() {
            @Override
            public int compare(final Pair<Long, MessageEntity> con1, final Pair<Long, MessageEntity> con2) {
                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}
