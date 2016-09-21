package com.ysp.houge.popwindow;

import com.ysp.houge.R;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ChangeLogStatusPopupWindow extends BasePopupWindow implements OnClickListener {

	private LinearLayout buyer, seller;
	private OnChangeLoginStatusListener listener;
	public ChangeLogStatusPopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
		this.contentView = contentView;
		setBackgroundDrawable(new ColorDrawable(0x90000000));
		setAnimationStyle(R.style.popwin_anim_top);
	}

	public void setListener(OnChangeLoginStatusListener listener) {
		this.listener = listener;
	}

	@Override
	public void initViews() {
		buyer = (LinearLayout) contentView.findViewById(R.id.line_buyer);
		seller = (LinearLayout) contentView.findViewById(R.id.line_seller);
	}

	@Override
	public void initEvents() {
		buyer.setOnClickListener(this);
		seller.setOnClickListener(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.line_buyer:
			listener.OnChangeBuyer();
			break;
		case R.id.line_seller:
			listener.OnChangeSeller();
			break;
		default:
			break;
		}
	}

	public interface OnChangeLoginStatusListener {
		void OnChangeBuyer();

		void OnChangeSeller();
	}

}
