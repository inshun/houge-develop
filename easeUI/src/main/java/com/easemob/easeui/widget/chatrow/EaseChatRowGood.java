package com.easemob.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.R;
import com.easemob.easeui.model.GoodChatRowEventBusEntity;
import com.ypy.eventbus.EventBus;

/**
 *  商品 ChatRow
 *
 * Created by it_huangxin on 2016/1/11.
 */
public class EaseChatRowGood extends EaseChatRowText {
    private View line;//线
    private TextView topMessage;//顶部消息
    private ImageView goodImg;//商品图片
    private TextView goodTitle;//商品名称
    private TextView goodPrice;//商品价格
    private LinearLayout good;//商品布局
    private LinearLayout goodOp;//操作外层线性布局
    private Button sure,refuse;//同意或者拒绝接活

    public EaseChatRowGood(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_good : R.layout.ease_row_sent_good, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);

        line    = findViewById(R.id.line);
        topMessage  = (TextView) findViewById(R.id.tv_top_message);
        goodImg = (ImageView) findViewById(R.id.iv_good_img);
        goodTitle   = (TextView) findViewById(R.id.tv_good_title);
        goodPrice   = (TextView) findViewById(R.id.tv_goods_price);
        good  = (LinearLayout) findViewById(R.id.line_good);
        goodOp  = (LinearLayout) findViewById(R.id.line_good_op);
        sure = (Button) findViewById(R.id.btn_sure);
        refuse = (Button) findViewById(R.id.btn_refuse);
    }

    @Override
    public void onSetUpView() {
        //自定义消息内容 goods_id为 需求ID name 为需求名 state为状态0未处理1允许2拒绝 price 价格 imageurl 图片的地址 custometype 1为需求 2为技能
        String type = message.getStringAttribute("custometype", "");
        if (TextUtils.equals(message.getStringAttribute("custometype", ""), "1")) {
            int color_blue = Color.parseColor("#7ad1f8");
            //顶部背景写成蓝色
            topMessage.setBackgroundColor(color_blue);
            sure.setBackgroundColor(color_blue);
            refuse.setBackgroundColor(color_blue);

            topMessage.setText("希望能接您的活");
        }else{
            int color_orange= Color.parseColor("#ff6724");
            //顶部背景写成橙色
            topMessage.setBackgroundColor(color_orange);
            sure.setBackgroundColor(color_orange);
            refuse.setBackgroundColor(color_orange);

            topMessage.setText("希望能就这个技术跟您聊一聊");
        }

        //加载图片
        String img = message.getStringAttribute("imageurl","drawable://"+R.drawable.defaultpic);
        Glide.with(context).load(img).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.defaultpic).into(goodImg);

        //标题
        String title= message.getStringAttribute("name","未知的商品");
        goodTitle.setText(title);
        //价格
        String price= message.getStringAttribute("price","0.0元");
        goodPrice.setText(price);

        //如果是收到，并且是需求，并且状态是未处理，那么显示下面的操作按钮
        if (message.direct == EMMessage.Direct.RECEIVE && TextUtils.equals(message.getStringAttribute("custometype", ""), "1")) {
            line.setVisibility(VISIBLE);
            goodOp.setVisibility(VISIBLE);

            //如果未处理状态，可以点击，否则不能点击
            if (TextUtils.equals(message.getStringAttribute("state",""),"0")){
                sure.setClickable(true);
                refuse.setClickable(true);
                sure.setTextColor(Color.parseColor("#ffffff"));
                refuse.setTextColor(Color.parseColor("#ffffff"));
            }else {
                sure.setClickable(false);
                refuse.setClickable(false);
                sure.setTextColor(Color.parseColor("#808080"));
                refuse.setTextColor(Color.parseColor("#808080"));
            }
        }else {
            line.setVisibility(GONE);
            goodOp.setVisibility(GONE);
        }

        good.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = -1;
                try {
                    id = Integer.parseInt(message.getStringAttribute("goods_id"));
                } catch (Exception e) {
                    return;
                }
                if (TextUtils.equals(message.getStringAttribute("custometype", ""), "1"))
                    EventBus.getDefault().post(new GoodChatRowEventBusEntity(GoodChatRowEventBusEntity.OPERATION_NEED, id));
                else
                    EventBus.getDefault().post(new GoodChatRowEventBusEntity(GoodChatRowEventBusEntity.OPERATION_SKILL, id));
            }
        });

        sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               if (TextUtils.equals(message.getStringAttribute("state",""),"0")){
                   int id = -1;
                   try {
                       id = Integer.parseInt(message.getStringAttribute("goods_id"));
                   } catch (Exception e) {
                       return;
                   }
                   EventBus.getDefault().post(new GoodChatRowEventBusEntity(GoodChatRowEventBusEntity.OPERATION_SURE, id));
                   message.setAttribute("state","1");
                   EMChatManager.getInstance().updateMessageBody(message);

               }
            }
        });

        refuse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(message.getStringAttribute("state", ""), "0")) {
                    message.setAttribute("state", "2");
                    EMChatManager.getInstance().updateMessageBody(message);
                    EventBus.getDefault().post(new GoodChatRowEventBusEntity(GoodChatRowEventBusEntity.OPERATION_REFUSE, 0));
                }
                }
            }

            );

            handleTextMessage();
        }

    }
