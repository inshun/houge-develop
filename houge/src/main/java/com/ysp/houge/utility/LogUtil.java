package com.ysp.houge.utility;

/**
 * log日志打印类
 */

import android.content.Context;
import android.util.Log;

import com.ysp.houge.BuildConfig;

public class LogUtil {
    private static boolean showLog = true;
    public static final String DEBUG = "测试加载本地数据流程流程";//key = 1
    public static final String DEBUG_A = "测试ListView加载刷新更多";// key = 2

    public static void setLogC(String key, String value) {
        if (showLog) {
            Log.d(key, value);
        }
    }
    public static void setLogC(Context context, String value) {
        if (showLog) {
            Log.d(context.getClass().getName(), value);
        }
    }
    public static void deBug(String clazz, String value,int key) {


        if (showLog) {
            switch (key){
                case 1:
                    if (false){
                        Log.d(DEBUG, clazz+" : "+value);
                    }
                case 2:
                    if (true){
                        Log.d(DEBUG_A, clazz+" : "+value);
                    }

            }

        }
    }
    /**
     * 设置有关log.i的日志打印
     */
    public static void setLogWithI(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.i(key, value);
        }
    }

    /**
     * 设置有关log.e的日志打印
     */
    public static void setLogWithE(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.e(key, value);
        }
    }

    /**
     * 设置有关log.w的日志打印
     */
    public static void setLogWithW(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.w(key, value);
        }
    }

    /**
     * 设置有关log.v的日志打印
     */
    public static void setLogWithV(String key, String value) {
        if (BuildConfig.DEBUG) {
            Log.v(key, value);
        }
    }
}
