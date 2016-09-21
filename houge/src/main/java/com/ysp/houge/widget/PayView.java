package com.ysp.houge.widget;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 描述： 支付视图
 *
 * @ClassName: PayView
 * 
 * @author: hx
 * 
 * @date: 2015年12月30日 上午9:06:55
 * 
 *        版本: 1.0
 */
public class PayView extends LinearLayout implements OnClickListener {
	private int[] icon = new int[] { R.drawable.icon_sel_orange, R.drawable.icon_def };
	private ImageView aliPay, balance, faceToFace;
	private TextView tvBalance;

	private int payWay = OrderEntity.PAY_TYPE_ALIPAY;

	public PayView(Context context) {
		super(context);
		init(context);
	}

	public PayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		// 加载布局
		View.inflate(context, R.layout.view_pay, this);
		initView();
	}

	private void initView() {
		findViewById(R.id.line_order_pay_way_alipay).setOnClickListener(this);
		findViewById(R.id.line_order_pay_way_face_to_face).setOnClickListener(this);
		findViewById(R.id.line_order_pay_way_balance).setOnClickListener(this);

		aliPay = (ImageView) findViewById(R.id.iv_alipay_choose_icon);
		faceToFace = (ImageView) findViewById(R.id.iv_face_to_face_choose_icon);
		balance = (ImageView) findViewById(R.id.iv_balance_choose_icon);

		tvBalance = (TextView) findViewById(R.id.tv_balance);
		tvBalance.setText(String.valueOf(MyApplication.getInstance().getUserInfo().balance));

		choosePayType(payWay);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.line_order_pay_way_alipay:
			if (payWay != OrderEntity.PAY_TYPE_ALIPAY) {
				payWay = OrderEntity.PAY_TYPE_ALIPAY;
				choosePayType(payWay);
			}
			break;

		case R.id.line_order_pay_way_face_to_face:
			if (payWay != OrderEntity.PAY_TYPE_FACE_TO_FACE) {
				payWay = OrderEntity.PAY_TYPE_FACE_TO_FACE;
				choosePayType(payWay);
			}
			break;

		case R.id.line_order_pay_way_balance:
			if (payWay != OrderEntity.PAY_TYPE_BALANCE) {
				payWay = OrderEntity.PAY_TYPE_BALANCE;
				choosePayType(payWay);
			}
			break;
		}
	}

	private void choosePayType(int type) {
		switch (type) {
		case OrderEntity.PAY_TYPE_ALIPAY:
			aliPay.setImageResource(icon[0]);
			balance.setImageResource(icon[1]);
			faceToFace.setImageResource(icon[1]);
			break;

		case OrderEntity.PAY_TYPE_FACE_TO_FACE:
			aliPay.setImageResource(icon[1]);
			balance.setImageResource(icon[1]);
			faceToFace.setImageResource(icon[0]);
			break;
		case OrderEntity.PAY_TYPE_BALANCE:
			aliPay.setImageResource(icon[1]);
			balance.setImageResource(icon[0]);
			faceToFace.setImageResource(icon[1]);
			break;
		}
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(int payWay) {
		this.payWay = payWay;
		choosePayType(this.payWay);
	}

    public void hideFaceToFace(){
        findViewById(R.id.line_order_pay_way_face_to_face).setVisibility(GONE);
        findViewById(R.id.face_to_face).setVisibility(GONE);
    }
}
