package com.ysp.houge.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.RecordDialog.RecordListener;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.IBuyerOrderModel;
import com.ysp.houge.model.IFileUploadModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.NeedEntity;
import com.ysp.houge.model.entity.bean.PictureEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.model.impl.BuyerOrderModelImpl;
import com.ysp.houge.model.impl.FileUploadModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.INeedPulishPresenter;
import com.ysp.houge.ui.iview.INeedPulishPagerView;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.FileUtil;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.voice.media.MyMediaPlayer;
import com.ysp.houge.utility.voice.media.MyMediaPlayer.onMediaPlayListener;
import com.ysp.houge.utility.voice.record.AudioRecorder;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @描述:召唤(买家发布)页面 Presenter层实现类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月27日下午6:43:46
 * @version 1.0
 */
@SuppressLint("SimpleDateFormat")
public class NeedPulishPresenter extends BasePresenter<INeedPulishPagerView>
		implements INeedPulishPresenter, onMediaPlayListener {
	private boolean isNetRecord = false;
	private ArrayList<String> photoPath; // 图像的路径集合
	private AudioRecorder recorder; // 录音工具类
	private MyMediaPlayer player; // 录音播放工具类
	private long recordTimeCount; // 录音时间记录
	private String recordPath; // 录音的路径
	private boolean isPlaying; // 录音正在播放
	private String serviceTimeShow; // 显示的服务时间
	private String serviceTimeUpload; // 上传的服务时间
	private NeedEntity needEntity;
	private AddressEntity addressEntity;

	private IAddressModel iAddressModel;
	private IFileUploadModel iFileUploadModel;
	private IBuyerOrderModel iBuyerOrderModel;

	/** 显示的时间格式对象 */
	private SimpleDateFormat sdfShow = new SimpleDateFormat("MM-dd");
	/** 上传的时间格式对象 */
	private SimpleDateFormat sdfUpload = new SimpleDateFormat("yyyy-MM-dd");

	public NeedPulishPresenter(INeedPulishPagerView view) {
		super(view);
		photoPath = new ArrayList<String>();
		addressEntity = new AddressEntity();
	}

	@Override
	public void initModel() {
		iAddressModel = new AddressModelImpl();
		iFileUploadModel = new FileUploadModelImpl();
		iBuyerOrderModel = new BuyerOrderModelImpl();
	}

	public int getPhotoPathSize() {
		return photoPath == null ? 0 : photoPath.size();
	}

	@Override
	public void loadDefaultAddress() {
		iAddressModel.getDefaultAddress(MyApplication.getInstance().getCurrentUid(),MyApplication.LOG_STATUS_BUYER, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (null != data && data instanceof AddressEntity) {
					addressEntity = (AddressEntity) data;
					mView.showContactWay(addressEntity);
				}
			}

			@Override
			public void onError(int errorCode, String message) {
                //加载错误了，不管
			}
		});
	}

	@Override
	public void onRecord() {
		if (!FileUtil.isSdcardExist()) {
			mView.showToast("未检测到sd卡,请确认!!!");
			return;
		}
		if (!TextUtils.isEmpty(recordPath)) {
			// 录音已存在
			return;
		}

		mView.showRecordDialog(new RecordListener() {

			@Override
			public void onTimeCountDown() {
				recorder.stop();
				recorder = null;
				recordPath = recorder.getFilePath();
				mView.showToast("录音结束");
				mView.addImg(photoPath);
				mView.showVoiceImg(recordPath);

				mView.showRecordAndImg();
				isNetRecord = false;
			}

			@Override
			public void onStartRecord() {
				recordPath = "";
				recordTimeCount = System.currentTimeMillis();
				if (recorder == null) {
					recorder = new AudioRecorder();
				}
				recorder.ready();
				recorder.start();
			}

			@Override
			public void onFinishRecord() {
				recorder.stop();
				// 录音时间必须大于一秒(这里没有判断录音权限获取失败时的文件大小，而是直接让用户去识别)
				if (!((System.currentTimeMillis() - recordTimeCount) / 1000 > 1)) {
					mView.showToast("没听清哦...");
					FileUtil.deleteFile(recorder.getFilePath());// 删除源文件
					recordPath = "";
				} else {
					player = new MyMediaPlayer();
					recordPath = recorder.getFilePath();
					mView.showToast("录音完成");
					mView.showVoiceImg(recordPath);
					mView.addImg(photoPath);
					isNetRecord = false;

					mView.showRecordAndImg();
				}
			}

			@Override
			public void onFailRecord() {
				recorder.stop();
			}
		});
	}

	@Override
	public void playRecord() {
		if (!TextUtils.isEmpty(recordPath)) {
			if (!isPlaying) {
				if (isNetRecord) {
					player.playFromNet(recordPath);
				}else {
					player.playFromFile(recordPath);
				}
				player.setListener(this);
				player.start();
				isPlaying = true;
			} else {
				player.stop();
				isPlaying = false;
			}
			mView.changeVoiceStatus(isPlaying);
		}else {
            onRecord();
        }
	}

	@Override
	public void onFinishPlay() {
		player.stop();
		isPlaying = false;
		mView.changeVoiceStatus(isPlaying);
	}

	@Override
	public void deleteRecord() {
		if (!TextUtils.isEmpty(recordPath) && !isPlaying) {
			FileUtil.deleteFile(recordPath);
			mView.showToast("录音已删除");
			mView.showVoiceImg("");
			recordPath = "";
			player = null;

		}else {
            if (isPlaying){
                mView.showToast("请等待录音播放完毕或者先暂停录音吧");
            }
        }
	}

	@Override
	public void choosePhoto(String path) {
		photoPath.add(path);
		mView.addImg(photoPath);

		mView.showRecordAndImg();

		if (TextUtils.isEmpty(recordPath)) {
			mView.showVoiceImg("");
		}
	}

	@Override
	public void deleteImg(int index) {
		if (photoPath.size() > index) {
			photoPath.remove(index);
			mView.showToast("删除成功");
			mView.addImg(photoPath);
			if (photoPath.size() == 0 && TextUtils.isEmpty(recordPath)) {
                mView.showVoiceImg("");
			}
		}
	}

	@Override
	public void chooseServerTime() {
		mView.showServerTimeDialog();
	}

	@Override
	public void chooseContactWay() {
		mView.jumpToChooseContactWay();
	}

	@Override
	public void nextStup(String title, String desc, String price) {
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(price)
				|| TextUtils.isEmpty(serviceTimeUpload) || !(addressEntity.id > 0)) {
			mView.showToast("请完善发布信息");
			return;
		}

        //暂时不是必须的
		//if (TextUtils.isEmpty(recordPath)) {
			//mView.showToast("请至少添加一段录音");
			//return;
		//}

		if (!(photoPath.size() > 0)) {
			mView.showToast("请至少添加一张图片");
			return;
		}

		needEntity = new NeedEntity(title, desc, Double.parseDouble(price), serviceTimeUpload, addressEntity.id);

		// 上传操作
		upLoadPic();
	}

	private void upLoadPic() {
		mView.showProgress();
		// TODO 上传图片
		iFileUploadModel.uploadMultiFile(photoPath, "path", "goods", new OnNetResponseCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof List<?>) {
					// 接收图片数组
					List<String> list = (List<String>) data;
					// 空判断并转成后台规定的格式
					if (list != null && list.size() > 0) {
						StringBuilder stringBuilder = new StringBuilder();
						for (int i = 0; i < list.size(); i++) {
							stringBuilder.append(list.get(i));
							stringBuilder.append(",");
						}

						// 去掉最后一个逗号
						if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1) {
							stringBuilder.deleteCharAt(stringBuilder.length() - 1);
						}
						needEntity.picPath = stringBuilder.toString();

                        //如果录音存在就上传录音
                        if (!TextUtils.isEmpty(recordPath))
						    upLoadRecord();
                        else
                            summonReques();
					} else {
						needEntity = null;
						mView.hideProgress();
						mView.showToast("图片上传失败");
						LogUtil.setLogWithE("SummonPagPresenter", "upLoadPic path list is null");
					}
				} else {
					needEntity = null;
					mView.hideProgress();
					mView.showToast("图片上传失败");
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				needEntity = new NeedEntity();
				mView.hideProgress();
				mView.showToast("图片上传失败");
			}
		});
	}

	private void upLoadRecord() {
		mView.showProgress();

		iFileUploadModel.uploadSingleFile(0, recordPath, "path", "buyer_release", new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				if (data != null && data instanceof PictureEntity) {
					needEntity.recordPath = ((PictureEntity) data).getPath();
					summonReques();
					mView.hideProgress();
				} else {
					needEntity = null;
					mView.hideProgress();
					mView.showToast("音频上传失败");
				}
			}

			@Override
			public void onError(int errorCode, String message) {
				needEntity = null;
				mView.hideProgress();
				mView.showToast("音频上传失败");
			}
		});
	}

	private void summonReques() {
		mView.showProgress();
		iBuyerOrderModel.releaseOrderRequest(needEntity, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				mView.hideProgress();
				if (data != null && data instanceof String) {
					mView.showToast((String) data);
					mView.jumpToHomeActivity();
					mView.pickUp();
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

	@Override
	public void pickUp() {
		mView.pickUp();
	}

	@Override
	public void chooseAddressFinish(AddressEntity an) {
		mView.showContactWay(an);
		addressEntity = an;
	}

	@Override
	public void chooseTimeFinish(String time, int date) {
		Date d = DateUtil.getDateAdd(new Date(), date);
		String week = "";
		switch (date) {
		case 0:
			week = "今天";
			break;
		case 1:
			week = "明天";
			break;
		case 2:
			week = "后天";
			break;
		default:
			// DateUtil.getDayOfWeek(d) 这个方法取出来的是1~7，可以点进去看
			week = DateUtil.WEEKS[DateUtil.getDayOfWeek(d) - 1];
			break;
		}
		serviceTimeShow = week + " " + sdfShow.format(d) + " " + time;
        serviceTimeUpload = String.valueOf(DateUtil.dateToLong(d));
		mView.showServerTime(serviceTimeShow);
	}
}
