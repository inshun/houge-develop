package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
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
import com.ysp.houge.model.entity.bean.MyBuyEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：我购买的 订单
 *
 * @ClassName: BuyAdapter
 *
 * @author: hx
 *
 * @date: 2015年12月13日 上午10:57:53
 *
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class BuyOrderAdapter extends BaseAdapter implements IDataAdapter<List<MyBuyEntity>>{
	private List<MyBuyEntity> list = new ArrayList<MyBuyEntity>();
	private OnItemClickListener listener;
	private LayoutInflater mInflater;
	private Context context;

	public BuyOrderAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
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
			convertView = mInflater.inflate(R.layout.item_buy_order, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null && list.size() > position) {
			final MyBuyEntity entity = list.get(position);
			if (null != entity) {				// 顶部共用模块 start
				// 顶部状态描述文字
				if (null != entity.order && !TextUtils.isEmpty(entity.order.status_text)) {
					holder.message.setVisibility(View.VISIBLE);
					holder.message.setText(entity.order.status_text);
				}else {
					holder.message.setVisibility(View.GONE);
				}

				// 状态(状态对应的操作)
				switch (entity.status) {
					case OrderEntity.STATUS_WAIT_PAY:
						holder.status.setText("待付款");
						holder.function.setText("马上支付");
						holder.function.setVisibility(View.VISIBLE);
						break;
					case OrderEntity.STATUS_WAIT_SERVICE:
						holder.status.setText("等待服务");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_DEPART:
						holder.status.setText("服务人员已出发");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_IN_SERVICE:
						holder.status.setText("服务中");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_SERVICE_FINISH:
						holder.status.setText("对方结束服务");
						holder.function.setText("确认完成");
						holder.function.setVisibility(View.VISIBLE);
						break;
					case OrderEntity.STATUS_WAIT_COMMENT:
						// ===============================
						// 评论状态涉及卖家评论、买家评论
						// 如果自己已经评论，对方未评论，显示已评论
						// 如果自己已经评论，对方已评论，订单进入下一个状态
						// 如果自未评论，对方也未评论，显示去评论
						// 如果自己未评论，对方已评论，显示去回评
						// ===============================

						// 先看自己有没有评论
						if (entity.is_buyer_rate == 0) {
							if (entity.is_seller_rate == 0) {
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
						holder.status.setText("已关闭");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_FREEZE:
						holder.status.setText("已冻结");
						holder.function.setVisibility(View.GONE);
						break;
					case OrderEntity.STATUS_FINISH:
						holder.status.setText("已完成");
						holder.function.setText("再来一单");
						holder.function.setVisibility(View.VISIBLE);
						break;
					case OrderEntity.STATUS_CLOSE:
						holder.status.setText("订单已关闭");
						holder.function.setVisibility(View.GONE);
						break;
					default:
						holder.status.setText("未处理的状态：" + entity.status);
						holder.function.setVisibility(View.GONE);
						break;
				}
				// 顶部共用模块 end

				// 底部用户共用模块 start
				UserInfoEntity infoEntity = entity.seller_user;
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
					holder.start.setText(String.valueOf(entity.seller_user.star));
				}
				// 底部用户共用模块 end

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

				// 技能模块 start
				// 技能图片
				if (null != entity.good_info.image && !entity.good_info.image.isEmpty()) {
					loadImage(holder.skillImg, entity.good_info.image.get(0), LoadImageType.NormalImage);
				} else {
					holder.skillImg.setImageResource(R.drawable.defaultpic);
				}

				// 技能标题
				if (TextUtils.isEmpty(entity.name)) {
					holder.skillTitle.setText("未知技能");
				} else {
					holder.skillTitle.setText(entity.name);
				}

				// 价格
				boolean isFee = entity.total_fee == entity.user_should_fee;
				if (isFee) {
					// 现价
					holder.skillNowPrice.setVisibility(View.INVISIBLE);
					// 原价（注意处理免薪实习问题）
//					holder.skillPrice.setText(String.valueOf(entity.user_should_fee));
					// 取消设置的的划线
					holder.skillPrice.getPaint().setFlags(0);
				} else {
					// 现价
					holder.skillNowPrice.setText(String.valueOf(entity.total_fee));
					holder.skillNowPrice.setVisibility(View.VISIBLE);
					// 原价
					holder.skillPrice.setText(String.valueOf(entity.user_should_fee));
					// 设置中划线并加清晰
					holder.skillPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
				}

				// 价格单位
				StringBuilder sb = new StringBuilder();
				sb.append("￥");
				sb.append(entity.unit_price);// 单价
				if (!TextUtils.isEmpty(entity.good_info.unit)) {
					sb.append(entity.good_info.unit);// 价格单位
				}
				sb.append(" * ");
				sb.append(entity.quantity);
				holder.skillUnit.setText(sb.toString());
				// 技能模块 end

				// 需求模块 start
				// 需求标题
				if (TextUtils.isEmpty(entity.name)) {
					holder.needTitle.setText("未知需求");
				} else {
					holder.needTitle.setText(entity.name);
				}

				//需求图片
				if (null != entity.good_info.image && !entity.good_info.image.isEmpty()) {
					loadImage(holder.needImg, entity.good_info.image.get(0), LoadImageType.NormalImage);
				} else {
					holder.needImg.setImageResource(R.drawable.defaultpic);
				}

				// 价格
				boolean isFee_ = entity.total_fee == entity.user_should_fee;
				if (isFee_) {
					// 现价
					holder.needNowPrice.setVisibility(View.INVISIBLE);
					// 原价
					holder.needPrice.setText(String.valueOf(entity.user_should_fee));
					// 取消设置的的划线
					holder.needPrice.getPaint().setFlags(0);
				} else {
					// 现价
					holder.needNowPrice.setText(String.valueOf(entity.total_fee));
					holder.needNowPrice.setVisibility(View.VISIBLE);
					// 原价
					holder.needPrice.setText(String.valueOf(entity.user_should_fee));
					// 设置中划线并加清晰
					holder.needPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
				}
				// 需求模块 end

				// 点击事件 start
				final int index = position;

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_ORDER_DETAIL);
						}
					}
				});

				holder.function.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_ORDER_FUNCTION);
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

				holder.needImg.setOnClickListener(new OnClickListener() {

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
//                            listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_CALL_TO_SALE);
							GetUri.openCallTelephone(context, entity.seller_user.mobile);

						}
					}
				});

				holder.sendMsg.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null != listener) {
							listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_IM_TO_SALE);
						}
					}
				});
				// 点击事件 end
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<MyBuyEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
	}

	@Override
	public List<MyBuyEntity> getData() {
		return list;
	}

	private void loadImage(View view, String url, LoadImageType loadImageType) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, url, loadImageType);
	}

	class Holder {
		TextView message;// 顶部状态消息

		TextView status;// 状态
		Button function;// 操作

		// 技能模块
		LinearLayout skill;// 技能外层布局
		ImageView skillImg;// 技能图片
		TextView skillTitle;// 技能标题
		TextView skillNowPrice;// 现价(有改价情况之后出现)
		TextView skillPrice;// 原价（单价乘以数量）
		TextView skillUnit;// 单位

		// 需求模块
		LinearLayout need;// 需求外层布局
		ImageView needImg;// 需求图片
		TextView needTitle;// 需求标题
		TextView needPrice;// 需求原价格
		TextView needNowPrice;// 需求价格

		LinearLayout user;// 用户外层布局
		ImageView avatar;
		TextView nick;
		TextView start;
		ImageView callPhone;
		ImageView sendMsg;

		public Holder(View convertView) {
			message = (TextView) convertView.findViewById(R.id.tv_order_message);

			status = (TextView) convertView.findViewById(R.id.tv_status_txt);
			function = (Button) convertView.findViewById(R.id.btn_function);

			skill = (LinearLayout) convertView.findViewById(R.id.line_skill_info);
			skillImg = (ImageView) convertView.findViewById(R.id.iv_skill_image);
			skillTitle = (TextView) convertView.findViewById(R.id.tv_skill_title);
			skillNowPrice = (TextView) convertView.findViewById(R.id.tv_skill_now_price);
			skillPrice = (TextView) convertView.findViewById(R.id.tv_skill_price);
			skillUnit = (TextView) convertView.findViewById(R.id.tv_skill_unit);

			need = (LinearLayout) convertView.findViewById(R.id.line_need_info);
			needImg = (ImageView) convertView.findViewById(R.id.iv_need_image);
			needTitle = (TextView) convertView.findViewById(R.id.tv_need_title);
			needPrice = (TextView) convertView.findViewById(R.id.tv_need_price);
			needNowPrice = (TextView) convertView.findViewById(R.id.tv_need_now_price);

			user = (LinearLayout) convertView.findViewById(R.id.line_user_info);
			avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			nick = (TextView) convertView.findViewById(R.id.tv_nick);
			start = (TextView) convertView.findViewById(R.id.tv_start);
			callPhone = (ImageView) convertView.findViewById(R.id.iv_call_phone);
			sendMsg = (ImageView) convertView.findViewById(R.id.iv_send_msg);
		}
	}
}
