package com.ysp.houge.popwindow;

import com.ysp.houge.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 描述： 举报pop
 *
 * @ClassName: ReportPopupWindow
 * 
 * @author: hx
 * 
 * @date: 2015年12月14日 下午6:08:35
 * 
 *        版本: 1.0
 */
public class ReportPopupWindow extends BasePopupWindow implements OnClickListener {
	public static final int REPORT_TYPE_ONE = 0;
	public static final int REPORT_TYPE_TWO = 1;
	public static final int REPORT_TYPE_THREE = 2;
	private TextView reportOne, reportTwo, reportThree, cancle;
	private OnReportLisenter lisenter;
	public ReportPopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
	}

	public void setLisenter(OnReportLisenter lisenter) {
		this.lisenter = lisenter;
	}

	@Override
	public void initViews() {
		reportOne = (TextView) contentView.findViewById(R.id.tv_report_type_one);
		reportTwo = (TextView) contentView.findViewById(R.id.tv_report_type_two);
		reportThree = (TextView) contentView.findViewById(R.id.tv_report_type_three);
		cancle = (TextView) contentView.findViewById(R.id.tv_report_cancle);
	}

	@Override
	public void initEvents() {
		reportOne.setOnClickListener(this);
		reportTwo.setOnClickListener(this);
		reportThree.setOnClickListener(this);
		cancle.setOnClickListener(this);
	}

	@Override
	public void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_report_type_one:
			lisenter.onReport(REPORT_TYPE_ONE);
			break;
		case R.id.tv_report_type_two:
			lisenter.onReport(REPORT_TYPE_TWO);
			break;
		case R.id.tv_report_type_three:
			lisenter.onReport(REPORT_TYPE_THREE);
			break;
		case R.id.tv_report_cancle:
			break;
		default:
			break;
		}
		dismiss();
	}
	
	public interface OnReportLisenter {
		void onReport(int reportType);
	}

}
