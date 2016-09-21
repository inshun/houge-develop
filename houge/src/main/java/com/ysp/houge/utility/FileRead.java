/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.text.TextUtils;

/**
 * This class is used for ...
 * 
 * @author tyn
 * @version 1.0, 2014-10-11 下午8:15:25
 */
public class FileRead {
	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 * @throws AppException
	 */
	public static String readTxtFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		String lineTxt = null;
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			} else {
				LogUtil.setLogWithE("FileRead:FileException", "找不到指定的文件");
			}
		} catch (Exception e) {
			LogUtil.setLogWithE("FileRead:FileException", "读取文件内容出错");
		}
		return lineTxt;
	}

}