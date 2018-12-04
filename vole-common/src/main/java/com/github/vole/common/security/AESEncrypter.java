package com.github.vole.common.security;


import com.github.vole.common.utils.StreamUtils;
import com.github.vole.common.utils.StringUtil;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * AES对称加解密
 * 加密数据默认转换为Base64编码
 */
public class AESEncrypter {
	// 因为美国对软件出口的控制,默认只支持128位;要使用256则需另下bcprov-jdk的jar包替换jre\lib\security下的jar
	private static final int KEY_SIZE = 128;
	protected static final String KEY_ALGORITHM = "AES";

	private static Map<String, AESEncrypter> encryptMap = new HashMap<String, AESEncrypter>();
	private int keySize = KEY_SIZE;
	private String workPattern = "ECB";
	private String paddingPattern = "PKCS5Padding";
	private Cipher enCipher = null;
	private Cipher deCipher = null;
	private B64Encrypter b64Encrypter = null;
	protected String aesKey;

	public AESEncrypter(String aesKey) throws RuntimeException {
		init(aesKey, B64Encrypter.DEFAULT_ALPHABET, workPattern, paddingPattern, aesKey);
	}

	public AESEncrypter(String aesKey, boolean useHex) throws RuntimeException {
		init(aesKey, useHex ? null : B64Encrypter.DEFAULT_ALPHABET, workPattern, paddingPattern, aesKey);
	}

	public AESEncrypter(String aesKey, String b64Key) throws RuntimeException {
		init(aesKey, b64Key, workPattern, paddingPattern, aesKey);
	}

	public AESEncrypter(String aesKey, String workPattern, String paddingPattern) throws RuntimeException {
		init(aesKey, null, workPattern, paddingPattern, aesKey);
	}

	public AESEncrypter(String aesKey, String b64Key, String workPattern, String paddingPattern, String ivParameter) throws RuntimeException {
		init(aesKey, b64Key, workPattern, paddingPattern, ivParameter);
	}

	private void init(String aesKey, String b64Key, String workPattern, String paddingPattern, String ivParameter) {
		this.aesKey=aesKey;
		try {
			Key key=generateKey();
			StringBuilder pattern = new StringBuilder(KEY_ALGORITHM);
			pattern.append("/").append(workPattern);
			pattern.append("/").append(paddingPattern);

			enCipher = Cipher.getInstance(pattern.toString());
			deCipher = Cipher.getInstance(pattern.toString());

			// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			if (workPattern.equals("CBC")) {
				ivParameter = StringUtil.getLMTString(ivParameter, 16, "x");
				IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes("UTF-8"));
				enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
				deCipher.init(Cipher.DECRYPT_MODE, key, iv);
			} else {
				enCipher.init(Cipher.ENCRYPT_MODE, key);
				deCipher.init(Cipher.DECRYPT_MODE, key);
			}

