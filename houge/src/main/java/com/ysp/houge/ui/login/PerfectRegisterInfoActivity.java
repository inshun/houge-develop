package com.ysp.houge.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IPerfectRegisterInfoPresenter;
import com.ysp.houge.presenter.impl.PerfectRegisterInfoPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IPerfectRegisterInfoPageView;
import com.ysp.houge.ui.recommend.WorkTypeListActivity;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.image.selector.MultiImageSelectorActivity;

import java.util.List;

/**
 * 描述： 完善注册信息
 *
 * @ClassName: PerfectRegisterInfoActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月19日 上午10:23:24
 * 
 *        版本: 1.0
 */
public class PerfectRegisterInfoActivity extends BaseFragmentActivity
		implements OnClickListener, IPerfectRegisterInfoPageView {
	private EditText mNameEdit; // 名称
	private EditText mRecommendCodeEdit; // 推荐码
	private ImageView mAvatar; // 头像
	private ImageView mSex; // 性别
	private IPerfectRegisterInfoPresenter iPerfectRegisterInfoPresenter;
	private Drawable Y, N;
	private TextView nextStep, serviceDeal;
	private boolean agreeDeal = true;

	private TextView me_sure_user_agreement;
	private RelativeLayout me_sure;
	private TextView tv_service_deal;
	private Bundle bundle;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, PerfectRegisterInfoActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_mobile_register);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.register));
		//actionBar.setLeftEnable(false);
		//actionBar.setLeftText(getString(R.string.back));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mNameEdit = (EditText) findViewById(R.id.mNameEdit);
		mRecommendCodeEdit = (EditText) findViewById(R.id.mRecommentCodeEdit);
		mAvatar = (ImageView) findViewById(R.id.mAvatar);
		mSex = (ImageView) findViewById(R.id.iv_sex);
		tv_service_deal = (TextView) findViewById(R.id.tv_service_deal);
		me_sure_user_agreement = (TextView) findViewById(R.id.me_sure_user_agreement);
		me_sure = (RelativeLayout) findViewById(R.id.me_sure);

		findViewById(R.id.btn_i_skill).setOnClickListener(this);
		findViewById(R.id.btn_i_need).setOnClickListener(this);
		mAvatar.setOnClickListener(this);
		mSex.setOnClickListener(this);
		tv_service_deal.setOnClickListener(this);
		me_sure.setOnClickListener(this);
		me_sure_user_agreement.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
        iPerfectRegisterInfoPresenter = new PerfectRegisterInfoPresenter(this);
        //让控制层取得上一个页面的数据
        iPerfectRegisterInfoPresenter.getDataFromFormaPage(getIntent().getExtras());
		changeCheckStatus(agreeDeal);
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
	public void choosePicture() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者
        // 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, 10002);
	}

	@Override
	public void setAvatarAndSex(String avatarUrl,int sex) {
		switch (sex) {
		case UserInfoEntity.SEX_MAL:
            if (!TextUtils.isEmpty(avatarUrl))
                mAvatar.setBackgroundResource(R.drawable.shapa_sex_mal);
			mSex.setImageResource(R.drawable.icon_sex_male);
			break;
		case UserInfoEntity.SEX_FEMAL:
            if (!TextUtils.isEmpty(avatarUrl))
                mAvatar.setBackgroundResource(R.drawable.shapa_sex_femal);
			mSex.setImageResource(R.drawable.icon_sex_female);
			break;
		}

        if (!TextUtils.isEmpty(avatarUrl))
		    MyApplication.getInstance().getImageLoaderManager().loadNormalImage(mAvatar, avatarUrl,
                LoadImageType.RoundAvatar);
	}

	@Override
	public void jumpToHomePage(int uid) {
        //销毁注册页面
		AppManager.getAppManager().finishActivity(RegisterActivity.class);
        //销毁当前页面
		AppManager.getAppManager().finishActivity(this);
        Bundle bundle = new Bundle();
        //标记代表注册直接进入的首页，在首页有处理
        //告诉主页是登录进入
        //2表示注册进入主页
        bundle.putInt("TAG",uid);
		WorkTypeListActivity.jumpIn(this, bundle);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        //接收选择图片的返回
		if (requestCode == 10002) {
			if (resultCode == RESULT_OK) {
				List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
				if (null == path || path.isEmpty()) {
					return;
				} else {
                    iPerfectRegisterInfoPresenter.choosePictureBack(path.get(0));
				}
			}
		}
	}

	private void changeCheckStatus(boolean isCheck) {
		if (isCheck) {
			if (null == Y) {
				switch (MyApplication.getInstance().getLoginStaus()){
					case MyApplication.LOG_STATUS_BUYER:
						Y = getResources().getDrawable(R.drawable.icon_sel_orange);
						break;
					case MyApplication.LOG_STATUS_SELLER:
						Y = getResources().getDrawable(R.drawable.icon_sel_blue);
						break;
				}
				Y.setBounds(0, 0, Y.getMinimumWidth(), Y.getMinimumHeight());
			}
			tv_service_deal.setCompoundDrawables(Y, null, null, null);
		} else {
			if (null == N) {
				N = getResources().getDrawable(R.drawable.icon_def);
				N.setBounds(0, 0, N.getMinimumWidth(), N.getMinimumHeight());
			}
			tv_service_deal.setCompoundDrawables(N, null, null, null);
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注册
		case R.id.btn_i_skill:
            //判断双击，注册属于重要地方
            // 判断双击可以防止重复注册发生
            if (DoubleClickUtil.isFastClick())
                return;

			if(agreeDeal == true){

				iPerfectRegisterInfoPresenter.clickRegisterBtn(mNameEdit.getText().toString().trim(),
						mRecommendCodeEdit.getText().toString().trim(), 2);
			}else{
				showToast("请同意用户协议");
			}
			break;
		case R.id.btn_i_need:
			if (DoubleClickUtil.isFastClick())
				return;
			if(agreeDeal == true){

				iPerfectRegisterInfoPresenter.clickRegisterBtn(mNameEdit.getText().toString().trim(),
						mRecommendCodeEdit.getText().toString().trim(), 3);
			}else{
				showToast("请同意用户协议");
			}
			break;
		// 选择头像
		case R.id.mAvatar:
            iPerfectRegisterInfoPresenter.clickAvatarLayout();
			break;

		// 更换性别
		case R.id.iv_sex:

            iPerfectRegisterInfoPresenter.changeSex();
			break;

			case R.id.me_sure_user_agreement:
				RegisterAgreementWebView.jumpIn(this);
				break;
			case R.id.me_sure:
			case R.id.tv_service_deal:
				agreeDeal = agreeDeal ? false : true;
				changeCheckStatus(agreeDeal);

		default:
			break;
		}
	}

}
