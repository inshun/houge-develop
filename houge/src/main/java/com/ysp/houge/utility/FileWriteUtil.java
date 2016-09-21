package com.ysp.houge.utility;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriteUtil {

	/**
	 * 写入文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void writeToPath(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, false);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
