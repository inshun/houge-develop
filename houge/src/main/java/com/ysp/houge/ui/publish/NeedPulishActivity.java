package com.ysp.houge.ui.publish;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.RecordDialog;
import com.ysp.houge.dialog.ServiceTimeDialog;
import com.ysp.houge.dialog.ServiceTimeDialog.OnChooseFinis;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.presenter.INeedPulishPresenter;
import com.ysp.houge.presenter.impl.NeedPulishPresenter;
import com.ysp.houge.ui.HomeActivity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.INeedPulishPagerView;
import com.ysp.houge.ui.me.address.AddressManagerActivity;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.image.selector.MultiImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 买家发布页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月27日下午5:12:55
 * @version 1.0
 */
public class NeedPulishActivity extends BaseFragmentActivity
		implements INeedPulishPagerView, OnClickListener, OnLongClickListener {
	/** 标题（召唤一个什么样的人） */
	private EditText mSummonTitle;
	/** 详述，详细描述 */
	private EditText mSummonExpand;
	/** 单价 */
	private EditText mPayNum;
	/** 服务时间 */
	private TextView mServerTime;
	/** 联系方式 */
	private TextView mContactWay;
	/** 下一步 */
	private TextView mNextStep;
	/** 图片以及语音 */
	private ViewStub mVoiceAndImg;
	private ImageView vsRecode, vsImg1, vsImg2, vsImg3;
	/** 选择服务时间 */
	private LinearLayout mServerDateLayout;
	/** 选择服务地点 */
	private LinearLayout mContactWayLayout;
	/** 收起 */
	private ImageButton mPickUp;
    /**所有布局的外层布局*/
    private LinearLayout lineOutSideLayout;
	/** Presenter层抽象类对象 */
	private INeedPulishPresenter iNeedPulishPresenter;

	private int srceenWidth;
	private ServiceTimeDialog dialog;
	private Bundle tiaojian;

	public static void JumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, NeedPulishActivity.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_buyer_pulish);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
			tiaojian = getIntent().getExtras();
		if(tiaojian != null){
			if (tiaojian.getInt("TAG") == 2) {
				actionBar.setLeftEnable(true);
				actionBar.setLeftIcon(R.drawable.icon_canle_push_need);
				actionBar.setRightText("完成");
				actionBar.setLeftClickListenner(this);
				actionBar.setRightClickListenner(this);
			} else {
				actionBar.setLeftEnable(false);
			}
		}
		actionBar.setTitle("免费发布");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		mNextStep = (TextView) findViewById(R.id.tv_next_step);


		mServerDateLayout = (LinearLayout) findViewById(R.id.line_server_date);
		mServerDateLayout.setOnClickListener(this);
		mContactWayLayout = (LinearLayout) findViewById(R.id.line_contact_way);
		mContactWayLayout.setOnClickListener(this);

		mSummonTitle = (EditText) findViewById(R.id.et_summon_title);
		mSummonExpand = (EditText) findViewById(R.id.et_summon_expand);
		mPayNum = (EditText) findViewById(R.id.et_pay_num);

		mPickUp = (ImageButton) findViewById(R.id.ibtn_pick);


		mServerTime = (TextView) findViewById(R.id.tv_server_time);
		mContactWay = (TextView) findViewById(R.id.tv_contact_way);
        lineOutSideLayout = (LinearLayout)findViewById(R.id.line_out_side_layout);
        lineOutSideLayout.setOnClickListener(this);

		mVoiceAndImg = (ViewStub) findViewById(R.id.vs_voice_and_img);
        View view = mVoiceAndImg.inflate();
        vsRecode = (ImageView) view.findViewById(R.id.iv_recode);
        vsImg1 = (ImageView) view.findViewById(R.id.iv_img_one);
        vsImg2 = (ImageView) view.findViewById(R.id.iv_img_two);
        vsImg3 = (ImageView) view.findViewById(R.id.iv_img_three);

		tiaojian = getIntent().getExtras();
		if(tiaojian != null) {
			if (tiaojian.getInt("TAG") == 2) {
				mPickUp.setVisibility(View.VISIBLE);
				mNextStep.setVisibility(View.VISIBLE);
			}
		}
		mPickUp.setOnClickListener(this);
		mNextStep.setOnClickListener(this);


        //vsRecode.setVisibility(View.VISIBLE);
        vsRecode.setVisibility(View.GONE);
        vsImg1.setVisibility(View.VISIBLE);
        vsRecode.setOnClickListener(this);
        vsImg1.setOnClickListener(this);
        vsImg2.setOnClickListener(this);
        vsImg3.setOnClickListener(this);
        vsRecode.setOnLongClickListener(this);
        vsImg1.setOnLongClickListener(this);
        vsImg2.setOnLongClickListener(this);
        vsImg3.setOnLongClickListener(this);

    }

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNeedPulishPresenter = new NeedPulishPresenter(this);
		srceenWidth = SizeUtils.getScreenWidth(this);
        iNeedPulishPresenter.loadDefaultAddress();
        showVoiceImg("");
        showAddImgImg(null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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
//TODO  返回键屏蔽
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (tiaojian != null) {
					if (tiaojian.getInt("TAG") == 2) {
						HomeActivity.jumpIn(this, null);
						pickUp();
						AppManager.getAppManager().removeActivity(this);
					}
				}else {
					pickUp();
				}
				break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.iv_recode:
			iNeedPulishPresenter.deleteRecord();
			break;
		case R.id.iv_img_one:
			iNeedPulishPresenter.deleteImg(0);
			break;
		case R.id.iv_img_two:
			iNeedPulishPresenter.deleteImg(1);
			break;
		case R.id.iv_img_three:
			iNeedPulishPresenter.deleteImg(2);
			break;
		default:
			LogUtil.setLogWithE("SummonActivity", "LongClick Error View ID.");
			break;
		}
		return true;
	}

	@Override
	public void showRecordDialog(RecordDialog.RecordListener listener) {
		RecordDialog dialog = new RecordDialog(this, listener);
		dialog.show();
	}

	@Override
	public void showPhotoDialog() {
		check();
	}

	@Override
	public void showServerTimeDialog() {
		if (dialog == null) {
			dialog = new ServiceTimeDialog(this, srceenWidth);
		}
		dialog.setOnChooseFinis(new OnChooseFinis() {

			@Override
			public void onFinish() {
				int date = dialog.date;
				String time = dialog.time;
				iNeedPulishPresenter.chooseTimeFinish(time, date);
			}
		});
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	@Override
	public void showServerTime(String date) {
		mServerTime.setText(date);
	}

	@Override
	public void jumpToChooseContactWay() {
		Bundle bundle = new Bundle();
		bundle.putInt(AddressManagerActivity.ENTER_PAGE_KEY, AddressManagerActivity.ENTER_PAGE_CHOOSE_ADDRESS);
		AddressManagerActivity.jumpIn(this, bundle);
	}

	@Override
	public void showContactWay(AddressEntity entity) {
		if (!(entity.mobile == null || entity.real_name == null || entity.street == null)) {
			StringBuilder sb = new StringBuilder();
			sb.append(entity.real_name);
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append(entity.mobile);
			sb.append("<br/>");
			// 这里对街道字段做截取
			if (entity.street.indexOf(",") > 0) {
				entity.street = entity.street.replaceFirst(",", "");
			}
			sb.append(entity.street);
			mContactWay.setText(Html.fromHtml(sb.toString()));
		}
	}

	@Override
	public void pickUp() {
		this.finish();
		overridePendingTransition(R.anim.activity_stay, R.anim.slide_out_to_bottom);
	}


    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 服务时间
		case R.id.line_server_date:
			iNeedPulishPresenter.chooseServerTime();
			break;

		// 联系方式
		case R.id.line_contact_way:
			iNeedPulishPresenter.chooseContactWay();
			break;

		// 下一步
		case R.id.menu_head_right:
		case R.id.tv_next_step:
            if (DoubleClickUtil.isFastClick())
                return;
			iNeedPulishPresenter.nextStup(mSummonTitle.getText().toString().trim(),
					mSummonExpand.getText().toString().trim(), mPayNum.getText().toString().trim());
			break;

		//返回（第一次进来返回， 不能会退到之前的页面，不然就到了登录页面）
			case R.id.menu_head_left:
				if(tiaojian != null) {
					if (tiaojian.getInt("TAG") == 2) {
						HomeActivity.jumpIn(this, null);
						pickUp();
						AppManager.getAppManager().removeActivity(this);
					}
				} else {
					pickUp();
				}
				break;

		// 收起
		case R.id.ibtn_pick:
			iNeedPulishPresenter.pickUp();
			break;

		// 录音试听
		case R.id.iv_recode:
			iNeedPulishPresenter.playRecord();
			break;
		// 添加图片操作
		case R.id.iv_img_one:
			if (((NeedPulishPresenter) iNeedPulishPresenter).getPhotoPathSize() == 0) {
				showPhotoDialog();
			}
			break;
		// 添加图片操作
		case R.id.iv_img_two:
			if (((NeedPulishPresenter) iNeedPulishPresenter).getPhotoPathSize() == 1) {
				showPhotoDialog();
			}
			break;
		case R.id.iv_img_three:
			if (((NeedPulishPresenter) iNeedPulishPresenter).getPhotoPathSize() == 2) {
				showPhotoDialog();
			}
			break;
		default:
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(
                    InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
			LogUtil.setLogWithE("SummonActivity", "Error View ID.");
			break;
		}
	}

	private void createChoosePicDialog() {
		int size = 3 - ((NeedPulishPresenter) iNeedPulishPresenter).getPhotoPathSize();
		Intent intent = new Intent(this, MultiImageSelectorActivity.class);

		// 是否显示调用相机拍照
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

		// 最大图片选择数量
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, size);

		// 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者
		// 多选/MultiImageSelectorActivity.MODE_MULTI)
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

		startActivityForResult(intent, 10002);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
					for (int i = 0; i < path.size(); i++) {
						iNeedPulishPresenter.choosePhoto(path.get(i));
					}
				}
			}
		}
	}

	@Override
	public void showVoiceImg(String record) {
        if (TextUtils.isEmpty(record)){
            //设置添加录音的图片
            vsRecode.setImageResource(R.drawable.add_record);
            //vsRecode.setVisibility(View.VISIBLE);
        }else {
		    // 设置未播放图片
		    vsRecode.setImageResource(R.drawable.avatar);
		    //vsRecode.setVisibility(View.VISIBLE);
        }
	}

	@Override
	public void changeVoiceStatus(boolean isPlaying) {
		if (isPlaying) {
			// 设置播放图片
			vsRecode.setImageResource(R.drawable.play_record);
		} else {
			// 设置未播放图片
			vsRecode.setImageResource(R.drawable.avatar);
		}
	}

	@Override
	public void addImg(ArrayList<String> list) {
		switch (list == null ? 0 : list.size()) {
		case 1:
			loadImg(list.get(0), vsImg1);
			showAddImgImg(vsImg2);
			vsImg3.setImageDrawable(null);
			break;
		case 2:
			loadImg(list.get(0), vsImg1);
			loadImg(list.get(1), vsImg2);
			showAddImgImg(vsImg3);
			break;
		case 3:
			loadImg(list.get(0), vsImg1);
			loadImg(list.get(1), vsImg2);
			loadImg(list.get(2), vsImg3);
			break;
		default:
			if (vsRecode.getVisibility() == View.VISIBLE) {
				showAddImgImg(vsImg1);
			} else {
				vsImg1.setImageDrawable(null);
			}
			vsImg2.setImageDrawable(null);
			vsImg3.setImageDrawable(null);
			break;
		}
	}

	@Override
	public void showRecordAndImg() {
		mVoiceAndImg.setVisibility(View.VISIBLE);
		// 设置未播放图片
		vsRecode.setImageResource(R.drawable.avatar);
		//vsRecode.setVisibility(View.VISIBLE);
	}

	private void loadImg(String path, ImageView view) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, "file://" + path,
				LoadImageType.NormalImage);
	}

	@Override
	public void showAddImgImg(ImageView view) {
		if (view == null) {
			vsImg1.setImageResource(R.drawable.add_img);
			return;
		}
		view.setImageResource(R.drawable.add_img);
	}

	@Override
	public void jumpToHomeActivity() {
		HomeActivity.jumpIn(this, null);
		pickUp();
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
			createChoosePicDialog();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==100){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 允许
				// TODO 创建选择图片对话框
				createChoosePicDialog();
			} else {
					//无权限
					showToast("请打开对应的权限，否者会导致App无法正常运行！");
			}
		}
	}


	/** 接收地址列表页面数据 */
	public void onEventMainThread(AddressEntity an) {
		iNeedPulishPresenter.chooseAddressFinish(an);
	}
}
