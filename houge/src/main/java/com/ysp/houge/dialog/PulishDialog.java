package com.ysp.houge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ysp.houge.R;
import com.ysp.houge.ui.publish.NeedPulishActivity;
import com.ysp.houge.ui.publish.SkillPulishActivity;

/**
 * Created by it_huangxin on 2016/1/23.
 */
public class PulishDialog extends Dialog implements View.OnClickListener {
    private Activity activity;

    private LinearLayout need,skill,outSide;


    public PulishDialog(Context context,Activity activity) {
        super(context, R.style.FileDesDialogNoBackground);
        setContentView(R.layout.dialog_pulish);
        setCanceledOnTouchOutside(true);
        this.activity = activity;
        initViews();
    }

    private void initViews() {
        need = (LinearLayout) findViewById(R.id.line_pulish_need);
        skill = (LinearLayout) findViewById(R.id.line_pulish_skill);
        outSide = (LinearLayout) findViewById(R.id.line_out_side_layout);

        need.setOnClickListener(this);
        skill.setOnClickListener(this);
        outSide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line_pulish_need:
                NeedPulishActivity.JumpIn(getContext(), null);
               activity.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.activity_stay);
                dismiss();
                break;
            case R.id.line_pulish_skill:
                SkillPulishActivity.JumpIn(getContext(), null);
                activity.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.activity_stay);
                dismiss();
                break;
            case R.id.line_out_side_layout:
                dismiss();
                break;
        }
    }
}
