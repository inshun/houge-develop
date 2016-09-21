package com.ysp.houge.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 系统工具类
 */
public class SystemUtils {
	/** 获取设备信息 */
	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDeviceId(Context context) {
		android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId() == null ? "" : tm.getDeviceId();
	}

	/** 获取版本Code */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionCode;
	}

	/** 获取版本号名称 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {

		}
		return versionName;
	}

	/**
	 * 获取手机型号
	 */
	public static String getProductModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机操作系统版本号
	 */
	public static String getReleaseVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取系统SDK版本 数字
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}

	/**
	 * 安装应用程序
	 * 
	 * @param context
	 * @param file
	 *            待安装的文件
	 */
	public static void install(Context context, File file) {
		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * @描述: 获取渠道名称 @方法名: getChannel @param context @return @返回类型 String @创建人
	 *      tyn @创建时间 2015年7月18日下午12:55:31 @修改人 tyn @修改时间
	 *      2015年7月18日下午12:55:31 @修改备注 @since @throws
	 */
	public static String getChannel(Context context) {
		String channel = "houge";
		if (context == null)
			return channel;
		try {
			channel = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return channel;
	}
	/*获取堆内存大小*/
	public static int getHeapSize(Context context){
		ActivityManager systemService = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return systemService.getMemoryClass();
	}

    /** 获取当前进程的名称 */
    public static String getAppName(Context context,int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }
}
