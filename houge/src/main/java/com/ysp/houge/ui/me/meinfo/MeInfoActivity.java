package com.ysp.houge.ui.me.meinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.HougeProgressDialog;
import com.ysp.houge.model.entity.eventbus.MeReviseEventBusEntity;
import com.ysp.houge.presenter.IMeInfoPresenter;
import com.ysp.houge.presenter.impl.MeInfoPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IMeInfoPageView;
import com.ysp.houge.ui.me.meinfo.auth.MeAuthenticationActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.image.selector.MultiImageSelectorActivity;

import java.util.List;

/**
 * @描述: 个人详情页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午1:48:28
 * @version 1.0
 */
public class MeInfoActivity extends BaseFragmentActivity implements OnClickListener, IMeInfoPageView {

	/** 头像显示 */
	private ImageView iv_face;
	/** 昵称显示 */
	private TextView tv_nickName;
	/** 性别显示 */
	private TextView tv_sex;
	/** 实名认证显示 */
	private TextView tv_auth;
	/** 学生认证显示 */
	private TextView tv_student_auth;
	/** 企业认证显示 */
	private TextView tv_company_auth;

	private IMeInfoPresenter iMeInfoPresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, MeInfoActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_info);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.account_info));
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		findViewById(R.id.rl_face).setOnClickListener(this);
		iv_face = (ImageView) findViewById(R.id.iv_face);

		findViewById(R.id.rl_nickName).setOnClickListener(this);
		tv_nickName = (TextView) findViewById(R.id.tv_nickName);

		findViewById(R.id.rl_sex).setOnClickListener(this);
		tv_sex = (TextView) findViewById(R.id.tv_sex);

		findViewById(R.id.rl_auth).setOnClickListener(this);
		tv_auth = (TextView) findViewById(R.id.tv_auth);

		findViewById(R.id.rl_student_auth).setOnClickListener(this);
		tv_student_auth = (TextView) findViewById(R.id.tv_student_auth);

		findViewById(R.id.rl_company_auth).setOnClickListener(this);
		tv_company_auth = (TextView) findViewById(R.id.tv_company_auth);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iMeInfoPresenter = new MeInfoPagePresenter(this);
		iMeInfoPresenter.getMeInfo();
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

    public void onEventMainThread(MeReviseEventBusEntity meReviseEventBusEntity) {
		if (meReviseEventBusEntity != null) {
			switch (meReviseEventBusEntity.getMeReviseType()) {
			case ReviseNickName:
				updateNickName((String) meReviseEventBusEntity.getObject());
				break;
			case ReviseSex:
				updateSex((Integer) meReviseEventBusEntity.getObject());
				break;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED) {
			LogUtil.setLogWithE("onActivityResult", "Canceled Avatar Capture.");
			return;
		}

		if (requestCode == 10002) {
			if (resultCode == RESULT_OK) {
				List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
				if (null == path || path.isEmpty()) {
					return;
				} else {
					iMeInfoPresenter.updateAvatar(path.get(0));
				}
			}
		}
	}

	/** 弹出选择图片对话框 */
	private void createChoosePicture() {
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
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	/** 更新头像 */
	@Override
	public void updateAvatar(String avatarUrl) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(iv_face, avatarUrl,
				LoadImageType.RoundAvatar);
	}

	/** 更新昵称 */
	@Override
	public void updateNickName(String nickname) {
		if (TextUtils.isEmpty(nickname)) {
			tv_nickName.setText("");
		} else {
			tv_nickName.setText(nickname);
		}
	}

	/** 更新性别 */
	@Override
	public void updateSex(int sex) {
		switch (sex) {
		case 1:
			tv_sex.setText(R.string.male);
			break;
		case 2:
			tv_sex.setText(R.string.female);
			break;
		default:
			tv_sex.setText("未知");
			break;
		}
	}

	@Override
	public boolean isProgressDialogShow() {
		return HougeProgressDialog.isShow();
	}

	@Override
	public void showChoosePictureDialog() {
		check();
	}

	public void check(){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
					100);
		}else {
			//已赋予过权限
			// TODO 创建选择图片对话框
			createChoosePicture();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==100){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 允许
				// TODO 创建选择图片对话框
				createChoosePicture();
			} else {
					//无权限
					showToast("请打开对应的权限，否者会导致App无法正常运行！");
			}
		}
	}

	@Override
	public void jumpToNickNamePage(String nickname) {
		Bundle bundle = new Bundle();
		bundle.putString(MeInfoNicknameActivity.NICKNAME, nickname);
		MeInfoNicknameActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToSexPage(int sex) {
		Bundle bundle = new Bundle();
		bundle.putInt(MeInfoSexActivity.SEX, sex);
		MeInfoSexActivity.jumpIn(this, bundle);
	}

	@Override
	public void updateAuth(String authentication) {
		tv_auth.setText(authentication);
	}

	@Override
	public void updateStudentAuth(String authentication) {
		tv_student_auth.setText(authentication);
	}

	@Override
	public void updateCompanyAuth(String authentication) {
		tv_company_auth.setText(authentication);
	}

	@Override
	public void jumpToAuth(int index) {
		Bundle bundle = new Bundle();
		bundle.putInt(MeAuthenticationActivity.CURRENT_INDEX_KEY, index);
		MeAuthenticationActivity.jumpIn(this, bundle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 头像
		case R.id.rl_face:
			iMeInfoPresenter.clickAvatarLayout();
			break;

		// 昵称
		case R.id.rl_nickName:
			iMeInfoPresenter.clickNicknameLayout();
			break;

		// 性别
		case R.id.rl_sex:
			iMeInfoPresenter.clickSexLayout();
			break;

		// 实名认证
		case R.id.rl_auth:
			if (tv_auth.getText().equals("已认证")) {
				return;
			}
			iMeInfoPresenter.clickAuthLayout(1);
			break;
		// 学生认证
		case R.id.rl_student_auth:
			if (tv_student_auth.getText().equals("已认证")) {
				return;
			}
			iMeInfoPresenter.clickAuthLayout(0);
			break;
		// 企业认证
		case R.id.rl_company_auth:
			if (tv_company_auth.getText().equals("已认证")) {
				return;
			}
			iMeInfoPresenter.clickAuthLayout(2);
			break;

		default:
			break;
		}
	}
}
