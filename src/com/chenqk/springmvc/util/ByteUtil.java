package com.chenqk.springmvc.util;

/**
 * byte数组处理公共类
 * @author RenLiang
 *
 */
public class ByteUtil {
	/*
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			if(i != src.length-1)
				stringBuilder.append(":");
		}
//		stringBuilder.subSequence(0, stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * byte数组转化成int
	 * @param src 原始byte数组
	 * @param start 要转化的起始位置byte数组起始位置
	 * @param length 要转化的byte个数
	 * @return 转化的int结果
	 */
	public static int bytesToInt(byte[] src, int start, int length) {
		if (src == null || src.length <= 0) {
			return 0;
		}
		if (start > src.length - 1 || start + length > src.length - 1) {
			return 0;
		}
		int result = 0;
		for (int i = start, j = 0; i < src.length && j < length; i++, j++) {
			int v = src[i] & 0xFF;
			result += (v << ((length - j - 1) * 8));
		}
		return result;
	}

	/**
	 * byte数组转化成int
	 * @param src
	 * @return
	 */
	public static int bytesToInt(byte[] src) {
		if (src == null || src.length <= 0) {
			return 0;
		}
		int result = 0;
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			result += (v << ((src.length - i - 1) * 8));
		}
		return result;
	}

}
