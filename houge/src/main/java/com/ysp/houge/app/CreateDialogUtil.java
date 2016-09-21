/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.ysp.houge.dialog.BottomThreeBtnDialog;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor.ClickType;

/**
 * This class is used for 创建对话框工具类
 * 
 * @author tyn
 * @version 1.0, 2015-1-23 下午1:43:13
 */

public class CreateDialogUtil {

	public static OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				return true;
			} else {
				return false;
			}
		}
	};

	/**
	 * 创建底部三个按钮的对话框
	 * 
	 * @param context
	 * @param descriptor
	 * @param onThreeBtnClickListener
	 */
	public void createBottomThreeBtnDialog(final Context context,
			BottomThreeBtnDescriptor descriptor,
			final com.ysp.houge.dialog.BottomThreeBtnDialog.OnThreeBtnClickListener onThreeBtnClickListener) {
		if (context == null) {
			return;
		}
		BottomThreeBtnDialog choosePicDialog = new BottomThreeBtnDialog(
				context,
				descriptor,
				new com.ysp.houge.dialog.BottomThreeBtnDialog.OnThreeBtnClickListener() {

					@Override
					public void onThreeBtnClick(ClickType clickType) {
						switch (clickType) {
						// 从图库选择图片
						case ClickOne:
							if (onThreeBtnClickListener != null) {
								onThreeBtnClickListener
										.onThreeBtnClick(ClickType.ClickOne);
							}
							break;
						// 拍照
						case ClickTwo:
							if (onThreeBtnClickListener != null) {
								onThreeBtnClickListener
										.onThreeBtnClick(ClickType.ClickTwo);
							}
							break;
						case Cancel:
							if (onThreeBtnClickListener != null) {
								onThreeBtnClickListener
										.onThreeBtnClick(ClickType.Cancel);
							}
							break;
						default:
							break;
						}
					}
				});
	}

}
