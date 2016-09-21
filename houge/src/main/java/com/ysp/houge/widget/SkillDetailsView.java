package com.ysp.houge.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

public class SkillDetailsView extends LinearLayout implements OnClickListener, TextWatcher, OnPageChangeListener {
	private SkillViewListener listener;
	private Context context;
	private  LinearLayout skillDetails;
	private LinearLayout userInfo;
	// 用户信息模块
	private ImageView avatar;
	private ImageView avatarBg;
	private TextView name;
	private TextView rate;
	private TextView serviceSafeguard;
	private TextView auth;
	private TextView distance;
	private TextView mSkillName;
	private TextView mSkillPriceAndUnit;
	private TextView mOrderCount;
	private List<String> list;
	private ImageView mBuyCountReduce;
	private EditText mBuyCount;
	private ImageView mBuyCountAdd;
	private TextView mSkillDesription;
	private int buyCont = 1;
	private int user_id = -1;
	private ImageView one, two, three, four, five, six, seven, eight, nine;
	private boolean hasRecord = false;
	private TextView skill_level;
	private TextView street;

	public SkillDetailsView(Context context) {
		super(context);
		init(context);
	}
	public SkillDetailsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public SkillDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs);
		init(context);
	}
	public void setListener(SkillViewListener listener) {
		this.listener = listener;
	}
	private void init(Context context) {
		this.context = context;
		View.inflate(context, R.layout.view_skill_details, this);
		initView();
	}
	private void initView() {
		skillDetails = (LinearLayout) findViewById(R.id.line_skill_detail);
		userInfo = (LinearLayout) findViewById(R.id.line_user_info_layout);
		userInfo.setOnClickListener(this);
		avatar = (ImageView) findViewById(R.id.iv_user_icon);
		avatarBg = (ImageView) findViewById(R.id.iv_user_icon_bg);
		name = (TextView) findViewById(R.id.tv_user_name);
		rate = (TextView) findViewById(R.id.tv_rate);
		serviceSafeguard = (TextView) findViewById(R.id.tv_service_safeguard);
		auth = (TextView) findViewById(R.id.tv_auth);
		distance = (TextView) findViewById(R.id.tv_distance);
		skill_level = (TextView) findViewById(R.id.tv_skill_level);
		street = (TextView) findViewById(R.id.tv_street);


		mSkillName = (TextView) findViewById(R.id.tv_skill_name);
		mSkillPriceAndUnit = (TextView) findViewById(R.id.tv_skill_price_and_unit);
		mOrderCount = (TextView) findViewById(R.id.tv_skill_order_count);
		mBuyCountReduce = (ImageView) findViewById(R.id.iv_skill_buy_count_reduce);
		mBuyCount = (EditText) findViewById(R.id.et_buy_count);
		mBuyCountAdd = (ImageView) findViewById(R.id.iv_skill_buy_count_add);
		mBuyCountReduce.setOnClickListener(this);
		mBuyCountAdd.setOnClickListener(this);
		mBuyCount.addTextChangedListener(this);
		mSkillDesription = (TextView) findViewById(R.id.tv_skill_description);

		//技能图片
		one = (ImageView)findViewById(R.id.iv_need_detials_images_one);
		two = (ImageView) findViewById(R.id.iv_need_detials_images_two);
		three = (ImageView)findViewById(R.id.iv_need_detials_images_three);
		four = (ImageView)findViewById(R.id.iv_need_detials_images_four);
		five = (ImageView) findViewById(R.id.iv_need_detials_images_five);
		six = (ImageView)findViewById(R.id.iv_need_detials_images_six);
		seven = (ImageView)findViewById(R.id.iv_need_detials_images_seven);
		eight = (ImageView) findViewById(R.id.iv_need_detials_images_eight);
		nine = (ImageView)findViewById(R.id.iv_need_detials_images_nine);

	}
	public void setUserInfo(UserInfoEntity info) {
		if (info != null) {
			userInfo.setVisibility(View.VISIBLE);
			user_id = info.id;
			// 头像不为空，加载头像
			if (!TextUtils.isEmpty(info.avatar)) {
				MyApplication.getInstance().getImageLoaderManager().loadNormalImage(avatar, info.avatar,
						LoadImageType.RoundAvatar);
			}else {
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
			}else {
				name.setText("无名氏");
			}
			// 星级
			rate.setText(String.valueOf(info.star) + " ★");
			// 服务保障
			if (TextUtils.isEmpty(info.serviceSafeguardg)) {
				serviceSafeguard.setVisibility(View.GONE);
			} else {
				double moeny = 0;
				try {
					moeny = Double.parseDouble(info.serviceSafeguardg);
				}catch (Exception e){}
				if (moeny > 0) {
					serviceSafeguard.setVisibility(View.VISIBLE);
				} else {
					serviceSafeguard.setVisibility(View.GONE);
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
				} else if(TextUtils.equals("3", info.verfied)){
					auth.setText("企业认证");
				}else {
					auth.setVisibility(View.GONE);
				}
			}
		}else {
			//隐藏用户栏
			userInfo.setVisibility(View.GONE);
		}
	}
	/**设置技能详情*/
	public void setSkillDetails(GoodsDetailEntity entity, Activity activity) {
		if (entity != null) {
			// 这里ViewPager里面的图片单独处理
			if (entity.image != null && !entity.image.isEmpty()) {
				list = entity.image;
				List<ImageView> pages = new ArrayList<ImageView>();
				for (int i = 0; i < entity.image.size(); i++) {
					// 大于4是为了判断处理后缀
					if (!TextUtils.isEmpty(entity.image.get(i)) && entity.image.get(i).length() > 4) {
						// 这里值判断是不是录音，因为录音格式统一为amr(不考虑异常情况吗打不了加载不了)
						if (!(entity.image.get(i).indexOf(".amr") > 0)) {
							ImageView view = new ImageView(context);
							//由于是动态添加的所以技能设置tag去判断点击事件
							view.setTag(entity.image.get(i));
							view.setOnClickListener(this);
//							view.setScaleType(ScaleType.CENTER_CROP);
							MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view,
									entity.image.get(i), LoadImageType.NormalImage);
							pages.add(view);
						} else {
							//这里移除是为了防止将图片传入大图查看页面
							list.remove(i);
						}
					}
				}

				// 等级
				if (TextUtils.isEmpty(entity.level)) {
					skill_level.setVisibility(View.GONE);
				} else {
					skill_level.setVisibility(View.VISIBLE);
					if (TextUtils.equals("1", entity.level)) {
						skill_level.setText("普通");
					} else if (TextUtils.equals("2", entity.level)) {
						skill_level.setText("初级");
					} else if (TextUtils.equals("3", entity.level)) {
						skill_level.setText("中级");
					} else if (TextUtils.equals("4", entity.level)) {
						skill_level.setText("高级");
					} else if (TextUtils.equals("5", entity.level)) {
						skill_level.setText("资深");
					} else if (TextUtils.equals("6", entity.level)) {
						skill_level.setText("专家");

					} else {
						skill_level.setVisibility(View.GONE);
					}
				}
				//街道
				// 详情
				if(!TextUtils.isEmpty(entity.address_detail)){
					street.setText(entity.address_detail);
				}

				//距离
				if (!TextUtils.isEmpty(entity.distance)) {
					distance.setVisibility(VISIBLE);
					distance.setText(entity.distance);
				} else {
					distance.setVisibility(GONE);
				}
				// 技能名称
				if (!TextUtils.isEmpty(entity.title)) {
					mSkillName.setText("我能·" + entity.title);
				} else {
					mSkillName.setText("我能·未知技能");
				}
				// 拼接销售价格
				if (!TextUtils.isEmpty(entity.price)) {
					StringBuilder sb = new StringBuilder();
					sb.append("￥");
					sb.append(entity.price);
					if (!TextUtils.isEmpty(entity.unit)) {
						sb.append(entity.unit);
					}
					if (sb.length() > 0) {
						mSkillPriceAndUnit.setText(sb.toString());
					}
				}
				//购买次数
				mBuyCount.setText("1");
				// 已购买次数
				if (!TextUtils.isEmpty(String.valueOf(entity.order_count))) {
					StringBuilder stringBuilder = new StringBuilder("已预约");
					stringBuilder.append(entity.order_count);
					stringBuilder.append("次");
					mOrderCount.setText(stringBuilder);
					mOrderCount.setVisibility(VISIBLE);
				} else {
					mOrderCount.setVisibility(GONE);
				}
				// 技能详情
				if (!TextUtils.isEmpty(entity.desc)) {
					mSkillDesription.setText(entity.desc);
				} else {
					mSkillDesription.setText("暂无技能介绍");
				}


//				DisplayMetrics localDisplayMetrics = new DisplayMetrics();
//				Display localDisplay = activity.getWindowManager().getDefaultDisplay();
//				localDisplay.getMetrics(localDisplayMetrics);
//				int screenWidth = localDisplayMetrics.widthPixels - SizeUtils.dip2px(context, 24);
//				int screenHeight = localDisplayMetrics.heightPixels - SizeUtils.dip2px(context, 24);
//
//				one.getLayoutParams().width = screenWidth;
//				one.getLayoutParams().height = screenHeight;
//				two.getLayoutParams().width = screenWidth;
//				two.getLayoutParams().height = screenHeight;
//				three.getLayoutParams().width = screenWidth;
//				three.getLayoutParams().height = screenHeight;
//				four.getLayoutParams().width = screenWidth;
//				four.getLayoutParams().height = screenHeight;
//				five.getLayoutParams().width = screenWidth;
//				five.getLayoutParams().height = screenHeight;
//				six.getLayoutParams().width = screenWidth;
//				six.getLayoutParams().height = screenHeight;
//				seven.getLayoutParams().width = screenWidth;
//				seven.getLayoutParams().height = screenHeight;
//				eight.getLayoutParams().width = screenWidth;
//				eight.getLayoutParams().height = screenHeight;
//				nine.getLayoutParams().width = screenWidth;
//				nine.getLayoutParams().height = screenHeight;

				for (int i = 0; i < entity.image.size(); i++) {
					if (i == 0) {
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
					if (i == 3) {
						four.setVisibility(VISIBLE);
						loadImg(four, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
					}
					if (i == 4) {
						five.setVisibility(VISIBLE);
						loadImg(five, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
					}
					if (i == 5) {
						six.setVisibility(VISIBLE);
						loadImg(six, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
					}
					if (i == 6) {
						seven.setVisibility(VISIBLE);
						loadImg(seven, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
					}
					if (i == 7) {
						eight.setVisibility(VISIBLE);
						loadImg(eight, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
					}
					if (i == 8) {
						nine.setVisibility(VISIBLE);
						loadImg(nine, entity.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
					}
				}

			}
			//设置完详情之后显示详情的夫布局
			skillDetails.setVisibility(VISIBLE);
		}
	}

	private void loadImg(ImageView view, String avatarUrl, LoadImageType type) {
//		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, avatarUrl, type);
		MyApplication.getInstance().getImageLoaderManager().load(view, avatarUrl);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 购买次数增加
			case R.id.iv_skill_buy_count_add:
				if (!TextUtils.isEmpty(mBuyCount.getText())) {
					buyCont = Integer.parseInt(mBuyCount.getText().toString().trim());
				}
				// 这里看需要限制最大购买次数等(暂定99)
				if (buyCont < 99) {
					buyCont++;
					mBuyCount.setText(String.valueOf(buyCont));
					if (null != listener) {
						listener.onBuyCountChange(buyCont);
					}
				}
				break;
			// 购买次数减少
			case R.id.iv_skill_buy_count_reduce:
				if (!TextUtils.isEmpty(mBuyCount.getText())) {
					buyCont = Integer.parseInt(mBuyCount.getText().toString().trim());
				}
				// 最少买一件
				if (buyCont > 1) {
					buyCont--;
					mBuyCount.setText(String.valueOf(buyCont));
					if (null != listener) {
						listener.onBuyCountChange(buyCont);
					}
				}
				break;
			// 分享
			case R.id.iv_share:
				// 这里暂不做处理 点一次分享一次(判断快速双击)
				if (!DoubleClickUtil.isFastClick() && null != listener) {
					listener.onShare();
				}
				break;
			// 用户信息
			case R.id.line_user_info_layout:
				if (user_id > 0) {
					Bundle bundle = new Bundle();
					bundle.putInt(UserDetailsActivity.KEY, user_id);
					UserDetailsActivity.jumpIn(context, bundle);
				}
				break;
			default:
//				// 图片(分析tag，如果是图片,则进入大图查看页面)
//				if (null != v.getTag() && v.getTag() instanceof String) {
//					Bundle bundle = new Bundle();
//					bundle.putString(PhotoViewActivity.KEY, new Gson().toJson(list));
//					bundle.putInt(PhotoViewActivity.PAGE_INDEX,pager.getCurrentItem());
//					PhotoViewActivity.jumpIn(context, bundle);
//				}
				break;
		}
	}
	public int getmBuyCount() {
		return Integer.parseInt(mBuyCount.getText().toString());
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO 输入购买次数的监听
		// 为空时设置成 1
		if (TextUtils.isEmpty(s)) {
			buyCont = 1;
			mBuyCount.setText("1");
		}
		try {
			buyCont = Integer.parseInt(s.toString());
		} catch (Exception e) {
			// TODO:捕获到异常
			buyCont = 1;
			mBuyCount.setText("1");
		}
		listener.onBuyCountChange(buyCont);
	}
	@Override
	public void onPageSelected(int arg0) {
//		emoDotView.setPos(list.size(), arg0);
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}
	@Override
	public void afterTextChanged(Editable s) {
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	public interface SkillViewListener {
		void clickUser();
		/** 价格更改 */
		void onBuyCountChange(int buyCount);
		/** 分享 */
		void onShare();
	}
}