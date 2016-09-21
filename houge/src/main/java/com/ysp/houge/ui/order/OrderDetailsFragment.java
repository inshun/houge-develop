package com.ysp.houge.ui.order;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.entity.eventbus.PaySuccessEventBusEntity;
import com.ysp.houge.presenter.IOrderDetailsFragmentPrsenter;
import com.ysp.houge.presenter.impl.OrderDetailsFragmentPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IOrderDetailsFragmentView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.widget.ClearEditText;

/**
 * 描述： 订单详情Fragment
 *
 * @ClassName: OrderDetailsFragment
 * @author: hx
 * @date: 2015年12月16日 上午10:22:32
 * <p/>
 * 版本: 1.0
 */
@SuppressLint("InflateParams")
public class OrderDetailsFragment extends BaseFragment implements OnClickListener, IOrderDetailsFragmentView, SwipeRefreshLayout.OnRefreshListener {
	private SwipeRefreshLayout refreshLayout;

	/**
	 * 订单状态描述
	 */
	private TextView statusDesc;
	/**
	 * 订单状态展示
	 */
	private TextView orderStatus;
	/**
	 * 订单状态操作按钮(不同的状态下有不同的操作)
	 */
	private Button statusOperate;
	/**
	 * 订单图片
	 */
	private ImageView orderImg;
	/**
	 * 订单标题、价格、单位
	 */
	private TextView orderTitle, orderPrice, orderUnit;
	/**
	 * 改价
	 */
	private Button changePrice;

	/**
	 * 服务者头像、电话，短信
	 */
	private ImageView userAvatar, callPhone, sendMessage;
	/**
	 * 服务者名称、评级、服务保障、认证
	 */
	private TextView userName, userRate, userSafeguard, userAuth;
	/**
	 * 订单号、订单时间
	 */
	private TextView orderNum, orderTime;
	/**
	 * 联系方式、服务时间、备注
	 */
	private TextView contacts, serviceTime, mome;
	/**
	 * 支付方式显示的图片
	 */
	private ImageView payWay;
	/**
	 * 支付方式以及金额描述
	 */
	private TextView payWayDesc;
	private TextView cancleOrder;
	/**
	 * 取消订单布局
	 */
	private LinearLayout cancleOrderLayout;
	/**
	 * 商品布局
	 */
	private LinearLayout good;
	/**
	 * 用户布局
	 */
	private LinearLayout user;

	private ClearEditText price;
	private Button sure, cancle;

	private String order_id;
	private String status;
	private int operation;

	private String flag;
	private PopupWindow pop;

	private UserInfoEntity userInfoEntity;
	private OrderDetailsEntity orderDetailsEntity;

	private IOrderDetailsFragmentPrsenter iOrderDetailsFragmentPrsenter;

