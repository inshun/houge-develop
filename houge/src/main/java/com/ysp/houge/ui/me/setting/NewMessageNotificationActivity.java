package com.ysp.houge.ui.me.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.presenter.INewMessageNotificationPagePresenter;
import com.ysp.houge.presenter.impl.NewMessageNotificationPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.INewMessageNotificationPageView;
import com.ysp.houge.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

//新消息通知
public class NewMessageNotificationActivity extends BaseFragmentActivity
		implements OnClickListener, INewMessageNotificationPageView {

	private ImageView mNightNtoe, mVoiceNote, mShockNote;
	private List<ImageView> imgs = new ArrayList<ImageView>();

	private INewMessageNotificationPagePresenter iNewMessageNotificationPagePresenter;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, NewMessageNotificationActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_new_message_notification);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(R.string.new_message_note);
		actionBar.setLeftEnable(true);
		actionBar.setRightText(R.string.submit);
		actionBar.setRightClickListenner(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mNightNtoe = (ImageView) findViewById(R.id.iv_night_note);
		mVoiceNote = (ImageView) findViewById(R.id.iv_voice_note);
		mShockNote = (ImageView) findViewById(R.id.iv_shock_note);

		imgs.add(mNightNtoe);
		imgs.add(mVoiceNote);
		imgs.add(mShockNote);

		for (int i = 0; i < imgs.size(); i++) {
			imgs.get(i).setOnClickListener(this);
		}
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iNewMessageNotificationPagePresenter = new NewMessageNotificationPagePresenter(this);

		iNewMessageNotificationPagePresenter.getNoteSetting();
	}

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 提交
		case R.id.menu_head_right:
			iNewMessageNotificationPagePresenter.submit();
			break;

		// 晚间提醒
		case R.id.iv_night_note:
			iNewMessageNotificationPagePresenter.changeNote(0);
			break;

		// 声音提醒
		case R.id.iv_voice_note:
			iNewMessageNotificationPagePresenter.changeNote(1);
			break;
		// 震动提醒
		case R.id.iv_shock_note:
			iNewMessageNotificationPagePresenter.changeNote(2);
			break;
		}
	}

	@Override
	public void serNoteType(int index, boolean isNote) {
		if (isNote) {
			switch (MyApplication.getInstance().getLoginStaus()){
                case MyApplication.LOG_STATUS_SELLER:
                    imgs.get(index).setImageResource(R.drawable.icon_sel_blue);
                    break;
                case MyApplication.LOG_STATUS_BUYER:
                    imgs.get(index).setImageResource(R.drawable.icon_sel_orange);
                    break;
            }
		} else {
			imgs.get(index).setImageResource(R.drawable.icon_def);
		}
	}
}
