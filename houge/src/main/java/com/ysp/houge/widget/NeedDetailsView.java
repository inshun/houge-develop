package com.ysp.houge.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.utility.voice.media.MyMediaPlayer.onMediaPlayListener;

import java.util.ArrayList;
import java.util.List;

public class NeedDetailsView extends LinearLayout
        implements OnClickListener, OnPageChangeListener, onMediaPlayListener {
    private Context context;
    private boolean hasRecord = false;
    // 用户信息模块
    private LinearLayout userInfo;
    private ImageView avatar;
    private ImageView avatarBg;
    private TextView name;
    private TextView rate;
    private TextView safeguard;
    private TextView auth;
    private TextView distance;
    private List<String> list;
    // 详情模块
    private TextView title;
    private TextView price;
    private TextView desc;
    private TextView address;
    private TextView time;
    private AnimationDrawable frameAnim;
    private NeedViewListener listener;
    private ImageView one, two, three;
    private int imgSize;

    public NeedDetailsView(Context context) {
        super(context);
        init(context);
    }

    public NeedDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NeedDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        init(context);
    }

    public void setListener(NeedViewListener listener) {
        this.listener = listener;
    }

    private void init(Context context) {
        this.context = context;
        View.inflate(context, R.layout.view_need_details, this);
        initView();
    }

    private void initView() {
        userInfo = (LinearLayout) findViewById(R.id.line_user_info_layout);
        userInfo.setOnClickListener(this);
        avatar = (ImageView) findViewById(R.id.iv_user_icon);
        avatarBg = (ImageView) findViewById(R.id.iv_user_icon_bg);
        name = (TextView) findViewById(R.id.tv_user_name);
        rate = (TextView) findViewById(R.id.tv_rate);
        safeguard = (TextView) findViewById(R.id.tv_service_safeguard);
        auth = (TextView) findViewById(R.id.tv_auth);
        distance = (TextView) findViewById(R.id.tv_distance);
        title = (TextView) findViewById(R.id.tv_skill_name);
        price = (TextView) findViewById(R.id.tv_price);
        desc = (TextView) findViewById(R.id.tv_skill_description);
        address = (TextView) findViewById(R.id.tv_address);
        time = (TextView) findViewById(R.id.tv_time);

        one = (ImageView) findViewById(R.id.iv_need_detials_images_one);
        two = (ImageView) findViewById(R.id.iv_need_detials_images_two);
        three = (ImageView) findViewById(R.id.iv_need_detials_images_three);

        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.play_record_need_details);
    }

    public void setUserInfo(UserInfoEntity info) {
        if (info != null) {
            userInfo.setVisibility(VISIBLE);
            // 头像不为空，加载头像
            if (!TextUtils.isEmpty(info.avatar)) {
                MyApplication.getInstance().getImageLoaderManager().loadNormalImage(avatar, info.avatar,
                        LoadImageType.RoundAvatar);
            } else {
                avatar.setImageResource(R.drawable.user_small);
            }
            // 头像背景
            switch (info.sex) {
                case UserInfoEntity.SEX_MAL:
                    avatarBg.setImageResource(R.drawable.skill_user_icon_man);
                    break;
                case UserInfoEntity.SEX_FEMAL:
                    avatarBg.setImageResource(R.drawable.skill_user_icon_wuman);
                    break;
                default:
                    avatarBg.setImageResource(R.drawable.skill_user_icon_nosex);
                    break;
            }
            // 用户名
            if (!TextUtils.isEmpty(info.nick)) {
                name.setText(info.nick);
            } else {
                name.setText("无名氏");
            }
            // 星级
            rate.setText(String.valueOf(info.star) + " ★");
            // 服务保障
            if (TextUtils.isEmpty(info.serviceSafeguardg)) {
                safeguard.setVisibility(View.GONE);
            } else {
                double money = -1;
                try {
                    money = Double.parseDouble(info.serviceSafeguardg);
                } catch (Exception e) {
                }
                if (money <= 0) {
                    safeguard.setVisibility(View.GONE);
                } else {
                    safeguard.setVisibility(View.VISIBLE);
                }
            }
            // 认证
            if (TextUtils.isEmpty(info.verfied)) {
                auth.setVisibility(View.GONE);
            } else {
                auth.setVisibility(View.VISIBLE);
                if (TextUtils.equals("1", info.verfied)) {
                    auth.setText("学生认证");
                } else if (TextUtils.equals("2", info.verfied)) {
                    auth.setText("个人认证");
                } else if (TextUtils.equals("3", info.verfied)) {
                    auth.setText("企业认证");
                } else {
                    auth.setVisibility(View.GONE);
                }
            }
        } else {
            userInfo.setVisibility(GONE);
        }
    }


    /**
     * 设置需求详情
     */
    public void setNeedDetails(final GoodsDetailEntity entity, Activity activity) {
        if (entity != null) {
            // 这里ViewPager里面的图片单独处理
            if (entity.image != null && entity.image.size() > 0) {
                list = entity.image;
                List<ImageView> pages = new ArrayList<ImageView>();
                for (int i = 0; i < entity.image.size(); i++) {
                    // 大于4是为了判断处理后缀
                    if (!TextUtils.isEmpty(entity.image.get(i)) && entity.image.get(i).length() > 4) {
                        // 这里值判断是不是录音，应为录音格式统一为amr
                        if (!(entity.image.get(i).indexOf(".amr") > 0)) {
                            ImageView view = new ImageView(context);
                            view.setTag(entity.image.get(i));
                            view.setOnClickListener(this);
                            view.setScaleType(ScaleType.CENTER_CROP);
                            MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view,
                                    entity.image.get(i), LoadImageType.NormalImage);
                            pages.add(view);
                        } else if (entity.image.get(i).indexOf(".amr") > 0) {
                            hasRecord = true;
                            list.remove(i);
                        }
                    }
                }
                if (pages.size() > 0) {

                }
            }

            if (!TextUtils.isEmpty(entity.title)) {
                StringBuilder sb = new StringBuilder();
                sb.append("#");
                sb.append("需要·");
                sb.append(entity.title);
                sb.append("#");
                title.setText(Html.fromHtml(sb.toString()));
                // 需求名称
            } else {
                title.setText("需要·未知需求");
            }
            // 价格
            if (TextUtils.isEmpty(entity.price))
                price.setText("未知价格");
            else
                price.setText("￥" + entity.price);
            // 描述
            if (!TextUtils.isEmpty(entity.desc)) {
                desc.setText(entity.desc);
            } else {
                desc.setText("暂无描述");
            }
            //地址
            if (null == entity.address || TextUtils.isEmpty(entity.address.street)) {
                address.setText("未知的地址");
            } else {
                address.setText(entity.address.street);
            }
            //时间
            if (TextUtils.isEmpty(entity.start_time)) {
                time.setText("未知的时间");
            } else {
                time.setText(entity.start_time);
            }
            //距离
            distance.setText(entity.distance);


            //此处获取的是屏幕的宽高
//			DisplayMetrics localDisplayMetrics = new DisplayMetrics();
//			Display localDisplay = activity.getWindowManager().getDefaultDisplay();
//			localDisplay.getMetrics(localDisplayMetrics);
//
//			int screenWidth = localDisplayMetrics.widthPixels - SizeUtils.dip2px(context, 24);
//			int screenHeight = localDisplayMetrics.heightPixels - SizeUtils.dip2px(context, 24);
//
//			one.getLayoutParams().width = screenWidth;
//			one.getLayoutParams().height = screenHeight;
//			two.getLayoutParams().width = screenWidth;
//			two.getLayoutParams().height = screenHeight;
//			three.getLayoutParams().width = screenWidth;
//			three.getLayoutParams().height = screenHeight;

            for (int i = 0; i < entity.image.size(); i++) {
                if (i == 0) {
                    one.setVisibility(VISIBLE);
                    loadImg(one, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
                }
                if (i == 1) {
                    two.setVisibility(VISIBLE);
                    loadImg(two, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
                }
                if (i == 2) {
                    three.setVisibility(VISIBLE);
                    loadImg(three, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 分享
            case R.id.iv_share:
                if (null != listener) {
                    listener.onShare();
                }
                break;
            case R.id.line_user_info_layout:
                if (null != listener) {
                    listener.clickUser();
                }
                break;
            default:
        }
    }

    private void loadImg(ImageView view, String avatarUrl, LoadImageType type) {
//		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, avatarUrl, type);
        MyApplication.getInstance().getImageLoaderManager().load(view, avatarUrl);
    }

    @Override
    public void onFinishPlay() {
        if (null != frameAnim) {
            frameAnim.stop();
        }
    }

    @Override
    public void onPageSelected(int arg0) {
//		emoDotView.setPos(list.size(), arg0);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public interface NeedViewListener {
        void clickUser();

        void onShare();

        void onClickRecord(boolean isRecord);
    }
}