			if (!StringUtil.isBlank(b64Key))
				b64Encrypter = B64Encrypter.getInstance(b64Key);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing AESEncrypter class. Cause: " + e);
		}
	}
	// 覆盖此方法以改变Key的生成方式
	public Key generateKey() throws Exception {
		if (keySize != 128 && keySize != 192 && keySize != 256) {
			throw new RuntimeException("Error initializing AESEncrypter class. Cause: Unsupported keySize " + keySize);
		}
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
		kgen.init(keySize, new SecureRandom(aesKey.getBytes()));
		return kgen.generateKey();
		//OR SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), KEY_ALGORITHM);
	}
	public String encode(byte[] data) throws RuntimeException {
		try {
			byte[] b = enCipher.doFinal(data);
			return b64Encrypter == null ? Hex.bytes2Hex(b) : b64Encrypter.encode(b);
		} catch (Exception e) {
			throw new RuntimeException("AESEncrypter encode error. Cause: " + e);
		}
	}

	public byte[] decode(String encryptText) throws RuntimeException {
		try {
			return deCipher.doFinal(b64Encrypter == null ? Hex.hexToBytes(encryptText) : b64Encrypter.decode(encryptText));
		} catch (Exception e) {
			throw new RuntimeException("AESEncrypter decode error. Cause: " + e);
		}
	}

	public void encodeFile(String file, String destFile) throws RuntimeException {
		InputStream is = null;
		OutputStream out = null;
		CipherInputStream cis = null;
		try {
			is = new FileInputStream(file);
			out = new FileOutputStream(destFile);
			cis = new CipherInputStream(is, enCipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
		} catch (Exception e) {
			throw new RuntimeException("AESEncrypt encodeFile error. Cause: " + e);
		} finally {
			StreamUtils.close(out, is, cis);
		}
	}

	public void encodeFileAsyn(String file, String destFile) throws RuntimeException, FileNotFoundException {
		InputStream is = new FileInputStream(file);
		CipherInputStream cis = new CipherInputStream(is, enCipher);
		OutputStream out = new FileOutputStream(destFile);
		Observable<byte[]> writer = Observable.create((subscriber) -> {
			byte[] buffer = new byte[1024];
			int r;
			try {
				while ((r = cis.read(buffer)) > 0) {
					subscriber.onNext(Arrays.copyOfRange(buffer, 0, r));
				}
			} catch (IOException ioe) {
				throw new RuntimeException("AESEncrypt encodeFileAsyn error. Cause: " + ioe);
			}
			subscriber.onComplete();
		});
		writer.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe((data) -> {
			try {
				out.write(data);
			} catch (IOException ioe) {
				throw new RuntimeException("AESEncrypt encodeFileAsyn error. Cause: " + ioe);
			}
		}, (e) -> {
			StreamUtils.close(out, is, cis);
			throw new RuntimeException("AESEncrypt encodeFileAsyn error. Cause: " + e);
		}, () -> {
			StreamUtils.close(out, is, cis);
		});
	}

	public void decodeFile(String file, String destFile) throws RuntimeException {
		InputStream is = null;
		OutputStream out = null;
		CipherOutputStream cos = null;
		try {
			is = new FileInputStream(file);
			out = new FileOutputStream(destFile);
			cos = new CipherOutputStream(out, deCipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = is.read(buffer)) >= 0) {
				cos.write(buffer, 0, r);
			}
		} catch (Exception e) {
			throw new RuntimeException("AESEncrypt decodeFile error. Cause: " + e);
		} finally {
			StreamUtils.close(is, out, cos);
		}
	}

	public void decodeFileAsyn(String file, String destFile) throws RuntimeException, FileNotFoundException {
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(destFile);
		CipherOutputStream cos = new CipherOutputStream(out, deCipher);
		Observable<byte[]> writer = Observable.create((subscriber) -> {
			byte[] buffer = new byte[1024];
			int r;
			try {
				while ((r = is.read(buffer)) > 0) {
					subscriber.onNext(Arrays.copyOfRange(buffer, 0, r));
				}
			} catch (IOException ioe) {
				throw new RuntimeException("AESEncrypt decodeFileAsyn error. Cause: " + ioe);
			}
			subscriber.onComplete();
		});
		writer.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe((data) -> {
			try {
				out.write(data);
			} catch (IOException ioe) {
				throw new RuntimeException("AESEncrypt decodeFileAsyn error. Cause: " + ioe);
			}
		}, (e) -> {
			StreamUtils.close(is, out, cos);
			throw new RuntimeException("AESEncrypt decodeFileAsyn error. Cause: " + e);
		}, () -> {
			StreamUtils.close(is, out, cos);
		});
	}

	/********************* static method ************************/

	public static AESEncrypter getInstance(String aesKey) {
		return getInstance(aesKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	public static AESEncrypter getInstance(String aesKey, boolean useHex) {
		if (StringUtil.isBlank(aesKey)) {
			return null;
		}
		String key = aesKey;
		AESEncrypter aesEncrypt = encryptMap.get(key);
		if (aesEncrypt == null) {
			aesEncrypt = new AESEncrypter(aesKey, useHex);
			encryptMap.put(key, aesEncrypt);
		}
		return aesEncrypt;
	}

	public static AESEncrypter getInstance(String aesKey, String b64Key) {
		if (StringUtil.isBlank(aesKey) || !B64Encrypter.checkAlphabet(b64Key)) {
			return null;
		}
		String key = aesKey + b64Key;
		AESEncrypter aesEncrypt = encryptMap.get(key);
		if (aesEncrypt == null) {
			aesEncrypt = new AESEncrypter(aesKey, b64Key);
			encryptMap.put(key, aesEncrypt);
		}
		return aesEncrypt;
	}

	public static AESEncrypter newInstance(String aesKey) {
		return newInstance(aesKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	public static AESEncrypter newInstance(String aesKey, boolean useHex) {
		if (StringUtil.isBlank(aesKey)) {
			return null;
		}
		return new AESEncrypter(aesKey, useHex);
	}

	public static AESEncrypter newInstance(String aesKey, String b64Key) {
		if (StringUtil.isBlank(aesKey) || !B64Encrypter.checkAlphabet(b64Key)) {
			return null;
		}
		return new AESEncrypter(aesKey, b64Key);
	}

	public static String encrypt(String aesKey, byte[] data) throws RuntimeException {
		if (StringUtil.isBlank(aesKey) || data == null)
			return null;
		return getInstance(aesKey).encode(data);
	}

	public static String encrypt(String aesKey, String b64Key, byte[] data) throws RuntimeException {
		if (StringUtil.isBlank(aesKey) || !B64Encrypter.checkAlphabet(b64Key) || data == null)
			return null;
		return getInstance(aesKey, b64Key).encode(data);
	}

	public static byte[] decrypt(String aesKey, String encryptText) throws RuntimeException {
		if (StringUtil.isBlank(aesKey) || StringUtil.isBlank(encryptText))
			return null;
		return getInstance(aesKey).decode(encryptText);
	}

	public static byte[] decrypt(String aesKey, String b64Key, String encryptText) throws RuntimeException {
		if (StringUtil.isBlank(aesKey) || StringUtil.isBlank(encryptText) || !B64Encrypter.checkAlphabet(b64Key))
			return null;
		return getInstance(aesKey, b64Key).decode(encryptText);
	}

	/*************** File encode **************/

	public static void encryptFile(String aesKey, String file, String destFile) throws RuntimeException {
		if (!StringUtil.isBlank(aesKey)) {
			getInstance(aesKey).encodeFile(file, destFile);
		}
	}

	public static void encryptFile(String aesKey, String b64Key, String file, String destFile) throws RuntimeException {
		if (!StringUtil.isBlank(aesKey) && B64Encrypter.checkAlphabet(b64Key)) {
			getInstance(aesKey, b64Key).encodeFile(file, destFile);
		}
	}

	public static void encryptFileAsyn(String aesKey, String file, String destFile) throws RuntimeException, FileNotFoundException {
		if (!StringUtil.isBlank(aesKey)) {
			getInstance(aesKey).encodeFileAsyn(file, destFile);
		}
	}

	public static void encryptFileAsyn(String aesKey, String b64Key, String file, String destFile) throws RuntimeException, FileNotFoundException {
		if (!StringUtil.isBlank(aesKey) && B64Encrypter.checkAlphabet(b64Key)) {
			getInstance(aesKey, b64Key).encodeFileAsyn(file, destFile);
		}
	}

	/*************** File decode **************/
	public static void decryptFile(String aesKey, String file, String destFile) throws RuntimeException {
		if (!StringUtil.isBlank(aesKey)) {
			getInstance(aesKey).decodeFile(file, destFile);
		}
	}

	public static void decryptFile(String aesKey, String b64Key, String file, String destFile) throws RuntimeException {
		if (!StringUtil.isBlank(aesKey) && B64Encrypter.checkAlphabet(b64Key)) {
			getInstance(aesKey, b64Key).decodeFile(file, destFile);
		}
	}

	public static void decryptFileAsyn(String aesKey, String file, String destFile) throws RuntimeException, FileNotFoundException {
		if (!StringUtil.isBlank(aesKey)) {
			getInstance(aesKey).decodeFileAsyn(file, destFile);
		}
	}

	public static void decryptFileAsyn(String aesKey, String b64Key, String file, String destFile) throws RuntimeException, FileNotFoundException {
		if (!StringUtil.isBlank(aesKey) && B64Encrypter.checkAlphabet(b64Key)) {
			getInstance(aesKey, b64Key).decodeFileAsyn(file, destFile);
		}
	}
}
