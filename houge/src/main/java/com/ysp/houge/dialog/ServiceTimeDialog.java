package com.ysp.houge.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.adapter.DateAdapter;
import com.ysp.houge.adapter.TimeAdapter;
import com.ysp.houge.model.entity.bean.DateTimeEntity;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.widget.HorizontalListView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * @描述: 服务时间Dialog
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月25日下午2:59:42
 * @version 1.0
 */
@SuppressLint("SimpleDateFormat")
public class ServiceTimeDialog extends Dialog implements OnItemClickListener {
	public String time; //几点
	public int date; // 几天之后的日期
	private Context context;
	// 日期
	private HorizontalListView listView;
	private DateAdapter dateAdapter;
	private List<DateTimeEntity> listDate;
	// 时间
	private GridView gridView;
	private TimeAdapter timeAdapter;
	private List<String> listTime;
	// 屏幕宽度方便计算控件宽度
	private int srceenWidth;
	private OnChooseFinis onChooseFinis;

	public ServiceTimeDialog(Context context, int srceenWidth) {
		super(context, R.style.FileDesDialogNoBackground);
		// 设置Dialog在底部
		LayoutParams a = getWindow().getAttributes();
		a.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(a);
		// 设置dialog的布局文件
		setContentView(R.layout.dialog_service_time);
		// 设置dialog的宽高
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// 设置dialog点击外部可以消失
		setCanceledOnTouchOutside(true);
		this.context = context;
		this.srceenWidth = srceenWidth;
		initDate();
		initTime();
		initViews();
	}

	public void setOnChooseFinis(OnChooseFinis onChooseFinis) {
		this.onChooseFinis = onChooseFinis;
	}

	private void initDate() {
		// TODO 初始日期数据
		listDate = new ArrayList<DateTimeEntity>();
		Date date = null;
		SimpleDateFormat sdWeek = new SimpleDateFormat("EEEE");
		SimpleDateFormat sdDate = new SimpleDateFormat("MM-dd");

		for (int i = 0; i < 7; i++) {
			date = DateUtil.getDateAdd(new Date(), i);
			DateTimeEntity entity = new DateTimeEntity();
			switch (i) {
			case 0:
				entity.week = "今天";
				break;
			case 1:
				entity.week = "明天";
				break;
			case 2:
				entity.week = "后天";
				break;
			default:
				entity.week = sdWeek.format(date);
				break;
			}
			entity.date = sdDate.format(date);
			listDate.add(entity);
		}
	}

	private void initTime() {
		// TODO 初始时间数据
		String[] time = new String[] { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
				"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" };
		listTime = Arrays.asList(time);
	}

	private void initViews() {
		// TODO 初始化视图
		listView = (HorizontalListView) findViewById(R.id.lv_date);
		gridView = (GridView) findViewById(R.id.gv_time);

		dateAdapter = new DateAdapter(srceenWidth, context, listDate);
		listView.setAdapter(dateAdapter);
		listView.setOnItemClickListener(this);

		timeAdapter = new TimeAdapter(listTime, context);
		gridView.setAdapter(timeAdapter);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (parent.getId()) {
		case R.id.lv_date:
			dateAdapter.setIndex(position);
			dateAdapter.notifyDataSetChanged();
			date = position;
			break;
		case R.id.gv_time:
			timeAdapter.setIndex(position);
			timeAdapter.notifyDataSetChanged();
			time = listTime.get(position);
			onChooseFinis.onFinish();
			ServiceTimeDialog.this.dismiss();
			break;
		default:
			break;
		}
	}

	public interface OnChooseFinis {
		void onFinish();
	}
}
