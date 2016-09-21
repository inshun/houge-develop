package com.ysp.houge.utility;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.util.Base64;

public class DesUtil {

	private final static String DES = "DES";

	private static String key2 = "weLian&188";

	//
	// public static void main(String[] args) throws Exception {
	// String data = "10086";
	// System.err.println(encrypt(data, key2));
	// String mi = "TRK55xLZTY0=";// iBfYJ~5FXiY=
	// System.err.println(decrypt(mi, key2));
	//
	// }
	//
	// static {
	//
	// }

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		byte[] bt = encrypt(data.getBytes("UTF8"), key2.getBytes("UTF8"));
		// String strs = new BASE64Encoder().encode(bt);
		String strs = Base64.encodeToString(bt, Base64.DEFAULT);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data) throws IOException,
			Exception {
		if (data == null)
			return null;
		byte[] buf = Base64.decode(data, Base64.DEFAULT);
		// BASE64Decoder decoder = new BASE64Decoder();
		// byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf, key2.getBytes());
		return new String(bt);
	}

	public static byte[] decrypt(byte[] source) throws Exception {
		if (source == null)
			return new byte[0];
		byte[] buf = Base64.decode(source, Base64.DEFAULT);
		// BASE64Decoder decoder = new BASE64Decoder();
		// byte[] buf = decoder.decodeBuffer(data);
		return decrypt(buf, key2.getBytes());
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * 二进制转字符串。
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n < b.length - 1)
			// hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length; // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i]; // 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			} // 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 字符串转二进制。
	 *
	 * @param hex
	 * @return
	 */
	private byte[] hex2byte(String hex) {
		String[] h = hex.split(":");
		byte[] b = new byte[h.length];
		for (int i = 0; i < h.length; i++) {
			// System.out.println(i+":"+h[i]);
			int byteint = Integer.parseInt(h[i], 16) & 0xFF;
			b[i] = new Integer(byteint).byteValue();
		}

		return b;
	}
}
