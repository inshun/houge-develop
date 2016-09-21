package com.ysp.houge.presenter.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserContorller;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.IOrderModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.model.impl.OrderModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IOrderPresenter;
import com.ysp.houge.ui.iview.IOrderPageView;
import com.ysp.houge.ui.me.address.AddressManagerActivity;
import com.ysp.houge.ui.order.OrderDetailsActivity;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class OrderPresenter extends BasePresenter<IOrderPageView> implements IOrderPresenter {
	/**
	 * 显示的时间格式对象
	 */
	private SimpleDateFormat sdfShow = new SimpleDateFormat("MM-dd");
	/**
	 * 上传的时间格式对象
	 */
	private SimpleDateFormat sdfUpload = new SimpleDateFormat("yyyy-MM-dd");

	private boolean isNeedOrder = false;
	private OrderEntity orderEntity;
	private IOrderModel iOrderModel;
	private IAddressModel iAddressModel;

	public OrderPresenter(IOrderPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
		iOrderModel = new OrderModelImpl();
		iAddressModel = new AddressModelImpl();
	}

	@Override
	public void setOrderInfo(OrderEntity entity) {
		// TODO 设置订单实体
		this.orderEntity = entity;
		orderEntity.payWay = OrderDetailsEntity.OrderEntity.PAY_TYPE_ALIPAY;
		orderEntity.addressEntity = null;
		orderEntity.serviceDate = null;
		mView.changePayWay(orderEntity.payWay);
		mView.showOrderInfo(orderEntity);
	}

	@Override
	public void setIsNeed() {
		isNeedOrder = true;
	}

	@Override
	public void getDefaultAddress(int user_id) {
		int apt = MyApplication.LOG_STATUS_BUYER;
		iAddressModel.getDefaultAddress(user_id, apt, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof AddressEntity) {
					AddressEntity entity = (AddressEntity) data;
					orderEntity.addressEntity = entity;
					mView.showAddress(entity);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
			}
		});
	}

	@Override
	public void safeDealClick() {
		// TODO 安全协议
		mView.jumpToSafeDeal(null);
	}

	@Override
	public void chooseCoupomClick() {
		// TODO 优惠券
		mView.jumpToChooseCoupon(null);
	}

	@Override
	public void ContactWayClick() {
		// TODO 选择联系方式
		Bundle bundle = new Bundle();
		bundle.putInt(AddressManagerActivity.ENTER_PAGE_KEY, AddressManagerActivity.ENTER_PAGE_CHOOSE_ADDRESS);
		mView.jumpToChooseAddress(bundle);
	}

	@Override
	public void ContactWayChooseFinish(AddressEntity entity) {
		// TODO 选择完联系方式
		orderEntity.addressEntity = entity;
		mView.showAddress(entity);
	}

	@Override
	public void serviceTimeClick() {
		// TODO 显示服务时间
		mView.showServiceTimeDialog();
	}

	@Override
	public void serviceTimeChooseFinish(String time, int date) {
		// TODO 选择完服务时间
		Date d = DateUtil.getDateAdd(new Date(), date);
		String week = "";
		switch (date) {
			case 0:
				week = "今天";
				break;
			case 1:
				week = "明天";
				break;
			case 2:
				week = "后天";
				break;
			default:
				week = DateUtil.WEEKS[DateUtil.getDayOfWeek(d) - 1];
				break;
		}
		String serviceTimeShow = week + " " + sdfShow.format(d) + " " + time;
		String serviceTimeUpload = sdfUpload.format(d) + " " + time;
		orderEntity.serviceDate = String.valueOf(DateUtil.stringToLong(serviceTimeUpload, "yyyy-MM-dd HH:mm"));
		if (TextUtils.equals(orderEntity.serviceDate, "0")) {
			orderEntity.serviceDate = "";
		}
		mView.showServiceTime(serviceTimeShow);
	}

	@Override
	public void changePayWay(int payWay) {
		// TODO 改变支付方式
		orderEntity.payWay = payWay;
		mView.changePayWay(orderEntity.payWay);
	}

	@Override
	public void pay(String memo) {
		// TODO 支付
		if (!isNeedOrder) {
			if (orderEntity.addressEntity == null) {
				mView.showToast("请选择联系方式");
				return;
			}

			if (orderEntity.serviceDate == null) {
				mView.showToast("请选择服务时间");
				return;
			}
		}

		// 先去创建订单
		if (orderEntity.payWay == OrderDetailsEntity.OrderEntity.PAY_TYPE_BALANCE) {
			// 余额支付的时候先计算余额
			if (MyApplication.getInstance().getUserInfo().balance < orderEntity.totalMoney) {
				mView.showToast("余额不足");
				return;
			}
		}
		creteOrder(memo);
	}

	private void creteOrder(String memo) {
		mView.showProgress();
		int address_id = -1;
		int order_type = -1;
		String date = "";
		int buyer_create_user = -1;
		if (isNeedOrder) {
			if (null == orderEntity.skillDetailEntity
					|| TextUtils.isEmpty(orderEntity.skillDetailEntity.start_time)
					|| null == orderEntity.skillDetailEntity.address) {
				mView.showToast("错误的信息，详情咨询客服");
				return;
			}
			buyer_create_user = orderEntity.user_id;
			address_id = orderEntity.skillDetailEntity.address.id;
			order_type = OrderDetailsEntity.ORDER_TYPE_TASK;
			date = orderEntity.skillDetailEntity.start_time;
		} else {
			address_id = orderEntity.addressEntity.id;
			order_type = OrderDetailsEntity.ORDER_TYPE_SKILL;
			date = orderEntity.serviceDate;
		}
		iOrderModel.createOrder(orderEntity.skillDetailEntity.id, orderEntity.num, address_id, memo,
				orderEntity.payWay, order_type, date, buyer_create_user,
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						mView.hideProgress();
						if (data != null && data instanceof String) {
							if (orderEntity.payWay == OrderDetailsEntity.OrderEntity.PAY_TYPE_ALIPAY) {
								// 得到签名的key
								String alipaySign = (String) data;
								// checkAlipaySign(alipaySign);(测试完成后换成这个)
								mView.alipay(alipaySign);
							} else {
								checkAlipaySign((String) data);
							}
						} else {
							mView.showToast(R.string.request_failed);
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						mView.hideProgress();
						switch (errorCode) {
							case ResponseCode.TIP_ERROR:
								mView.showToast(message);
								break;
							default:
								mView.showToast(R.string.request_failed);
								break;
						}
					}
				});
	}

	@Override
	public void checkAlipaySign(String sign) {
		// TODO 验证支付宝签名(拆分订单号，跳转订单详情页面)
		StringBuilder builder = new StringBuilder(sign);
		String tag = "out_trade_no=";
		if (builder.indexOf(tag) >= 0) {
			// 找到订单号参数，截断之前的 字符
			builder.delete(0, builder.indexOf(tag));
			// 截断订单号参数字符
			builder.delete(0, new String(tag).length());
			if (builder.indexOf("&") > 0) {
				// 找到参数结束标志&，并截断之后的参数
				builder.delete(builder.indexOf("&"), builder.length());
			}

			Bundle bundle = new Bundle();
			bundle.putString("TAG", "BUY");
			bundle.putBoolean("isFuncation", false);
			bundle.putString(OrderDetailsActivity.KEY, builder.toString());
			mView.jumpToOrderDetails(bundle);
			mView.close();
		} else {
			mView.showToast("订单编号异常");
		}
	}


	@Override
	public void getUserInfo() {
		// TODO 获取用户信息
		iOrderModel.meInfoRequest(true, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof UserInfoEntity) {
					UserInfoEntity userInfo = (UserInfoEntity) data;
					userInfo.im_id = MyApplication.getInstance().getUserInfo().im_id;
					userInfo.im_pass = MyApplication.getInstance().getUserInfo().im_pass;
					// 更新内存中的个人信息数据
					MyApplication.getInstance().setUserInfo(userInfo);
					// 保存或更新个人信息到数据库
					UserInfoController.createOrUpdate(userInfo);
					//存储到基本信息表
					UserEntity userEntity = new UserEntity(userInfo.id, userInfo.nick, userInfo.avatar, userInfo.sex);
					UserContorller.createOrUpdate(userEntity);
					mView.showUserInfo(userInfo);
				} else {
					mView.showToast("获取信息异常");
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				mView.showUserInfo(MyApplication.getInstance().getUserInfo());
			}
		});
	}
}

