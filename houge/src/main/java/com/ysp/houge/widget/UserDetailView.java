package com.ysp.houge.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author it_hu 选择时间的扇形视图
 *
 */
public class UserDetailView extends LinearLayout {
    public final static int TYPE_SKILL = 0;
    public final  static int TYPE_NEED = 1;

    public ImageView avatarBg;// 个人信息主页
    private ImageView avatar;// 头像
    private TextView nick;// 昵称
    private TextView safeguard;// 服务保障
    private TextView auth;// 认证
    private TextView rate;// 评♥等级
    private TextView skill, need;

    public List<String> avatarList;
    private List<String> avatarBgList;

    public UserDetailView(Context context) {
        super(context);
        initView(context);
    }

    public UserDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public UserDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View.inflate(context, R.layout.view_user_detail, this);
        avatarBg = (ImageView)findViewById(R.id.iv_user_info_head_bg);
        avatar = (ImageView)findViewById(R.id.iv_user_avatar);
        nick = (TextView)findViewById(R.id.tv_nick);
        safeguard = (TextView) findViewById(R.id.tv_service_safeguard);
        auth = (TextView) findViewById(R.id.tv_auth);
        rate = (TextView) findViewById(R.id.tv_user_rate);

        skill = (TextView) findViewById(R.id.tv_skill);
        need = (TextView) findViewById(R.id.tv_need);

        avatarBg.getLayoutParams().height = SizeUtils.getScreenWidth((Activity)context);
    }

    public void setClickLisenter(OnClickListener lisenter){
        avatar.setOnClickListener(lisenter);
        skill.setOnClickListener(lisenter);
        need.setOnClickListener(lisenter);
    }

    public void removeClickLisenter(){
        avatar.setOnClickListener(null);
        skill.setOnClickListener(null);
        need.setOnClickListener(null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setUserInfo(UserInfoEntity infoEntity){
        // 个人中心背景图
        String json = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
        if (TextUtils.isEmpty(json)) {
            avatarBg.setBackgroundResource(R.drawable.splash);
        } else {
            List<String> list = new Gson().fromJson(json, AppInitEntity.class).user_bg_image;
            if (null == list || list.isEmpty()) {
                avatarBg.setBackgroundResource(R.drawable.splash);
            } else {
                avatarBgList = new ArrayList<>();
                avatarBgList.add(list.get(new Random().nextInt(list.size())));
                MyApplication.getInstance().getImageLoaderManager().loadNormalImage(avatarBg,
                        avatarBgList.get(0), ImageLoaderManager.LoadImageType.NormalImage);
            }
        }

        // 性别(头像背景框)
        switch (infoEntity.sex) {
            case UserInfoEntity.SEX_FEMAL:
                avatar.setBackground(getResources().getDrawable(R.drawable.shapa_sex_femal));
                break;
            case UserInfoEntity.SEX_MAL:
                avatar.setBackground(getResources().getDrawable(R.drawable.shapa_sex_mal));
                break;
            default:
                avatar.setBackground(getResources().getDrawable(R.drawable.shapa_sex_def));
                break;
        }

        // 头像
        MyApplication.getInstance().getImageLoaderManager().loadNormalImage(avatar, infoEntity.avatar,
                ImageLoaderManager.LoadImageType.RoundAvatar);

        //用户头像的大图查看
        avatarList = new ArrayList<String>();
        avatarList.add(infoEntity.avatar);

        // 昵称
        if (TextUtils.isEmpty(infoEntity.nick)) {
            nick.setText("无名氏");
        } else {
            nick.setText(infoEntity.nick);
        }

        // 服务保障
        if (TextUtils.isEmpty(infoEntity.serviceSafeguardg)) {
            safeguard.setVisibility(View.GONE);
        } else {
            double money = -1;
            try{money = Double.parseDouble(infoEntity.serviceSafeguardg);}catch (Exception e){}
            if (money <= 0) {
                safeguard.setVisibility(View.GONE);
            } else {
                safeguard.setVisibility(View.VISIBLE);
            }
        }

        // 认证
        if (TextUtils.isEmpty(infoEntity.verfied)) {
            auth.setVisibility(View.GONE);
        } else {
            auth.setVisibility(View.VISIBLE);
            if (TextUtils.equals("1", infoEntity.verfied)) {
                auth.setText("学生认证");
            } else if (TextUtils.equals("2", infoEntity.verfied)) {
                auth.setText("个人认证");
            } else if (TextUtils.equals("3", infoEntity.verfied)){
                auth.setText("企业认证");
            }else {
                auth.setVisibility(View.GONE);
            }
        }

        // 评星
        rate.setText(String.valueOf(infoEntity.star));
    }

    public void setType(int type){
//        switch (type){
//            case TYPE_SKILL:
//                skill.setTextColor(getResources().getColor(R.color.red));
//                need.setTextColor(getResources().getColor(R.color.color_999999));
//                break;
//            case TYPE_NEED:
//                skill.setTextColor(getResources().getColor(R.color.color_999999));
//                need.setTextColor(getResources().getColor(R.color.red));
//                break;
//        }
    }
}
