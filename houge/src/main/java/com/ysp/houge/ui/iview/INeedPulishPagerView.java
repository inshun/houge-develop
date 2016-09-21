package com.ysp.houge.ui.iview;

import android.widget.ImageView;

import com.ysp.houge.dialog.RecordDialog;
import com.ysp.houge.model.entity.bean.AddressEntity;

import java.util.ArrayList;

/**
 * @描述: 买家发布页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月27日下午5:50:33
 * @version 1.0
 */
public interface INeedPulishPagerView extends IBaseView {

	void showRecordDialog(RecordDialog.RecordListener listener);

	void showPhotoDialog();

	void showServerTimeDialog();

	void showServerTime(String date);

	void jumpToChooseContactWay();

	/** 显示联系方式 */
	void showContactWay(AddressEntity entity);

	void pickUp();

	void showVoiceImg(String record);

	void changeVoiceStatus(boolean isPlaying);

	void addImg(ArrayList<String> list);

	void showRecordAndImg();

	void showAddImgImg(ImageView view);

	void jumpToHomeActivity();
}
