package com.ysp.houge.ui.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.impl.OrderModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.ColoredRatingBar;
import com.ysp.houge.widget.MyActionBar;

/**
 * 描述： 评论页面
 *
 * @ClassName: CommentActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月16日 下午5:36:56
 * 
 *        版本: 1.0
 */
public class CommentActivity extends BaseFragmentActivity implements OnClickListener {
	public static final String KEY = "order_id";
	public static final String APT = "apt";

	private String order_id = "";
	private int apt = MyApplication.LOG_STATUS_BUYER;
	private ColoredRatingBar rb;
	private ClearEditText comment;
	private TextView sure;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, CommentActivity.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_comment);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("评价");
		actionBar.setRightText("客服");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String json = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
				if (!TextUtils.isEmpty(json)) {
					GetUri.openCallTelephone(CommentActivity.this,
							new Gson().fromJson(json, AppInitEntity.class).mobile);
				} else {
					showToast("获取客服电话失败");
				}
			}
		});
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		rb = (ColoredRatingBar) findViewById(R.id.crb_scort);
		comment = (ClearEditText) findViewById(R.id.cet_commnet);
		sure = (TextView) findViewById(R.id.tv_sure);

		rb.setOnClickListener(this);
		comment.setOnClickListener(this);
		sure.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		rb.setRating(5);
		if (getIntent().getExtras().containsKey(KEY)) {
			order_id = getIntent().getExtras().getString(KEY);
			apt = getIntent().getExtras().getInt(APT);
		} else {
			showToast("错误的订单信息");
			close();
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
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.crb_scort:
		// isRate = true;
		// break;

		case R.id.tv_sure:
			commet(comment.getText().toString());
			break;
		default:
			break;
		}
	}

	private void commet(String comment) {
		if (TextUtils.isEmpty(comment)) {
			showToast("请输入评论内容");
			return;
		}

		if (rb.getRating() == 0) {
			showToast("评价未免太低了吧~！");
			return;
		}

		new OrderModelImpl().orderRate(order_id, rb.getRating(), comment, apt, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				// 这里借用支付接口回去刷新
//				EventBus.getDefault().post(new PaySuccessEventBusEntity());
//				close();
				finish();
			}

			@Override
			public void onError(int errorCode, String message) {
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					showToast(message);
					break;
				default:
					showToast(R.string.request_failed);
					break;
				}
			}
		});
	}
}
