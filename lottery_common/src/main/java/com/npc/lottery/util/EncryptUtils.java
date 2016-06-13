/**
 * 
 */
package com.npc.lottery.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * SHA加密工具类
 * @author c1panx
 * 2014-11-21  上午11:48:24
 */
public class EncryptUtils {
	
	private static Logger logger = Logger.getLogger(EncryptUtils.class);
	
	/**
	 * 加密明文<br>
	 * eservice密码加密策略
	 * @param plaintext
	 * @return
	 */
	public static String encryptBySHA(String plaintext) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(plaintext.getBytes("UTF-8"));

			byte raw[] = md.digest();
			String hash = new Base64().encodeToString(raw);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * MD5加密
	 * @param text
	 * @return
	 */
	public static String encryptByMD5(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = text.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
