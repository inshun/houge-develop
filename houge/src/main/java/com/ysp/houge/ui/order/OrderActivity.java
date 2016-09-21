package com.ysp.houge.ui.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.ServiceTimeDialog;
import com.ysp.houge.dialog.ServiceTimeDialog.OnChooseFinis;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IOrderPresenter;
import com.ysp.houge.presenter.impl.OrderPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IOrderPageView;
import com.ysp.houge.ui.me.address.AddressManagerActivity;
import com.ysp.houge.utility.AliPayUtils;
import com.ysp.houge.utility.AliPayUtils.AlipayResponeCallBack;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

/**
 * 描述： 预定
 *
 * @ClassName: OrderActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月16日 上午10:22:17
 * 
 *        版本: 1.0
 */
@SuppressLint("HandlerLeak")
public class OrderActivity extends BaseFragmentActivity implements IOrderPageView, OnClickListener {
    //需求订单进入必须携带的Key
    public static final String KEY = "need_order";

	private ImageView ivOrderImg;
	private TextView tvOrderTitle;
	private TextView tvOrderPrice;

	private TextView tvOrderContactWay;
	private TextView tvOrderServiceTime;

	private ClearEditText etMemo;
	// 支付方式选中图标
	private ImageView ivPayAlipay;// 支付宝
	private ImageView ivPayFaceToFace;// 当面
	private ImageView ivPayBalance;// 余额
	private TextView tvBalance;;

	// 支付方式是否选中数据（选中，未选中）
	private int[] chooseImg = new int[] { R.drawable.icon_sel_orange, R.drawable.icon_def };

	private TextView tvPay;
	private ServiceTimeDialog dialog;

