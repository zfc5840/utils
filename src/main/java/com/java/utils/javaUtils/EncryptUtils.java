package com.java.utils.javaUtils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class EncryptUtils {
	private static String DEFAULT_CHARSET = "utf-8";
	private static final String KEY = "member_invite";
	private static final String[] KEYS = new String[]{
			"f256e8a0-9548-4d54-ab40-7aa93f114a02",
			"075163b5-9d60-4e9c-95d8-a62bed876044",
			"1a07d41e-d648-4054-8123-88176637c50e" };
	private final static String DES = "DES";

	public static void main(String[] args) {
		System.out.println(7 % 2 == 0);
	}

	public static String encryptEmail(String content) {
		return encrypt(KEY, content);
	}

	public static String getKeys(int i){
		if(i > KEYS.length || i < 0){
			return KEY;
		}
		return KEYS[i];
	}
	/**
	 * des 加密
	 * 
	 * @param key
	 * @param content
	 *            要加密的内容
	 * @return
	 */
	public static String encrypt(String key, String content) {
		try {

			byte[] keyBytes = key.getBytes(DEFAULT_CHARSET);
			byte[] contentBytes = content.getBytes(DEFAULT_CHARSET);
			byte[] encryptBytes = encrypt(contentBytes, keyBytes);
			String encryptStr = byte2hex(encryptBytes);
			return encryptStr;
		} catch (Exception e) {
			 e.printStackTrace();
			return null;

		}
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            明文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 密文(字节)
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
}
