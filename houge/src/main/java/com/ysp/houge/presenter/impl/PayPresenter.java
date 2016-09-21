package com.ysp.houge.presenter.impl;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.IOrderModel;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;
import com.ysp.houge.model.entity.eventbus.PaySuccessEventBusEntity;
import com.ysp.houge.model.impl.OrderModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IPayPresenter;
import com.ysp.houge.ui.iview.IPayPagerView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import android.text.TextUtils;

/**
 * 描述： 支付页面Presenter层
 *
 * @ClassName: PayPagerPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月30日 上午10:17:02
 * 
 *        版本: 1.0
 */
public class PayPresenter extends BasePresenter<IPayPagerView> implements IPayPresenter {
	private String order_id;
	private IPayPagerView iPayPagerView;
	private IOrderModel iOrderModel;

	public PayPresenter(IPayPagerView view) {
		super(view);
		iPayPagerView = view;
	}

	@Override
	public void initModel() {
		iOrderModel = new OrderModelImpl();
	}

	@Override
	public void setOrderId(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public void clickEQS() {
		iPayPagerView.showEQSPop();
	}

	@Override
	public void pay(int payType) {
		switch (payType) {
		case OrderEntity.PAY_TYPE_ALIPAY:
			aliPay(order_id);
			break;
		case OrderEntity.PAY_TYPE_FACE_TO_FACE:
			// 这个参数没有自持
			mView.showToast("未支持的支付类型");
			break;
		case OrderEntity.PAY_TYPE_BALANCE:
			balance(order_id);
			break;
		default:
			break;
		}
	}

	private void aliPay(String order_id) {
		if (TextUtils.isEmpty(order_id)) {
			mView.showToast("错误的订单号");
			return;
		} else {
			mView.showProgress();
			iOrderModel.getSignByOrderId(order_id, new OnNetResponseCallback() {

				@Override
				public void onSuccess(Object data) {
					mView.hideProgress();
					if (null != data && data instanceof String) {
						// 获得到签名进行后续操作
						mView.aliPay((String) data);
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
	}

	private void balance(String order_id2) {
		if (TextUtils.isEmpty(order_id)) {
			mView.showToast("错误的订单号");
			return;
		} else {
			mView.showProgress();
			iOrderModel.payByBalcance(order_id, new OnNetResponseCallback() {

				@Override
				public void onSuccess(Object data) {
					mView.hideProgress();
					EventBus.getDefault().post(new PaySuccessEventBusEntity());
					mView.showToast("支付成功");
					mView.close();
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
	}
}
