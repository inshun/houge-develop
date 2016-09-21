package com.ysp.houge.utility;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by zhangjianlong on 16/8/23.
 */
public class ControlKeyBoardLayout {
    public static void control(final View root, final View mSubmit, final boolean isScroll) {

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                root.getWindowVisibleDisplayFrame(rect);
                int rooeHeight = root.getRootView().getHeight() - rect.bottom;
                if (rooeHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    mSubmit.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
//                    int srollHeight = (location[1] + mSubmit.getHeight()) - rect.bottom;
                    if (isScroll == true) {
                        root.scrollTo(0, rooeHeight);
                    }

                } else {
                    root.scrollTo(0, 0);
                }

            }
        });
    }

}