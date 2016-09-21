package com.ysp.houge.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;
/**
 * @描述: 推荐列表的适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author hx
 * @date 2015年10月11日下午5:45:37
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class SkillDetailstAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
	private List<GoodsDetailEntity> entities = new ArrayList<GoodsDetailEntity>();
	private LayoutInflater mInflater;
	private OnItemClickListener listener;
	private int imgSize;
	public SkillDetailstAdapter(Context context, int screenWidth) {
		super();
		mInflater = LayoutInflater.from(context);
		imgSize = (screenWidth - SizeUtils.dip2px(context, 28)) / 3;
	}
	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}
	@Override
	public int getCount() {
		return entities.size();
	}
	@Override
	public Object getItem(int position) {
		return entities.get(position);
	}
	@Override
	public long getItemId(int position) {
		return entities.get(position).id;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		GoodsDetailEntity entity = entities.get(position);
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_skill_details, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null && entity != null) {
			// 数据显示操作start
			// 加载默认用户头像
			loadImg(holder.icon, "drawable://" + R.drawable.user_small, LoadImageType.RoundAvatar);
			// 设置默认的用户名
			holder.name.setText("无名氏");
			// 加载用户头像等用户信息
			if (entity.userInfo != null) {
				// 头像部位空，加载头像
				if (!TextUtils.isEmpty(entity.userInfo.avatar)) {
					loadImg(holder.icon, entity.userInfo.avatar, LoadImageType.RoundAvatar);
				}
				// 头像背景
				switch (entity.userInfo.sex) {
					case UserInfoEntity.SEX_MAL:
						holder.iconBg.setImageResource(R.drawable.skill_user_icon_man);
						break;
					case UserInfoEntity.SEX_FEMAL:
						holder.iconBg.setImageResource(R.drawable.skill_user_icon_wuman);
						break;
					default:
						holder.iconBg.setImageResource(R.drawable.skill_user_icon_nosex);
						break;
				}
				// 用户名
				if (!TextUtils.isEmpty(entity.userInfo.nick)) {
					holder.name.setText(entity.userInfo.nick);
				}
				// 星级
				if (!TextUtils.isEmpty(String.valueOf(entity.userInfo.star))) {
					holder.rate.setText(String.valueOf(entity.userInfo.star) + " ★");
				}
				// 服务保障
				if (TextUtils.isEmpty(entity.userInfo.serviceSafeguardg)) {
					holder.serviceSafeguard.setVisibility(View.GONE);
				} else {
					double serviceSafeguardg = -1;
					try{
						serviceSafeguardg = Double.parseDouble(entity.userInfo.serviceSafeguardg);
					}catch (Exception e){
					}
					if (serviceSafeguardg > 0) {
						holder.serviceSafeguard.setVisibility(View.VISIBLE);
					} else {
						holder.serviceSafeguard.setVisibility(View.GONE);
					}
				}
				// 认证
				if (TextUtils.isEmpty(entity.userInfo.verfied)) {
					holder.auth.setVisibility(View.GONE);
				} else {
					holder.auth.setVisibility(View.VISIBLE);
					if (TextUtils.equals("1", entity.userInfo.verfied)) {
						holder.auth.setText("学生认证");
					} else if (TextUtils.equals("2", entity.userInfo.verfied)) {
						holder.auth.setText("个人认证");
					} else  if (TextUtils.equals("3", entity.userInfo.verfied)){
						holder.auth.setText("企业认证");
					}else {
						holder.auth.setVisibility(View.GONE);
					}
				}
			}
			// 距离
			holder.distance.setText(entity.distance);
			holder.street.setText(entity.address_detail);
			// 技能标题
			holder.skillName.setText("标题");
			if (!TextUtils.isEmpty(entity.title)) {
				holder.skillName.setText("我能·" + entity.title);
			}
			// 等级
			if (TextUtils.isEmpty(entity.level)) {
				holder.skillLevel.setVisibility(View.GONE);
			} else {
				holder.skillLevel.setVisibility(View.VISIBLE);
				if (TextUtils.equals("1", entity.level)) {
					holder.skillLevel.setText("普通");
				} else if (TextUtils.equals("2", entity.level)) {
					holder.skillLevel.setText("初级");
				} else if (TextUtils.equals("3", entity.level)) {
					holder.skillLevel.setText("中级");
				} else if (TextUtils.equals("4", entity.level)) {
					holder.skillLevel.setText("高级");
				} else if (TextUtils.equals("5", entity.level)) {
					holder.skillLevel.setText("资深");
				} else if (TextUtils.equals("6", entity.level)) {
					holder.skillLevel.setText("专家");

				} else {
					holder.skillLevel.setVisibility(View.GONE);
				}
			}
			// 加载技能图片
			if (entity.image != null && entity.image.size() > 0) {
				holder.skillImgs.setVisibility(View.VISIBLE);
				for (int i = 0; i < 3; i++) {
					if (i < entity.image.size() && !TextUtils.isEmpty(entity.image.get(i))) {
						holder.imgs[i].setVisibility(View.VISIBLE);
						holder.imgs[i].setTag(entity.image.get(i));
						loadImg(holder.imgs[i], entity.image.get(i), LoadImageType.NormalImage);
					} else {
						holder.imgs[i].setVisibility(View.INVISIBLE);
					}
				}
			} else {
				holder.skillImgs.setVisibility(View.GONE);
			}
			// 技能描述
			holder.experienceDescribe.setText("描述");
			if (!TextUtils.isEmpty(entity.desc)) {
				holder.experienceDescribe.setText(entity.desc);
			}
			// 拼接销售价格
			StringBuilder sb = new StringBuilder();
			sb.append("￥");
			sb.append(entity.price);
			sb.append("/");
			sb.append(entity.unit);
//			// 显示售价以及单位
//			if (TextUtils.isEmpty(entity.price)){
//				holder.priceAndWorkTime.setText("免薪实习");
//			}else {
//				holder.priceAndWorkTime.setText("无售价");
//			}
			//如果拼接的价格单位正确，显示
				holder.priceAndWorkTime.setText(sb.toString());
			// 喜欢(暂时用浏览数量代替)
			holder.praise.setText("0");
			if (!TextUtils.isEmpty(String.valueOf(entity.view_count))) {
				holder.praise.setText(String.valueOf(entity.view_count));
			}
			// 留言次数
			holder.levelMsg.setText("0");
			if (!TextUtils.isEmpty(String.valueOf(entity.comment_count))) {
				holder.levelMsg.setText(String.valueOf(entity.comment_count));
			}
			// 数据显示操作end
			// 点击操作 start
			// 技能详情
			final int index = position;
			holder.skill.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL);
					}
				}
			});
			// 用户详情
			holder.info.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_USER_DETAIL);
					}
				}
			});
			// 聊一聊
			holder.haveATalk.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK);
					}
				}
			});
			// 分享
			holder.share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_SHARE);
					}
				}
			});
			// 点击操作 end
		}
		return convertView;
	}
	@Override
	public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
		if (isRefresh) {
			entities.clear();
		}
		entities.addAll(data);
	}
	@Override
	public List<GoodsDetailEntity> getData() {
		return entities;
	}
	/** 加载图片 */
	private void loadImg(ImageView view, String avatarUrl, LoadImageType type) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, avatarUrl, type);
	}
	class Holder {
		LinearLayout skill; // 技能外层布局
		ImageView icon; // 头像
		ImageView iconBg; // 头像背景
		TextView name; // 昵称
		TextView rate; // 等级
		TextView serviceSafeguard; // 服务保障
		TextView auth; // 认证
		TextView distance; // 距离
		TextView street;  //街道
		LinearLayout info; // 个人信息外层布局
		TextView skillName;// 技能名称
		TextView skillLevel;// 技能等级
		// 技能图片外层布局
		LinearLayout skillImgs;
		// 图片集合控件
		ImageView ivOne;
		ImageView ivTwo;
		ImageView ivThree;
		ImageView[] imgs;
		TextView experienceDescribe;// 经历描述
		TextView priceAndWorkTime;// 价格以及工作时常
		TextView haveATalk;// 聊一聊
		ImageView share; // 分享
		TextView praise; // 赞的数量
		TextView levelMsg; // 留言数量

		public Holder(View convertView) {
			skill = (LinearLayout) convertView.findViewById(R.id.line_skill_detail);
			icon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
			iconBg = (ImageView) convertView.findViewById(R.id.iv_user_icon_bg);
			name = (TextView) convertView.findViewById(R.id.tv_user_name);
			rate = (TextView) convertView.findViewById(R.id.tv_rate);
			serviceSafeguard = (TextView) convertView.findViewById(R.id.tv_service_safeguard);
			auth = (TextView) convertView.findViewById(R.id.tv_auth);
			distance = (TextView) convertView.findViewById(R.id.tv_distance);
			street = (TextView) convertView.findViewById(R.id.tv_street);
			info = (LinearLayout) convertView.findViewById(R.id.line_user_info_layout);
			skillName = (TextView) convertView.findViewById(R.id.tv_skill_name);
			skillLevel = (TextView) convertView.findViewById(R.id.tv_skill_level);
			skillImgs = (LinearLayout) convertView.findViewById(R.id.line_skill_imgs);
			// 图片集合控件
			ivOne = (ImageView) convertView.findViewById(R.id.iv_skill_img_one);
			ivTwo = (ImageView) convertView.findViewById(R.id.iv_skill_img_two);
			ivThree = (ImageView) convertView.findViewById(R.id.iv_skill_img_three);
			ivOne.getLayoutParams().width = imgSize;
			ivOne.getLayoutParams().height = imgSize;
			ivTwo.getLayoutParams().width = imgSize;
			ivTwo.getLayoutParams().height = imgSize;
			ivThree.getLayoutParams().width = imgSize;
			ivThree.getLayoutParams().height = imgSize;
			imgs = new ImageView[] { ivOne, ivTwo, ivThree };
			experienceDescribe = (TextView) convertView.findViewById(R.id.tv_experience_describe);
			priceAndWorkTime = (TextView) convertView.findViewById(R.id.tv_price_and_work_time);
			haveATalk = (TextView) convertView.findViewById(R.id.tv_have_a_talk);
			share = (ImageView) convertView.findViewById(R.id.iv_share);
			praise = (TextView) convertView.findViewById(R.id.tv_praise);
			levelMsg = (TextView) convertView.findViewById(R.id.tv_level_msg);
		}
	};
}