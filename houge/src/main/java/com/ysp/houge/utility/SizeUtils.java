package com.ysp.houge.utility;

import android.app.Activity;
import android.content.Context;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.view.Display;

import java.io.IOException;

public class SizeUtils {

	/**
	 * 获取手机屏幕分辨率，
	 * 
	 * @param activity
	 * @param type
	 *            参数1表示获取屏幕宽像素值，2表示获取屏幕高像素值，3表示分辨率
	 * @return
	 */
	public static int getDisplay(Activity activity, int type) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		Display localDisplay = activity.getWindowManager().getDefaultDisplay();
		localDisplay.getMetrics(localDisplayMetrics);
		int size = 0;
		switch (type) {
		case 1:
			size = localDisplayMetrics.widthPixels;
			break;
		case 2:
			size = localDisplayMetrics.heightPixels;
			break;
		case 3:
			size = localDisplayMetrics.densityDpi;
			break;
		}
		return size;
	}

	/**
	 * 获取手机屏幕的宽度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		Display localDisplay = activity.getWindowManager().getDefaultDisplay();
		localDisplay.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.widthPixels;
	}


	/**
	 * 获取手机屏幕的高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		Display localDisplay = activity.getWindowManager().getDefaultDisplay();
		localDisplay.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.heightPixels;
	}

	/**
	 * 将dip转换为px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px转换为dip
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * 得到旋转度数，修正图片的角度,用来调整图片到正常方向，为了解决三星手机拍照倾斜的bug
	 * 
	 * @param filePath
	 *            照片的路径
	 */
	public static int getExifOrientation(String filePath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filePath);
		} catch (IOException ex) {
			LogUtil.setLogWithE("getExifOrientation", "cannot read exif");
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
}
