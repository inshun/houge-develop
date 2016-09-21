package com.ysp.houge.utility;

/**
 * Created by it_huangxin on 2016/2/23.
 */
public class ChangeUtils {

    /** 异常返回default，其他返回转换值 */
    public static int toInt(String string,int defaultNum){
        int num = defaultNum;
        try{
            num = Integer.parseInt(string);
        }catch (Exception e){
            LogUtil.setLogWithE("ChangeUtils","toInt:" + e.getMessage());
        }
        return num;
    }

    public static double toDouble(String string,double defaultNum){
        double num = defaultNum;
        try{
            num = Double.parseDouble(string);
        }catch (Exception e){
            LogUtil.setLogWithE("ChangeUtils","toDouble:" + e.getMessage());
        }
        return num;
    }
}
