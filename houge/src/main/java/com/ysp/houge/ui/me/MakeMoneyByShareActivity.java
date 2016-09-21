package com.ysp.houge.ui.me;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.MakeMoneyByShareEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.PictureUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author it_hu
 * 
 *         分享赚钱
 *
 */
public class MakeMoneyByShareActivity extends BaseFragmentActivity {
	private LinearLayout line;
	private TextView tvInveteCode;
	private ImageView ivAndroidDownloading;
	private ImageView ivIOSDownloading;
	private MakeMoneyByShareEntity shareEntity;

    private int QRCodeSize;
	private IUserInfoModel iUserInfoModel;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, MakeMoneyByShareActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_make_by_share);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(R.string.share_to_make_money);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
        line = (LinearLayout) findViewById(R.id.line_top_outside);
		tvInveteCode = (TextView) findViewById(R.id.tv_invest_code);
        ivAndroidDownloading = (ImageView) findViewById(R.id.iv_android_down);
        ivIOSDownloading = (ImageView) findViewById(R.id.iv_ios_down);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
                line.setBackgroundColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;
            case MyApplication.LOG_STATUS_SELLER:
                line.setBackgroundColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
        }

        //计算二维码宽高度
        QRCodeSize = ivAndroidDownloading.getLayoutParams().height - SizeUtils.dip2px(this,10);
        ivAndroidDownloading.getLayoutParams().width = QRCodeSize;
        ivAndroidDownloading.getLayoutParams().height = QRCodeSize;
        ivIOSDownloading.getLayoutParams().width = QRCodeSize;
        ivIOSDownloading.getLayoutParams().height = QRCodeSize;

		iUserInfoModel = new UserInfoModelImpl();
		loadShareInfo();
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

    private void loadShareInfo() {
		showProgress();
		iUserInfoModel.getShareInfo(new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				hideProgress();
				if (null != data && data instanceof MakeMoneyByShareEntity) {
					shareEntity = (MakeMoneyByShareEntity) data;
				} else {
					shareEntity = new MakeMoneyByShareEntity("", "","");
				}
				showInveteCode(shareEntity.getInvite_id());
                showIOSDownloading(shareEntity.getDownload_url());
                showAndroidDownloading(shareEntity.getShare_url());
			}

			@Override
			public void onError(int errorCode, String message) {
				hideProgress();
				shareEntity = new MakeMoneyByShareEntity("", "","");
				showInveteCode(shareEntity.getInvite_id());
                showIOSDownloading(shareEntity.getDownload_url());
                showAndroidDownloading(shareEntity.getShare_url());
			}
		});
	}

	private void showInveteCode(String investeCodel) {
		if (!TextUtils.isEmpty(investeCodel)) {
			tvInveteCode.setText("您的推荐码为 " + investeCodel);
		} else {
			tvInveteCode.setText("推荐码获取失败，请关闭页面后重试");
		}
	}

	private void showIOSDownloading(String downLoading) {
		if (!TextUtils.isEmpty(downLoading)) {
			Bitmap bitmap = PictureUtil.createQRImage(this, downLoading,QRCodeSize);
			if (bitmap != null) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					ivIOSDownloading.setBackground(new BitmapDrawable(bitmap));
				}
				return;
			}
		}
		ivIOSDownloading.setBackgroundResource(R.drawable.defaultpic);
	}

    private void showAndroidDownloading(String downLoading) {
		if (!TextUtils.isEmpty(downLoading)) {
			Bitmap bitmap = PictureUtil.createQRImage(this, downLoading,QRCodeSize);
			if (bitmap != null) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					ivAndroidDownloading.setBackground(new BitmapDrawable(bitmap));
				}
				return;
			}
		}
        ivAndroidDownloading.setBackgroundResource(R.drawable.defaultpic);
	}
}
