package com.ysp.houge.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
* Created by IntelliJ IDEA.
* User: zhUser
* Date: 13-1-24
* Time: 下午6:53
*/
public class NoScrollGridView extends GridView{
     public NoScrollGridView(Context context) {
          super(context);
     }

     public NoScrollGridView(Context context, AttributeSet attrs) {
          super(context, attrs);
     }

     public NoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
          super(context, attrs, defStyleAttr);
     }

     @TargetApi(Build.VERSION_CODES.LOLLIPOP)
     public NoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
          super(context, attrs, defStyleAttr, defStyleRes);
     }

//     public NoScrollGridView(Context context, AttributeSet attrs){
//          super(context, attrs);
//     }


     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
          super.onMeasure(widthMeasureSpec, mExpandSpec);
     }
}