    //是否是需求订单的标记
    private boolean isNeedOrder = false;
	private AliPayUtils aliPayUtils;
	private IOrderPresenter iOrderPagePresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, OrderActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_order);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.order));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {


		ivOrderImg = (ImageView) findViewById(R.id.iv_order_img);
		tvOrderTitle = (TextView) findViewById(R.id.tv_order_name);
		tvOrderPrice = (TextView) findViewById(R.id.tv_order_total);

		// 安全协议
		findViewById(R.id.tv_order_safe_deal).setOnClickListener(this);
		// 选择优惠券
		findViewById(R.id.tv_order_choose_coupon).setOnClickListener(this);

		EventBus.getDefault().register(this);
		// 联系方式
		findViewById(R.id.line_order_contact_way).setOnClickListener(this);

		// 服务时间
		findViewById(R.id.line_order_service_time).setOnClickListener(this);

		tvOrderContactWay = (TextView) findViewById(R.id.tv_order_contact_way);
		tvOrderServiceTime = (TextView) findViewById(R.id.tv_order_service_time);

		etMemo = (ClearEditText) findViewById(R.id.tv_order_memo);

		ivPayAlipay = (ImageView) findViewById(R.id.iv_order_alipay_choose_icon);
		ivPayFaceToFace = (ImageView) findViewById(R.id.iv_order_face_to_face_choose_icon);
		ivPayBalance = (ImageView) findViewById(R.id.iv_order_balance_choose_icon);

		// 支付宝支付
		findViewById(R.id.line_order_pay_way_alipay).setOnClickListener(this);
		// 当面付
		findViewById(R.id.line_order_pay_way_face_to_face).setOnClickListener(this);
		// 余额支付
		findViewById(R.id.line_order_pay_way_balance).setOnClickListener(this);

		tvBalance = (TextView) findViewById(R.id.tv_balance);

		tvPay = (TextView) findViewById(R.id.tv_order_pay);
		tvPay.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		// TODO 初始化
		iOrderPagePresenter = new OrderPresenter(this);

		OrderEntity entity = null;
		if (getIntent().getExtras().containsKey(ORDER_SKILL_KEY)) {
			entity = (OrderEntity) getIntent().getExtras().getSerializable(ORDER_SKILL_KEY);
            if (getIntent().getExtras().containsKey(KEY)){
                isNeedOrder = true;
                iOrderPagePresenter.setIsNeed();
                findViewById(R.id.line_add_and_time).setVisibility(View.GONE);
            }else {
			    iOrderPagePresenter.getDefaultAddress(MyApplication.getInstance().getCurrentUid());
            }
			iOrderPagePresenter.setOrderInfo(entity);
		} else {
			showToast("未知错误");
			finish();
		}
	}

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

	@Override
	public void showOrderInfo(OrderEntity entity) {
		// TODO 显示订单基本信息
		if (entity.skillDetailEntity.image != null && entity.skillDetailEntity.image.size() > 0) {
			MyApplication.getInstance().getImageLoaderManager().loadNormalImage(ivOrderImg,
					entity.skillDetailEntity.image.get(0), LoadImageType.NormalImage);
		}

		tvOrderTitle.setText(entity.skillDetailEntity.title);
		tvOrderPrice.setText("总价 ￥" + entity.totalMoney + "元");
//		tvBalance.setText(MyApplication.getInstance().getUserInfo().balance + "");
		iOrderPagePresenter.getUserInfo();


	}


	@Override
	public void jumpToSafeDeal(Bundle bundle) {
		// TODO 安全协议
		showToast("跳转安全协议");
	}

	@Override
	public void jumpToChooseCoupon(Bundle bundle) {
		// TODO 选择优惠券
		showToast("选择优惠券");
	}

	@Override
	public void jumpToChooseAddress(Bundle bundle) {
		// TODO 先择地址
		AddressManagerActivity.jumpIn(this, bundle);
	}

	@Override
	public void showAddress(AddressEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append(entity.real_name);
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append(entity.mobile);
		sb.append("<br>");
		sb.append(entity.street);
		tvOrderContactWay.setText(Html.fromHtml(sb.toString()));
	}

	@Override
	public void showServiceTimeDialog() {
		// TODO 服务时间弹窗
		if (dialog == null) {
			dialog = new ServiceTimeDialog(this, SizeUtils.getScreenWidth(this));
		}
		dialog.setOnChooseFinis(new OnChooseFinis() {

			@Override
			public void onFinish() {
				int date = dialog.date;
				String time = dialog.time;
				iOrderPagePresenter.serviceTimeChooseFinish(time, date);
			}
		});

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	@Override
	public void showServiceTime(String ServiceTime) {
		// TODO 显示服务时间
		tvOrderServiceTime.setText(ServiceTime);
	}

	@Override
	public void changePayWay(int payWay) {
		// TODO 改变支付方式
		switch (payWay) {
		// 支付宝
		case OrderDetailsEntity.OrderEntity.PAY_TYPE_ALIPAY:
			ivPayAlipay.setImageResource(chooseImg[0]);
			ivPayFaceToFace.setImageResource(chooseImg[1]);
			ivPayBalance.setImageResource(chooseImg[1]);
			break;

		// 当面
		case OrderDetailsEntity.OrderEntity.PAY_TYPE_FACE_TO_FACE:
			ivPayAlipay.setImageResource(chooseImg[1]);
			ivPayFaceToFace.setImageResource(chooseImg[0]);
			ivPayBalance.setImageResource(chooseImg[1]);
			break;

		// 余额
		case OrderDetailsEntity.OrderEntity.PAY_TYPE_BALANCE:
			ivPayAlipay.setImageResource(chooseImg[1]);
			ivPayFaceToFace.setImageResource(chooseImg[1]);
			ivPayBalance.setImageResource(chooseImg[0]);
			break;

		default:
			ivPayAlipay.setImageResource(chooseImg[0]);
			ivPayFaceToFace.setImageResource(chooseImg[1]);
			ivPayBalance.setImageResource(chooseImg[1]);
			break;
		}
	}

	/** 接收选择地址页面的 */
	public void onEventMainThread(AddressEntity entity) {
		iOrderPagePresenter.ContactWayChooseFinish(entity);
	}

	@Override
	public void jumpToOrderDetails(Bundle bundle) {
		// TODO 订单详情页面
		OrderDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void showUserInfo(UserInfoEntity info) {
		tvBalance.setText(String.valueOf(info.balance));
	}

	@Override
	public void alipay(final String sign) {
		// TODO 支付宝支付
		aliPayUtils = new AliPayUtils(new AlipayResponeCallBack() {

			@Override
			public void payWait() {
				showToast("支付结果确认中");
				iOrderPagePresenter.checkAlipaySign(sign);
			}

			@Override
			public void paySuccess(String sign) {
				// 这里拿着签名再去验证
				iOrderPagePresenter.checkAlipaySign(sign);
			}

			@Override
			public void payError(int errorCode) {
				showToast("支付失败");
				iOrderPagePresenter.checkAlipaySign(sign);
			}
		}, sign);
		aliPayUtils.alipay(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 点击事件
		switch (v.getId()) {
		// 安全协议
		case R.id.tv_order_safe_deal:
			iOrderPagePresenter.safeDealClick();
			break;

		// 优惠券
		case R.id.tv_order_choose_coupon:
			iOrderPagePresenter.chooseCoupomClick();
			break;

		// 联系方式
		case R.id.line_order_contact_way:
			iOrderPagePresenter.ContactWayClick();
			break;

		// 服务时间
		case R.id.line_order_service_time:
			iOrderPagePresenter.serviceTimeClick();
			break;

		// 支付宝
		case R.id.line_order_pay_way_alipay:
			iOrderPagePresenter.changePayWay(OrderDetailsEntity.OrderEntity.PAY_TYPE_ALIPAY);
			break;

		// 当面
		case R.id.line_order_pay_way_face_to_face:
			iOrderPagePresenter.changePayWay(OrderDetailsEntity.OrderEntity.PAY_TYPE_FACE_TO_FACE);
			break;

		// 余额
		case R.id.line_order_pay_way_balance:
			iOrderPagePresenter.changePayWay(OrderDetailsEntity.OrderEntity.PAY_TYPE_BALANCE);
			break;

		// 支付
		case R.id.tv_order_pay:
			iOrderPagePresenter.pay(etMemo.getText().toString().trim());
			break;
		default:
			break;
		}
	}
}
