package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.MySellEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 卖出订单适配器
 *
 * @ClassName: SellOrderAdapter
 *
 * @author: hx
 *
 * @date: 2015年12月17日 下午3:58:50
 *
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class SellOrderAdapter extends BaseAdapter implements IDataAdapter<List<MySellEntity>> {
	private List<MySellEntity> list = new ArrayList<MySellEntity>();
	private OnItemClickListener listener;
	private LayoutInflater mInflater;
	private Context context;
	private Drawable drawable;
	private int color;


	public SellOrderAdapter(Context context) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
		switch (MyApplication.getInstance().getLoginStaus()){
			case MyApplication.LOG_STATUS_BUYER:
				drawable = context.getResources().getDrawable(R.drawable.icon_diangmianzhifu_orange);
				color = context.getResources().getColor(R.color.color_app_theme_orange_bg);
				break;
			case MyApplication.LOG_STATUS_SELLER:
				drawable = context.getResources().getDrawable(R.drawable.icon_dangmianfu_blue);
				color = context.getResources().getColor(R.color.color_app_theme_blue_bg);
				break;
		}
	}

	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		// 获得操作对象
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_sell_order, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null && list.size() > position) {
			final MySellEntity entity = list.get(position);
			if (null != entity) {
				// 状态公共部分 start
				// 顶部状态描述文字
				if (null!=entity.order&&!TextUtils.isEmpty(entity.order.status_text)) {
					holder.message.setVisibility(View.VISIBLE);
					holder.message.setText(entity.order.status_text);
				} else {
					holder.message.setVisibility(View.GONE);
				}

				// 状态(状态对应的操作)
				switch (entity.status) {
					case OrderEntity.STATUS_WAIT_PAY:
						holder.status.setText("待付款");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_WAIT_SERVICE:
						holder.status.setText("已付款");
						holder.function.setText("去服务");
						holder.function.setVisibility(View.VISIBLE);

						//当面付类型显示未付款
						if (entity.pay_type == OrderEntity.PAY_TYPE_FACE_TO_FACE)
							holder.status.setText("未付款");
						break;
					case OrderEntity.STATUS_DEPART:
						holder.status.setText("去服务途中");
						holder.function.setText("开始服务");
						holder.function.setVisibility(View.VISIBLE);
						break;
					case OrderEntity.STATUS_IN_SERVICE:
						holder.status.setText("服务中");
						holder.function.setText("结束服务");
						holder.function.setVisibility(View.VISIBLE);
						break;
					case OrderEntity.STATUS_SERVICE_FINISH:
						holder.status.setText("已结束");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_WAIT_COMMENT:
						if (entity.is_seller_rate == 0) {
							if (entity.is_buyer_rate == 0) {
								holder.status.setText("待评价");
								holder.function.setText("去评价");
							} else {
								holder.status.setText("对方已评价");
								holder.function.setText("去回评");
							}
							holder.function.setVisibility(View.VISIBLE);
						} else {
							// 这里如果自己已经评论还在这个状态，只有可能就是对方还未进行评论
							holder.status.setText("已评论");
							holder.function.setVisibility(View.GONE);
						}
						break;
					case OrderEntity.STATUS_STATUS_EXPIRE:
						holder.status.setText("已过期");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_FREEZE:
						holder.status.setText("已冻结");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_FINISH:
						holder.status.setText("已完成");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_CLOSE:
						holder.status.setText("订单已关闭");
						holder.function.setVisibility(View.GONE);
						break;
					default:
						break;
				}
				// 状态公共部分 end

				// 用户公共部分 start
				UserInfoEntity infoEntity = entity.buyer_user;
				if (null != infoEntity) {
					// 性别(头像背景框)
					switch (infoEntity.sex) {
						case UserInfoEntity.SEX_FEMAL:
							holder.avatar.setBackgroundResource(R.drawable.shapa_sex_femal);
							break;
						case UserInfoEntity.SEX_MAL:
							holder.avatar.setBackgroundResource(R.drawable.shapa_sex_mal);
							break;
						default:
							holder.avatar.setBackgroundResource(R.drawable.shapa_sex_def);
							break;
					}

					// 头像
					loadImage(holder.avatar, infoEntity.avatar, LoadImageType.RoundAvatar);

					// 星级
					holder.start.setText(String.valueOf(entity.buyer_user.star));
				}
				// 用户公共部分 end

				// 判断并显示或者隐藏技能或者需求
				holder.skill.setVisibility(View.VISIBLE);
				holder.need.setVisibility(View.VISIBLE);
				switch (entity.type) {
					case OrderDetailsEntity.ORDER_TYPE_SKILL:
						holder.need.setVisibility(View.GONE);
						break;
					case OrderDetailsEntity.ORDER_TYPE_TASK:
						holder.skill.setVisibility(View.GONE);
						break;
				}

				// 技能部分 start
				GoodsDetailEntity goodsDetailEntity = entity.good_info;
				if (null != goodsDetailEntity) {
					// 技能图片
					if (null != goodsDetailEntity.image && !goodsDetailEntity.image.isEmpty()) {
						loadImage(holder.skillImg,goodsDetailEntity.image.get(0), LoadImageType.NormalImage);
					}

					// 技能标题
					if (TextUtils.isEmpty(entity.name)) {
						holder.skillTtile.setText("未知技能");
					} else {
						holder.skillTtile.setText(entity.name);
					}

					// 距离
					holder.skillDistance.setText(goodsDetailEntity.distance);

					// 价格
					holder.skillPrice.setText(String.valueOf(entity.total_fee) + "元");

					//当面支付
					if (entity.pay_type == OrderEntity.PAY_TYPE_FACE_TO_FACE){
						holder.skillFaceToFace.setVisibility(View.VISIBLE);
						// 这一步必须要做,否则不会显示.
						drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
						holder.skillFaceToFace.setCompoundDrawables(drawable, null, null,null);
					}else {
						holder.skillFaceToFace.setVisibility(View.INVISIBLE);
					}
					holder.skillFaceToFace.setTextColor(color);
				}
				// 技能部分 end

				// 需求部分 start
				if (null != goodsDetailEntity) {
					// 技能标题
					if (TextUtils.isEmpty(entity.name)) {
						holder.needTitle.setText("未知需求");
					} else {
						holder.needTitle.setText(entity.name);
					}

					//价格
					holder.needPrice.setText(String.valueOf(entity.total_fee) + "元");

					//时间
					String time = "";
					try{
						long date = Long.parseLong(entity.param.start_at);
						time = DateUtil.longToString(date,"yyyy-MM-dd HH:mm:ss");
					}catch (Exception e){
						if (null != entity.param && !TextUtils.isEmpty(entity.param.start_at)){
							time = entity.param.start_at;
						}
					}
					if (!TextUtils.isEmpty(time)){
						holder.needTime.setText(time);
					}else {
						holder.needTime.setText("未知的时间");
					}

					// 距离
					holder.needDistance.setText(goodsDetailEntity.distance);

					//当面支付
					if (entity.pay_type == OrderEntity.PAY_TYPE_FACE_TO_FACE){
						holder.needFaceToFace.setVisibility(View.VISIBLE);
						// 这一步必须要做,否则不会显示.
						drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
						holder.needFaceToFace.setCompoundDrawables(drawable, null, null,null);
					}else {
						holder.needFaceToFace.setVisibility(View.INVISIBLE);
					}
					holder.needFaceToFace.setTextColor(color);

					//已支付
					//判断当面付图标是否显示
					switch (entity.is_payed){
						case 1:
							holder.needFaceToFace.setVisibility(View.GONE);
							break;
						default:
							holder.needFaceToFace.setVisibility(View.GONE);
							break;
					}
				}
				// 需求部分 end

				final int index = position;
				// 点击事件 start
				holder.function.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_ORDER_FUNCTION);
						}
					}
				});

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_ORDER_DETAIL);
						}
					}
				});

				holder.user.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_USER_DETAIL);
						}
					}
				});

				holder.skillImg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_NEED_DETAIL);
						}
					}
				});

				holder.need.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL);
						}
					}
				});


				holder.callPhone.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null != listener) {
//                            listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_CALL_TO_BUY);
							GetUri.openCallTelephone(context, entity.buyer_user.mobile);
						}
					}
				});

				holder.sendMsg.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_IM_TO_BUY);
						}
					}
				});
				// 点击事件 end
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<MySellEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
	}

	@Override
	public List<MySellEntity> getData() {
		return list;
	}

	private void loadImage(View view, String url, LoadImageType loadImageType) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, url, loadImageType);
	}

	class Holder {
		TextView message;

		TextView status;
		Button function;

		LinearLayout user;
		ImageView avatar;
		TextView nick;
		TextView start;
		ImageView callPhone;
		ImageView sendMsg;

		LinearLayout skill;
		ImageView skillImg;
		TextView skillTtile;
		TextView skillPrice;
		TextView skillDistance;
		TextView skillFaceToFace;

		LinearLayout need;
		TextView needTitle;
		TextView needDistance;
		TextView needPrice;
		TextView needFaceToFace;
		TextView needWasPay;
		TextView needTime;

		public Holder(View convertView) {
			message = (TextView) convertView.findViewById(R.id.tv_order_message);

			status = (TextView) convertView.findViewById(R.id.tv_status_txt);
			function = (Button) convertView.findViewById(R.id.btn_function);

			user = (LinearLayout) convertView.findViewById(R.id.line_user_info);
			avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			nick = (TextView) convertView.findViewById(R.id.tv_nick);
			start = (TextView) convertView.findViewById(R.id.tv_start);
			callPhone = (ImageView) convertView.findViewById(R.id.iv_call_phone);
			sendMsg = (ImageView) convertView.findViewById(R.id.iv_send_msg);

			skill = (LinearLayout) convertView.findViewById(R.id.line_skill_info);
			skillImg = (ImageView) convertView.findViewById(R.id.iv_skill_img);
			skillTtile = (TextView) convertView.findViewById(R.id.tv_skill_title);
			skillPrice = (TextView) convertView.findViewById(R.id.tv_skill_price);
			skillDistance = (TextView) convertView.findViewById(R.id.tv_skill_distance);
			skillFaceToFace = (TextView)convertView.findViewById(R.id.tv_face_to_face);

			need = (LinearLayout) convertView.findViewById(R.id.line_need_info);
			needTitle = (TextView) convertView.findViewById(R.id.tv_need_title);
			needDistance = (TextView) convertView.findViewById(R.id.tv_need_distance);
			needPrice = (TextView) convertView.findViewById(R.id.tv_need_price);
			needFaceToFace = (TextView) convertView.findViewById(R.id.tv_need_face_to_face);
			needWasPay = (TextView) convertView.findViewById(R.id.tv_need_was_pay);
			needTime = (TextView) convertView.findViewById(R.id.tv_need_time);
		}
	}
}
