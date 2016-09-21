package com.ysp.houge.dialog;

import com.ysp.houge.R;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.widget.image.selector.bean.Image;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class RecordDialog extends Dialog implements OnTouchListener {
    private TextView mCountDownNum, mPinToRecord, mLongestTime;
    private LinearLayout mRecordLayout;
    private ImageView record;
    private TimeCount count;
    private RecordListener listener;
    private AnimationDrawable frameAnim;

    public RecordDialog(Context context, RecordListener listener) {
        super(context, R.style.FileDesDialogNoBackground);
        this.listener = listener;
        setCanceledOnTouchOutside(true);// 点击外部可以取消
        setContentView(R.layout.dialog_record);
        initViews();
    }

    public void stopCountDown() {
        if (count != null) {
            count.cancel();
        }
    }

    private void initViews() {
        mCountDownNum = (TextView) findViewById(R.id.tv_time_count);
        mPinToRecord = (TextView) findViewById(R.id.tv_pin_to_record);
        mLongestTime = (TextView) findViewById(R.id.tv_longest_time);
        mRecordLayout = (LinearLayout) findViewById(R.id.line_record_layout);
        mRecordLayout.setOnTouchListener(this);

        record = (ImageView) findViewById(R.id.iv_record);
        frameAnim = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.play_record_push_need);
        record.setImageDrawable(frameAnim);
        frameAnim.stop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (count == null) {
                    count = new TimeCount(60 * 1000, 1000);
                    mLongestTime.setVisibility(View.GONE);
                    mPinToRecord.setText("松开取消");
                    count.start();
                    frameAnim.start();
                    listener.onStartRecord();
                } else {
                    LogUtil.setLogWithE("RecordDialog", "TimeCount is not null");
                }
                break;
            case MotionEvent.ACTION_UP:
                count.cancel();
                frameAnim.stop();
                listener.onFinishRecord();
                RecordDialog.this.cancel();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                count.cancel();
                listener.onFailRecord();
                RecordDialog.this.cancel();
                break;
        }
        return true;
    }

    public interface RecordListener {
        void onStartRecord();

        void onFinishRecord();

        void onTimeCountDown();

        void onFailRecord();
    }

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mCountDownNum.setText(millisUntilFinished / 1000 + "\"");
        }

        @Override
        public void onFinish() {
            listener.onTimeCountDown();
            this.cancel();
            frameAnim.stop();
            RecordDialog.this.cancel();
        }

    }

}
