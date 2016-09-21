package com.ysp.houge.utility;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

/**
 * 跳转常见系统页面
 * 
 */
public class GetUri {

	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
	}

	/**
	 * 打开系统拨号页面
	 * 
	 * @param context
	 * @param telephone
	 */
	public static void openCallTelephonePage(Context context, String telephone) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * @描述:直接打电话 @方法名: openCallTelephone @param context @param telephone @返回类型
	 * void @创建人 tyn @创建时间 2015年6月28日上午7:32:32 @修改人 tyn @修改时间
	 * 2015年6月28日上午7:32:32 @修改备注 @since @throws
	 */
	public static void openCallTelephone(Context context, String telephone) {
//		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
		Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + telephone));
		context.startActivity(phoneIntent);
	}


	/**
	 * 打开系统短信页面
	 * 
	 * @param context
	 * @param telephone
	 */
	public static void openSMS(Context context, String content, String telephone) {
		Uri uri = Uri.parse("smsto:" + telephone);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

	/**
	 * 跳转到应用市场
	 */
	public static void jumpToMarket(Context context) {
		Intent i = getIntent(context);
		boolean b = judge(context, i);
		if (b == false) {
			context.startActivity(i);
		}
	}

	/**
	 * @描述: @方法名: openPDF @param paramContext @param path @return @返回类型
	 * Intent @创建人 tyn @创建时间 2015年5月28日下午12:57:37 @修改人 tyn @修改时间
	 * 2015年5月28日下午12:57:37 @修改备注 @since @throws
	 */
	public static Intent openPDF(Context paramContext, String path) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "application/pdf");
		paramContext.startActivity(intent);
		return intent;
	}

	public static Intent getIntent(Context paramContext) {
		StringBuilder localStringBuilder = new StringBuilder().append("market://details?id=");
		String str = paramContext.getPackageName();
		localStringBuilder.append(str);
		Uri localUri = Uri.parse(localStringBuilder.toString());
		return new Intent("android.intent.action.VIEW", localUri);
	}

	public static void start(Context paramContext, String paramString) {
		Uri localUri = Uri.parse(paramString);
		Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		paramContext.startActivity(localIntent);
	}

	public static boolean judge(Context paramContext, Intent paramIntent) {
		List<ResolveInfo> localList = paramContext.getPackageManager().queryIntentActivities(paramIntent,
				PackageManager.GET_INTENT_FILTERS);
		if ((localList != null) && (localList.size() > 0)) {
			return false;
		} else {
			return true;
		}
	}
}