package com.ysp.houge.ui.me.meinfo.auth;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.IAuthModel;
import com.ysp.houge.model.IFileUploadModel;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.eventbus.SubmidAuthEvnetBusEntity;
import com.ysp.houge.model.impl.AuthModelImpl;
import com.ysp.houge.model.impl.FileUploadModelImpl;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.image.selector.MultiImageSelectorActivity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CompanyAuthFragment extends BaseFragment implements OnClickListener, OnYesOrNoDialogClickListener {

	private int index = -1;
	private ClearEditText cetCorporationName, cetLegalName;
	private String corporationName, legalName;
	private ImageView ivTradingCertificate, ivLegalHeadCard, ivLegalHeadCardFront, ivLegalHeadCardVerso;

	private SparseArray<String> picPaths;
	private List<ImageView> ivs;
	private List<String> list;

	private YesOrNoDialogEntity dialogEntity;
	private YesOrNoDialog dialog;
	private IFileUploadModel iFileUploadModel;
	private IAuthModel iAuthModel;

	@Override
	protected int getContentView() {
		return R.layout.fragment_company_auth;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		cetCorporationName = (ClearEditText) view.findViewById(R.id.cet_corporation_name);
		cetLegalName = (ClearEditText) view.findViewById(R.id.cet_legal_name);

		ivTradingCertificate = (ImageView) view.findViewById(R.id.iv_trading_certificate);
		ivLegalHeadCard = (ImageView) view.findViewById(R.id.iv_legal_head_card);
		ivLegalHeadCardFront = (ImageView) view.findViewById(R.id.iv_legal_head_card_front);
		ivLegalHeadCardVerso = (ImageView) view.findViewById(R.id.iv_legal_head_card_verso);

		ivs = new ArrayList<ImageView>();
		ivs.add(ivTradingCertificate);
		ivs.add(ivLegalHeadCard);
		ivs.add(ivLegalHeadCardFront);
		ivs.add(ivLegalHeadCardVerso);

		view.findViewById(R.id.rela_trading_certificate).setOnClickListener(this);
		view.findViewById(R.id.rela_legal_head_card).setOnClickListener(this);
		view.findViewById(R.id.rela_legal_head_card_front).setOnClickListener(this);
		view.findViewById(R.id.rela_legal_head_card_verso).setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		picPaths = new SparseArray<String>();
		iFileUploadModel = new FileUploadModelImpl();
		iAuthModel = new AuthModelImpl();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_trading_certificate:
		case R.id.rela_trading_certificate:
			index = 0;
			break;
		case R.id.iv_legal_head_card:
		case R.id.rela_legal_head_card:
			index = 1;
			break;

		case R.id.iv_legal_head_card_front:
		case R.id.rela_legal_head_card_front:
			index = 2;
			break;

		case R.id.iv_legal_head_card_verso:
		case R.id.rela_legal_head_card_verso:
			index = 3;
			break;
		default:
			break;
		}

		Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);

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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_CANCELED) {
			LogUtil.setLogWithE("onActivityResult", "Canceled Avatar Capture.");
			return;
		}

		if (requestCode == 10002) {
			if (resultCode == Activity.RESULT_OK) {
				List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
				if (null == path || path.isEmpty()) {
					return;
				} else {
					String str = "file://" + path.get(0);
					picPaths.append(index, path.get(0));
					MyApplication.getInstance().getImageLoaderManager().loadNormalImage(ivs.get(index), str,
							LoadImageType.NormalImage);
					ivs.get(index).setVisibility(View.VISIBLE);
				}
			}
		}
	}

	/** 接收提交请求的 消息 */
	public void onEventMainThread(SubmidAuthEvnetBusEntity busEntity) {
		if (isVisible() && busEntity.getPageIndex() == 2 && checkSubmit()) {
			// 判断是否已经是企业认证
			String auth = MyApplication.getInstance().getUserInfo().verfied;
			if (!TextUtils.isEmpty(auth) && TextUtils.equals(auth, "3")) {
				creteSureAuthDialog();
			} else {
				loadImge();
			}
		}
	}

	private void creteSureAuthDialog() {
		if (null == dialogEntity) {
			dialogEntity = new YesOrNoDialogEntity("您已经通过企业认证，是否覆盖认证信息", "覆盖可能导致审核不通过！！！", getString(R.string.cancel),
					getString(R.string.sure));
		}

		if (null == dialog) {
			dialog = new YesOrNoDialog(getActivity(), dialogEntity, this);
		}

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	private boolean checkSubmit() {
		corporationName = cetCorporationName.getText().toString().trim();
		if (TextUtils.isEmpty(corporationName)) {
			showToast("请输入公司名称");
			return false;
		}

		if (TextUtils.isEmpty(picPaths.get(0))) {
			showToast("请添加营业执照副本");
			return false;
		}

		legalName = cetLegalName.getText().toString().trim();
		if (TextUtils.isEmpty(legalName)) {
			showToast("请输入法人姓名");
			return false;
		}

		if (TextUtils.isEmpty(picPaths.get(1))) {
			showToast("请添加法人手持身份证照片");
			return false;
		}

		if (TextUtils.isEmpty(picPaths.get(2))) {
			showToast("请添加法人身份证正面照片");
			return false;
		}

		if (TextUtils.isEmpty(picPaths.get(3))) {
			showToast("请添加法人身份证反面照片");
			return false;
		}

		return true;
	}

	private void loadImge() {
		showProgress();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < picPaths.size(); i++) {
			list.add(picPaths.get(i));
		}
		iFileUploadModel.uploadMultiFile(list, "path", "auth", new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				hideProgress();
				if (data != null && data instanceof List<?>) {
					CompanyAuthFragment.this.list = (List<String>) data;
					// 成功之后拿着 图片数组去请求认证接口
					requestAuth(corporationName, legalName);
				} else {
					showToast("图片上传失败!~");
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				hideProgress();
				switch (errorCode) {
				case ResponseCode.TIP_ERROR:
					showToast(message);
					break;

				default:
					showToast("图片上传失败!~");
					break;
				}
			}
		});
	}

	private void requestAuth(String std_name, String name) {
		if (list.size() < 4) {
			showToast("图片数量参数错误");
			return;
		}
		showProgress();
		iAuthModel.companyAuth(std_name, name, list.get(0), list.get(1), list.get(2), list.get(3),
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						hideProgress();
						showToast("提交成功");
						getActivity().finish();
					}

					@Override
					public void onError(int errorCode, String message) {
						hideProgress();
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

	@Override
	public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
		switch (yesOrNoType) {
		case BtnOk:
			loadImge();
			break;

		default:
			break;
		}
	}
}
