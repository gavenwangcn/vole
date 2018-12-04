package com.github.vole.common.security;

import org.apache.commons.lang3.StringUtils;

/**
 * 加密工具
 * 高级加密标准 AES(对称加密算法)
 */
public class AESUtil extends AESEncrypter {
	public AESUtil(String aesKey) throws RuntimeException {
		super(aesKey);
	}
	// 因为美国对软件出口的控制,默认只支持128位;要使用256则需另下bcprov-jdk的jar包替换jre\lib\security下的jar
	private static final int KEY_SIZE = 128;
	// 密钥
	private static final String aesKey = "8979d58a2a51e140a2088fb505218ce1";

	/**
	 * 加密
	 * @param text
	 * @return
	 * @throws RuntimeException
	 */
	public static String encrypt(String text) throws RuntimeException {
		if (StringUtils.isBlank(text))
			return null;
		return AESEncrypter.encrypt(aesKey, text.getBytes());
	}

	/**
	 * 解密
	 * @param encryptText
	 * @return
	 * @throws RuntimeException
	 */
	public static String decrypt(String encryptText) throws RuntimeException {
		if (StringUtils.isBlank(encryptText))
			return null;
		return new String(AESEncrypter.decrypt(aesKey, encryptText));
	}

	public static void main(String[] args) {
		String accNoBefore = "6223889011053390";
		String accNoencrypt = encrypt(accNoBefore);
		System.out.println(accNoencrypt);
		System.out.println(decrypt(accNoencrypt));
	}
}
