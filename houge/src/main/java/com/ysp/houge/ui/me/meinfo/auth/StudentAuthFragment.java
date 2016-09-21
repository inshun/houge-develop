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

public class StudentAuthFragment extends BaseFragment implements OnClickListener,OnYesOrNoDialogClickListener {
	private int index = -1;

	private SparseArray<String> picPaths;
	private List<String> list;
	private List<ImageView> ivs;

	private ClearEditText cetRelaName, cetCardNum, cetReadSchool, cetXueXin;
	private String relaName, cardNum, readSchool, xueXin;
	private ImageView ivHandCard, ivCardFront, ivCardVerso, ivCertificateOfDegree, ivWeixin;

	private YesOrNoDialogEntity dialogEntity;
	private YesOrNoDialog dialog;

	private IFileUploadModel iFileUploadModel;
	private IAuthModel iAuthModel;

	@Override
	protected int getContentView() {
		return R.layout.fragment_student_auth;
	}

	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		view.findViewById(R.id.rela_hand_card).setOnClickListener(this);
		view.findViewById(R.id.rela_card_front).setOnClickListener(this);
		view.findViewById(R.id.rela_card_verso).setOnClickListener(this);
		view.findViewById(R.id.rela_certificate_of_degree).setOnClickListener(this);
		view.findViewById(R.id.rela_xuexin).setOnClickListener(this);

		ivHandCard = (ImageView) view.findViewById(R.id.iv_head_card);
		ivCardFront = (ImageView) view.findViewById(R.id.iv_card_front);
		ivCardVerso = (ImageView) view.findViewById(R.id.iv_card_verso);
		ivCertificateOfDegree = (ImageView) view.findViewById(R.id.iv_certificate_of_degree);
		ivWeixin = (ImageView) view.findViewById(R.id.iv_xuexin);

		ivHandCard.setOnClickListener(this);
		ivCardFront.setOnClickListener(this);
		ivCardVerso.setOnClickListener(this);
		ivCertificateOfDegree.setOnClickListener(this);
		ivWeixin.setOnClickListener(this);

		ivs = new ArrayList<ImageView>();
		ivs.add(ivHandCard);
		ivs.add(ivCardFront);
		ivs.add(ivCardVerso);
		ivs.add(ivCertificateOfDegree);
		ivs.add(ivWeixin);

		cetRelaName = (ClearEditText) view.findViewById(R.id.cet_real_name);
		cetCardNum = (ClearEditText) view.findViewById(R.id.cet_card_num);
		cetReadSchool = (ClearEditText) view.findViewById(R.id.cet_read_school);
		cetXueXin = (ClearEditText) view.findViewById(R.id.cet_education);
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
		case R.id.iv_head_card:
		case R.id.rela_hand_card:
			index = 0;
			break;
		case R.id.iv_card_front:
		case R.id.rela_card_front:
			index = 1;
			break;
		case R.id.iv_card_verso:
		case R.id.rela_card_verso:
			index = 2;
			break;
		case R.id.iv_certificate_of_degree:
		case R.id.rela_certificate_of_degree:
			index = 3;
			break;
		case R.id.iv_xuexin:
		case R.id.rela_xuexin:
			index = 4;
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
		if (isVisible() && busEntity.getPageIndex() == 0 && checkSubmit()) {
			// 判断是否已经是学生认证
			String auth = MyApplication.getInstance().getUserInfo().verfied;
			if (!TextUtils.isEmpty(auth) && TextUtils.equals(auth, "1")) {
				creteSureAuthDialog();
			} else {
				loadImge();
			}
		}
	}

	private void creteSureAuthDialog() {
		if (null == dialogEntity) {
			dialogEntity = new YesOrNoDialogEntity("您已经通过学生认证，是否覆盖认证信息", "覆盖可能导致审核不通过！！！", getString(R.string.cancel),
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
		relaName = cetRelaName.getText().toString().trim();
		if (TextUtils.isEmpty(relaName)) {
			showToast("请输入真实姓名");
			return false;
		}

		cardNum = cetCardNum.getText().toString().trim();
		if (TextUtils.isEmpty(cardNum)) {
			showToast("请输入身份证号");
			return false;
		}

		if (cardNum.length() > 18) {
			showToast("身份证号为18位");
			return false;
		}

		if (TextUtils.isDigitsOnly(picPaths.get(0))) {
			showToast("请添加手持身份证照片");
			return false;
		}

		if (TextUtils.isDigitsOnly(picPaths.get(1))) {
			showToast("请添加身份证正面照片");
			return false;
		}

		if (TextUtils.isDigitsOnly(picPaths.get(2))) {
			showToast("请添加身份证反面照片");
			return false;
		}

		readSchool = cetReadSchool.getText().toString().trim();
		if (TextUtils.isEmpty(readSchool)) {
			showToast("请输入学校名称");
			return false;
		}

		xueXin = cetXueXin.getText().toString().trim();
		if (TextUtils.isEmpty(xueXin)) {
			showToast("请输入学历");
			return false;
		}

		if (TextUtils.isDigitsOnly(picPaths.get(3))) {
			showToast("请添加学位证照片");
			return false;
		}

		if (TextUtils.isDigitsOnly(picPaths.get(4))) {
			showToast("请添加学信网截图");
			return false;
		}

		return true;
	}

	private void loadImge() {
		showProgress("上传图片中，请稍后...");
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
					StudentAuthFragment.this.list = (List<String>) data;
					// 成功之后拿着 图片数组去请求认证接口
					requestAuth(relaName, cardNum, readSchool, xueXin);
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

	private void requestAuth(String name, String people_id, String school, String levle_school) {
		if (list.size() < 5) {
			showToast("图片数量参数错误");
			return;
		}
		showProgress();
		iAuthModel.studentAuth(name, people_id, school, levle_school, list.get(0), list.get(1), list.get(2),
				list.get(3), list.get(4), new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						hideProgress();
						showToast("提交成功，等待认证中");
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
