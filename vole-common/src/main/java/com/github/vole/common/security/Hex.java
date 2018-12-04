package com.github.vole.common.security;

/**
 * 十六进制/数组互转
 *
 * @author steellee
 * @version 1.0.0
 */
public class Hex {
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * 将字节数组转换为十六进制字符数组
	 * 
	 * @param data
	 *            byte[]
	 * @return 十六进制char[]
	 */
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 * 
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	 * @return 十六进制char[]
	 */
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 * 
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            用于控制输出的char[]
	 * @return 十六进制char[]
	 */
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param data
	 *            byte[]
	 * @return 十六进制String
	 */
	public static String encodeHexStr(byte[] data) {
		return encodeHexStr(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	 * @return 十六进制String
	 */
	public static String encodeHexStr(byte[] data, boolean toLowerCase) {
		return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            用于控制输出的char[]
	 * @return 十六进制String
	 */
	protected static String encodeHexStr(byte[] data, char[] toDigits) {
		return new String(encodeHex(data, toDigits));
	}

	/**
	 * 将十六进制字符数组转换为字节数组
	 * 
	 * @param data
	 *            十六进制char[]
	 * @return byte[]
	 * @throws RuntimeException
	 *             如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
	 */
	public static byte[] decodeHex(char[] data) {
		int len = data.length;
		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * 将十六进制字符转换成一个整数
	 * 
	 * @param ch
	 *            十六进制char
	 * @param index
	 *            十六进制字符在字符数组中的位置
	 * @return 一个整数
	 * @throws RuntimeException
	 *             当ch不是一个合法的十六进制字符时，抛出运行时异常
	 */
	private static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	public static String bytes2Hex(byte[] inbuf) {
		int i;
		String byteStr;
		StringBuffer strBuf = new StringBuffer();
		for (i = 0; i < inbuf.length; i++) {
			byteStr = Integer.toHexString(inbuf[i] & 0x00ff);
			if (byteStr.length() != 2) {
				strBuf.append('0').append(byteStr);
			} else {
				strBuf.append(byteStr);
			}
		}
		return new String(strBuf);
	}

	public static byte[] hexToBytes(String inbuf) {
		int i;
		int len = inbuf.length() / 2;
		byte outbuf[] = new byte[len];
		for (i = 0; i < len; i++) {
			String tmpbuf = inbuf.substring(i * 2, i * 2 + 2);
			outbuf[i] = (byte) Integer.parseInt(tmpbuf, 16);
		}
		return outbuf;
	}

	public static void main(String[] args) {
		String srcStr = "待转换字符串";
		String encodeStr = encodeHexStr(srcStr.getBytes());
		String decodeStr = new String(decodeHex(encodeStr.toCharArray()));
		System.out.println("转换前：" + srcStr);
		System.out.println("转换后：" + encodeStr);
		System.out.println("还原后：" + decodeStr);
	}
}
