package com.ysp.houge.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;

import com.ysp.houge.R;

public class HougeProgressDialog {
	private static HougeProgressDialog myProgressDialog = null;
	private boolean hasShow = false;
	private ProgressDialog progressDialog = null;

	private HougeProgressDialog(boolean hasShow) {
		this.hasShow = hasShow;
	}

	public static HougeProgressDialog newInstance(boolean hasShow) {
		if (myProgressDialog == null) {
			myProgressDialog = new HougeProgressDialog(hasShow);
		}
		return myProgressDialog;
	}

	public static void show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable) {
		if (myProgressDialog == null) {
			myProgressDialog = HougeProgressDialog.newInstance(false);
		}
		if (!myProgressDialog.hasShow) {
			myProgressDialog.progressDialog = ProgressDialog.show(context,
					title, message, indeterminate, cancelable);
			myProgressDialog.progressDialog.setCanceledOnTouchOutside(false);
			myProgressDialog.progressDialog
					.setContentView(R.layout.custom_progress_dialog);
			TextView tvmessage = (TextView) myProgressDialog.progressDialog
					.findViewById(R.id.tvforprogress);
			tvmessage.setText(message);
		}
		myProgressDialog.hasShow = true;
	}

	public static void hide() {
		if (myProgressDialog != null && myProgressDialog.hasShow && myProgressDialog.progressDialog != null) {
			myProgressDialog.progressDialog.dismiss();
			myProgressDialog.progressDialog = null;
//			myProgressDialog.hasShow = false;
		}
	}

	public static boolean isShow() {
		if (myProgressDialog != null && myProgressDialog.hasShow
				&& myProgressDialog.progressDialog != null) {
			return true;
		} else
			return false;
	}

	public static void show(Context context, CharSequence message) {
		show(context, "", message, true, true);
	}

	public static void show(Context context) {
		if (context != null) {
			show(context, "", "请稍候...", true, true);
		}
	}
}