	@Override
	protected int getContentView() {
		return R.layout.fragment_order_details;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mRefreshListView);
		refreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_app_theme_orange_bg), getResources().getColor(R.color.color_app_theme_blue_bg));
		refreshLayout.setOnRefreshListener(this);

		statusDesc = (TextView) view.findViewById(R.id.tv_status_desc);
		orderStatus = (TextView) view.findViewById(R.id.tv_order_status);
		statusOperate = (Button) view.findViewById(R.id.btn_status_operate);

		orderImg = (ImageView) view.findViewById(R.id.iv_order_details_img);
		orderTitle = (TextView) view.findViewById(R.id.tv_order_details_title);
		orderPrice = (TextView) view.findViewById(R.id.tv_order_details_price);
		orderUnit = (TextView) view.findViewById(R.id.tv_order_details_unit);
		changePrice = (Button) view.findViewById(R.id.btn_change_price);
		good = (LinearLayout) view.findViewById(R.id.line_good);

		userAvatar = (ImageView) view.findViewById(R.id.iv_servicer_icon);
		callPhone = (ImageView) view.findViewById(R.id.iv_call_phone);
		sendMessage = (ImageView) view.findViewById(R.id.iv_send_message);
		userName = (TextView) view.findViewById(R.id.tv_servicer_nick);
		userRate = (TextView) view.findViewById(R.id.tv_rate);
		userSafeguard = (TextView) view.findViewById(R.id.tv_service_safeguard);
		userAuth = (TextView) view.findViewById(R.id.tv_auth);
		user = (LinearLayout) view.findViewById(R.id.line_service_user);

		orderNum = (TextView) view.findViewById(R.id.tv_order_num);
		orderTime = (TextView) view.findViewById(R.id.tv_order_date);

		contacts = (TextView) view.findViewById(R.id.tv_service_contanct_way);
		serviceTime = (TextView) view.findViewById(R.id.tv_service_time);
		mome = (TextView) view.findViewById(R.id.tv_service_memo);

		payWay = (ImageView) view.findViewById(R.id.iv_pay_way_icon);
		payWayDesc = (TextView) view.findViewById(R.id.tv_service_price);

		cancleOrderLayout = (LinearLayout) view.findViewById(R.id.line_cancle_order_layout);
		cancleOrder = (TextView) view.findViewById(R.id.tv_cancle_order);

		changePrice.setOnClickListener(this);
		statusOperate.setOnClickListener(this);
		good.setOnClickListener(this);
		user.setOnClickListener(this);
		callPhone.setOnClickListener(this);
		sendMessage.setOnClickListener(this);
		cancleOrder.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iOrderDetailsFragmentPrsenter = new OrderDetailsFragmentPresenter(this);
		iOrderDetailsFragmentPrsenter.setOrderInfo(order_id, status, operation, this);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (null != iOrderDetailsFragmentPrsenter)
			iOrderDetailsFragmentPrsenter.requstOrderDetails();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
	}

	@Override
	public void onRefresh() {
		iOrderDetailsFragmentPrsenter.requstOrderDetails();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void close() {
		super.close();
		getActivity().finish();
	}

	@Override
	public void setOrderInfo(String order_id, String status, int operation) {
		this.order_id = order_id;
		this.status = status;
		this.operation = operation;

		if (TextUtils.isEmpty(order_id)) {
			showToast("错误的订单信息");
			close();
			return;
		}
	}

	@Override
	public void setOrderInfo(OrderDetailsEntity orderDetailsEntity) {
		refreshLayout.setRefreshing(false);

		if (null == orderDetailsEntity) {
			showToast("错误的订单信息");
			close();
			return;
		}

		//判断推送情况下的买卖家
		// status = "buy" 判断的是从我的购买到的订单详情 mobile ,直接略过此判断
		// status = ""　判断的是从首页支付到订单详情 mobile
		if (TextUtils.equals(status, "")) {
			if (null == orderDetailsEntity.seller || null == orderDetailsEntity.buyer) {
				showToast("错误的订单信息");
				close();
				return;
			} else {
				//这样写在技能订单的情况下是对的
				//如果是buyer.id  从首页支付到订单详情，用户是 buyer == true
				//如果是seller.id 从首页支付到订单详情，用户是 seller

				// MyApplication.getInstance().getCurrentUid() = ""
				if (MyApplication.getInstance().getCurrentUid() == orderDetailsEntity.buyer.id) {
					status = "buy";
				} else {
					status = "sell";
				}
			}
		}


		this.orderDetailsEntity = orderDetailsEntity;
		OrderDetailsEntity.OrderEntity orderEntity = orderDetailsEntity.order;
		GoodsDetailEntity skillDetailEntity = orderDetailsEntity.good;
		AddressEntity addressEntity = orderDetailsEntity.address;

		showInfoByOrder(orderEntity);
		showInfoByGood(skillDetailEntity);
		showInfoByAddress(addressEntity);

		//判断得到的status == buy ?  seller  :  buyer  头像，信息，不包括电话
		if (TextUtils.equals(status, "buy")) {
			userInfoEntity = orderDetailsEntity.seller;
		} else {
			userInfoEntity = orderDetailsEntity.buyer;
		}
		showInfoByUser(userInfoEntity);

	}

	private void showInfoByOrder(OrderDetailsEntity.OrderEntity orderEntity) {
		if (null == orderEntity) {
			return;
		}

		// 订单号
		if (TextUtils.isEmpty(orderEntity.order_id)) {
			orderNum.setText("订单号:未知");
		} else {
			orderNum.setText("订单号:" + orderEntity.order_id);
		}

		// 时间
		if (TextUtils.isEmpty(orderEntity.created_at)) {
			orderTime.setText("下单时间:未知");
		} else {
			orderTime.setText("下单时间:" + orderEntity.created_at);
		}

		// 备注
		if (TextUtils.isEmpty(orderEntity.memo)) {
			mome.setText("");
		} else {
			mome.setText(orderEntity.memo);
		}

		cancleOrderLayout.setVisibility(View.GONE);
		cancleOrder.setText("取消订单");
		cancleOrder.getText();
		statusOperate.setVisibility(View.VISIBLE);
		changePrice.setVisibility(View.GONE);

		// 状态
		switch (orderEntity.status) {
			case OrderEntity.STATUS_WAIT_PAY:
				if (TextUtils.equals(status, "buy")) {
					orderStatus.setText("未支付");
					statusOperate.setText("马上支付");
					statusOperate.setVisibility(View.VISIBLE);
					cancleOrderLayout.setVisibility(View.VISIBLE);
				} else {
					orderStatus.setText("待付款");
					cancleOrder.setVisibility(View.VISIBLE);
					statusOperate.setVisibility(View.GONE);
					changePrice.setVisibility(View.VISIBLE);
				}
				break;
			case OrderEntity.STATUS_WAIT_SERVICE:
				orderStatus.setText("待服务");
				if (TextUtils.equals(status, "buy")) {
					cancleOrderLayout.setVisibility(View.VISIBLE);
					statusOperate.setVisibility(View.GONE);
					//当面付类型显示未付款
					if (orderEntity.pay_type == OrderEntity.PAY_TYPE_FACE_TO_FACE)
						orderStatus.setText("未付款");
				} else {
					statusOperate.setText("去服务");
					statusOperate.setVisibility(View.VISIBLE);
					cancleOrderLayout.setVisibility(View.GONE);
				}
				break;
			case OrderEntity.STATUS_DEPART:
				if (TextUtils.equals(status, "buy")) {
					orderStatus.setText("服务人员已出发");
					statusOperate.setVisibility(View.GONE);
					//非当面付方式时，可以申请退款
//					if (orderEntity.pay_type != OrderEntity.PAY_TYPE_FACE_TO_FACE) {
//						cancleOrder.setText("申请退款");
//						cancleOrderLayout.setVisibility(View.VISIBLE);
//						cancleOrder.getText();
//                        LogUtil.setLogWithE("aaaaa", cancleOrder.getText().toString());
//					}
				} else {
					orderStatus.setText("去服务途中");
					statusOperate.setText("开始服务");
					statusOperate.setVisibility(View.VISIBLE);

//					//如果退款状态存在，则显示同意退款
//					if (orderEntity.is_refund == 1) {
//						cancleOrder.setText("同意退款");
//						cancleOrderLayout.setVisibility(View.VISIBLE);
//						cancleOrder.getText();
//					}
				}
				break;
			case OrderEntity.STATUS_IN_SERVICE:
				orderStatus.setText("服务中");
				if (TextUtils.equals(status, "buy")) {
					statusOperate.setVisibility(View.GONE);
					//非当面付方式时，可以申请退款
//					if (orderEntity.pay_type != OrderEntity.PAY_TYPE_FACE_TO_FACE) {
//						cancleOrder.setText("申请退款");
//						cancleOrderLayout.setVisibility(View.VISIBLE);
//						cancleOrder.getText();
//					}
				} else {
					statusOperate.setText("结束服务");
					statusOperate.setVisibility(View.VISIBLE);

					//如果退款状态存在，则显示同意退款(后续需要加上)
//					if (orderEntity.is_refund == 1) {
//						cancleOrder.setText("同意退款");
//						cancleOrderLayout.setVisibility(View.VISIBLE);
//						cancleOrder.getText();
//					}
				}
				break;
			case OrderEntity.STATUS_SERVICE_FINISH:
				if (TextUtils.equals(status, "buy")) {
					orderStatus.setText("对方已结束服务");
					statusOperate.setText("确认完成");
					statusOperate.setVisibility(View.VISIBLE);
					//非当面付方式时，可以申请退款
//					if (orderEntity.pay_type != OrderEntity.PAY_TYPE_FACE_TO_FACE) {
//						cancleOrder.setText("申请退款");
//						cancleOrderLayout.setVisibility(View.VISIBLE);
//						cancleOrder.getText();
//					}
				} else {
					orderStatus.setText("服务已结束");
					//如果退款状态存在，则显示同意退款(后续需要加上)
					statusOperate.setVisibility(View.GONE);
//					if (orderEntity.is_refund == 1) {
//						cancleOrder.setText("同意退款");
//						cancleOrderLayout.setVisibility(View.VISIBLE);
//						cancleOrder.getText();
//					}
				}
				break;
			case OrderEntity.STATUS_WAIT_COMMENT:
				if (TextUtils.equals(status, "buy")) {
						if (orderEntity.is_buyer_rate == 0) {
						if (orderEntity.is_seller_rate == 0) {
							orderStatus.setText("待评论");
							statusOperate.setText("去评论");
							orderStatus.getText();
							statusOperate.setVisibility(View.VISIBLE);
						} else {
							orderStatus.setText("对方已评论");
							statusOperate.setText("去回评");
							statusOperate.setVisibility(View.VISIBLE);
						}
					} else {
						orderStatus.setText("已评论");
						statusOperate.setVisibility(View.GONE);
					}
				} else {
					if (orderEntity.is_seller_rate == 0) {
						if (orderEntity.is_buyer_rate == 0) {
							orderStatus.setText("待评论");
							statusOperate.setText("去评论");
							statusOperate.setVisibility(View.VISIBLE);
						} else {
							orderStatus.setText("对方已评论");
							statusOperate.setText("去回评");
							statusOperate.setVisibility(View.VISIBLE);
						}
					} else {
						orderStatus.setText("已评论");
						orderStatus.setVisibility(View.GONE);
						statusOperate.setVisibility(View.GONE);

					}
				}

				//双方都评论了，需要请求订单状态操作，将订单进入完成状态
				if (orderEntity.is_seller_rate != 0 && orderEntity.is_buyer_rate != 0) {
					iOrderDetailsFragmentPrsenter.statusOperate();
				}
				break;
			case OrderEntity.STATUS_FINISH:
				if (TextUtils.equals(status, "buy")) {
					orderStatus.setText("已完成");
					statusOperate.setText("再来一单");
					statusOperate.setVisibility(View.VISIBLE);
				} else {
					orderStatus.setText("已完成");
					statusOperate.setVisibility(View.GONE);
				}
				break;
			case OrderEntity.STATUS_STATUS_EXPIRE:
				orderStatus.setText("已关闭");
				statusOperate.setVisibility(View.GONE);
				break;
			case OrderEntity.STATUS_FREEZE:
				orderStatus.setText("已冻结");
				statusOperate.setVisibility(View.GONE);
				break;
			case OrderEntity.STATUS_CLOSE:
				orderStatus.setText("已关闭");
				statusOperate.setVisibility(View.GONE);

				break;
		}


		// 支付类型
		StringBuilder sb = new StringBuilder();
		switch (orderEntity.pay_type) {
			case OrderDetailsEntity.OrderEntity.PAY_TYPE_ALIPAY:
				payWay.setImageResource(R.drawable.icon_zhifubao);
//			sb.append("支付宝支付");
				break;
			case OrderDetailsEntity.OrderEntity.PAY_TYPE_BALANCE:
				payWay.setImageResource(R.drawable.icon_yuer);
//			sb.append("余额支付");
				break;
			case OrderDetailsEntity.OrderEntity.PAY_TYPE_FACE_TO_FACE:
				payWay.setImageResource(R.drawable.icon_diangmianzhifu);
//			sb.append("当面付");
				break;
		}

		//现价
		orderPrice.setText(String.valueOf(orderEntity.total_fee));

		payWayDesc.setText(orderEntity.pay_type_text);
		if(orderEntity.status_text != null){
			statusDesc.setText(orderEntity.status_text);
		}else {
			statusDesc.setVisibility(View.GONE);
		}
		String time = "";
		try {
			long date = Long.parseLong(orderEntity.param.start_at);
			time = DateUtil.longToString(date, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			if (null != orderEntity.param && !TextUtils.isEmpty(orderEntity.param.start_at)) {
				time = orderEntity.param.start_at;
			}
		}
		if (!TextUtils.isEmpty(time)) {
			serviceTime.setText(time);
		} else {
			serviceTime.setText("未知的时间");
		}
	}

	private void showInfoByGood(GoodsDetailEntity goodDetailEntity) {
		if (null == goodDetailEntity) {
			return;
		}

		// 技能图片
		if (null != goodDetailEntity.image && !goodDetailEntity.image.isEmpty()) {
			MyApplication.getInstance().getImageLoaderManager().loadNormalImage(orderImg, goodDetailEntity.image.get(0),
					LoadImageType.NormalImage);
		}

		// 技能标题
		if (TextUtils.isEmpty(goodDetailEntity.title)) {
			orderTitle.setText("未知商品");
		} else {
			orderTitle.setText(goodDetailEntity.title);
		}

		//数量以及价格单位
		StringBuffer sb = new StringBuffer();
//		if (TextUtils.isEmpty(goodDetailEntity.price)) {
//			orderUnit.setText("免薪实习");
//			orderPrice.setVisibility(View.GONE);
//		} else {
			orderPrice.setVisibility(View.VISIBLE);
			sb.append(goodDetailEntity.price);
			if (!TextUtils.isEmpty(goodDetailEntity.unit)) {
				sb.append(goodDetailEntity.unit);
				sb.append("*");
			}
			sb.append(orderDetailsEntity.order.quantity);

			orderUnit.setText(sb.toString());
//		}

		if (orderDetailsEntity.order.total_fee == orderDetailsEntity.order.user_should_fee) {
			orderPrice.setVisibility(View.GONE);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void showInfoByUser(UserInfoEntity infoEntity) {
		if (null == infoEntity) {
			return;
		}

		// 头像背景
		switch (infoEntity.sex) {
			case UserInfoEntity.SEX_FEMAL:
				userAvatar.setBackground(getResources().getDrawable(R.drawable.shapa_sex_femal));
				break;
			case UserInfoEntity.SEX_MAL:
				userAvatar.setBackground(getResources().getDrawable(R.drawable.shapa_sex_mal));
				break;
			default:
				userAvatar.setBackground(getResources().getDrawable(R.drawable.shapa_sex_def));
				break;
		}

		// 头像
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(userAvatar, infoEntity.avatar,
				LoadImageType.RoundAvatar);

		if (TextUtils.isEmpty(infoEntity.nick)) {
			userName.setText("无名氏");
		} else {
			userName.setText(infoEntity.nick);
		}

		// 评级
		userRate.setText(String.valueOf(infoEntity.star) + " ★");

		// 服务保障
		if (TextUtils.isEmpty(infoEntity.serviceSafeguardg)) {
			userSafeguard.setVisibility(View.GONE);
		} else {
			if (TextUtils.equals("0", infoEntity.serviceSafeguardg)) {
				userSafeguard.setVisibility(View.GONE);
			} else {
				userSafeguard.setVisibility(View.VISIBLE);
			}
		}

		// 认证
		if (TextUtils.isEmpty(infoEntity.verfied)) {
			userAuth.setVisibility(View.GONE);
		} else {
			userAuth.setVisibility(View.VISIBLE);
			if (TextUtils.equals("1", infoEntity.verfied)) {
				userAuth.setText("学生认证");
			} else if (TextUtils.equals("2", infoEntity.verfied)) {
				userAuth.setText("个人认证");
			} else if (TextUtils.equals("3", infoEntity.verfied)) {
				userAuth.setText("企业认证");
			} else {
				userAuth.setVisibility(View.GONE);
			}
		}
	}

	private void showInfoByAddress(AddressEntity addressEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(addressEntity.street);
		//如果未付款， 地址截断','之后的详细部分
		if (orderDetailsEntity.order.is_payed != 1) {//1为已支付状态
			if (sb.indexOf(",") > 0) {
				sb.delete(sb.indexOf(","), sb.length());
			}
		} else {
			if (sb.indexOf(",") > 0) {
				sb.deleteCharAt(sb.indexOf(","));
			}
		}
		sb.append("<br/>");
		sb.append(addressEntity.real_name);
		sb.append("	");
		sb.append(addressEntity.mobile);
		contacts.setText(Html.fromHtml(sb.toString()));
	}

	@Override
	public void jumpToPay(Bundle bundle) {
		PayActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToOrder(com.ysp.houge.model.entity.bean.OrderEntity orderEntity) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(OrderActivity.ORDER_SKILL_KEY, orderEntity);
		OrderActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToComment(String id) {
		Bundle bundle = new Bundle();
		bundle.putString(CommentActivity.KEY, id);
		if (TextUtils.equals(status, "buy")) {
			//卖家
			bundle.putInt(CommentActivity.APT, MyApplication.LOG_STATUS_SELLER);
		} else {
			//买家
			bundle.putInt(CommentActivity.APT, MyApplication.LOG_STATUS_BUYER);
		}
		CommentActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void callPhone(String phone) {
		if (TextUtils.isEmpty(phone)) {
			showToast("错误的电话号码");
			return;
		}
		GetUri.openCallTelephone(getActivity(), phone);
	}

	@Override
	public void jumpToChatPage() {
		ChatPageEntity chatPageEntity = new ChatPageEntity(String.valueOf(userInfoEntity.id));
		Bundle bundle = new Bundle();
		bundle.putSerializable(ChatActivity.CHAT_PAGE_ENTITY, chatPageEntity);
		ChatActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToSkilldetail(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, id);
		SkillDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToNeedDetail(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, id);
		NeedDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void jumpToUser() {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, userInfoEntity.id);
		UserDetailsActivity.jumpIn(getActivity(), bundle);
	}

	@Override
	public void showChangePirceDialog() {
		flag = "changePrice";
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_password, null);
		((TextView) view.findViewById(R.id.tv_title)).setText("输入新的总价");
		price = (ClearEditText) view.findViewById(R.id.cet_pwd);
		sure = (Button) view.findViewById(R.id.btn_sure);
		cancle = (Button) view.findViewById(R.id.btn_cancle);
		price.setHint("请输入价格");
		price.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

		sure.setOnClickListener(this);
		cancle.setOnClickListener(this);

		pop = new PopupWindow(view, SizeUtils.dip2px(getActivity(), 300), LayoutParams.WRAP_CONTENT, true) {

			@Override
			public void dismiss() {
				super.dismiss();
				// pop消失的时候还原窗体透明度
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1.0f;
				getActivity().getWindow().setAttributes(lp);
			}
		};
		pop.setAnimationStyle(R.style.popwin_anim_top);
		pop.setFocusable(true);// 获得焦点
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// 透明度
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.3f;
		getActivity().getWindow().setAttributes(lp);
		// 设置显示的地方
		pop.showAtLocation(getActivity().getWindow().getDecorView().getRootView(), Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	@Override
	public void showTrefundReasonDialog() {
		flag = "trefund";
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_password, null);
		((TextView) view.findViewById(R.id.tv_title)).setText("请输入退款理由");
		price = (ClearEditText) view.findViewById(R.id.cet_pwd);
		sure = (Button) view.findViewById(R.id.btn_sure);
		cancle = (Button) view.findViewById(R.id.btn_cancle);
		price.setHint("请输入您的退款理由");
		price.setInputType(InputType.TYPE_CLASS_TEXT);

		sure.setOnClickListener(this);
		cancle.setOnClickListener(this);

		pop = new PopupWindow(view, SizeUtils.dip2px(getActivity(), 300), LayoutParams.WRAP_CONTENT, true) {

			@Override
			public void dismiss() {
				super.dismiss();
				// pop消失的时候还原窗体透明度
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1.0f;
				getActivity().getWindow().setAttributes(lp);
			}
		};
		pop.setAnimationStyle(R.style.popwin_anim_top);
		pop.setFocusable(true);// 获得焦点
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// 透明度
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.3f;
		getActivity().getWindow().setAttributes(lp);
		// 设置显示的地方
		pop.showAtLocation(getActivity().getWindow().getDecorView().getRootView(), Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	@Override
	public void showSureFinishDoalog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity("确认结束服务后平台将打款给对方", "是否确认", "取消", "确定");
		YesOrNoDialog dialog = new YesOrNoDialog(getActivity(), dialogEntity, new YesOrNoDialog.OnYesOrNoDialogClickListener() {

			@Override
			public void onYesOrNoDialogClick(YesOrNoDialog.YesOrNoType yesOrNoType) {
				if (yesOrNoType == YesOrNoDialog.YesOrNoType.BtnOk) {
					iOrderDetailsFragmentPrsenter.statusOperate();
				}
			}
		});
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	@Override
	public String getBtnText() {
		return (String) cancleOrder.getText();
	}

	@Override
	public String getOrderStaus() {
		return (String) orderStatus.getText();
	}


	private void showSureGoServerDialog() {
		YesOrNoDialogEntity entity = new YesOrNoDialogEntity("确定前往服务?", "", getString(R.string.cancel), getString(R.string.sure));
		new YesOrNoDialog(getActivity(), entity, new YesOrNoDialog.OnYesOrNoDialogClickListener() {
			@Override
			public void onYesOrNoDialogClick(YesOrNoDialog.YesOrNoType yesOrNoType) {
				if (yesOrNoType == YesOrNoDialog.YesOrNoType.BtnOk)
					iOrderDetailsFragmentPrsenter.statusOperate();
			}
		}).show();
	}

	/**
	 * 支付成功刷新的消息
	 */
	public void onEventMainThread(PaySuccessEventBusEntity busEntity) {
		// 这里无法立即刷新，后续弄一个延时请求
		iOrderDetailsFragmentPrsenter.requstOrderDetails();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 订单操作，不同的状态不同的操作，交给Presenter层处理就好了
			case R.id.btn_status_operate:
				if (DoubleClickUtil.isFastClick())
					return;

				if (orderDetailsEntity.order.status == OrderEntity.STATUS_WAIT_SERVICE
						&& !TextUtils.equals(status, "buy")) {
					showSureGoServerDialog();
				} else if (orderDetailsEntity.order.status == OrderEntity.STATUS_SERVICE_FINISH) {
					showSureFinishDoalog();
				} else {
					iOrderDetailsFragmentPrsenter.statusOperate();
				}
				break;

			//技能或者需求详情
			case R.id.line_good:
				iOrderDetailsFragmentPrsenter.goodClick();
				break;

			//用户详情
			case R.id.line_service_user:
				iOrderDetailsFragmentPrsenter.userClick();
				break;

			// 拨打电话
			case R.id.iv_call_phone:
				iOrderDetailsFragmentPrsenter.callPhone();
				break;

			// 发送短信
			case R.id.iv_send_message:
				iOrderDetailsFragmentPrsenter.sendMessage();
				break;

			// 取消订单
			case R.id.tv_cancle_order:
				if (!DoubleClickUtil.isFastClick()) {
					iOrderDetailsFragmentPrsenter.cancleOrder();
					return;
				}
				break;
			// 改价
			case R.id.btn_change_price:
				if (DoubleClickUtil.isFastClick())
					return;
				iOrderDetailsFragmentPrsenter.changePrice();
				break;

			// 弹窗确认改价/退款
			case R.id.btn_sure:
				if (DoubleClickUtil.isFastClick()) {
					return;
				}
				if (null != pop) {
					pop.dismiss();
				}
				if (TextUtils.equals(flag, "changePrice")) {
					iOrderDetailsFragmentPrsenter.changePrice(price.getText().toString());
				} else if (TextUtils.equals(flag, "trefund")) {
					iOrderDetailsFragmentPrsenter.requesTrefund(order_id, price.getText().toString());
				}

				break;

			// 取消
			case R.id.btn_cancle:
				if (null != pop) {
					pop.dismiss();
				}
				break;
		}
	}
}
