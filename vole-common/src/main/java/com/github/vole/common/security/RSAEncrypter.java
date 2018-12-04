package com.github.vole.common.security;

import com.github.vole.common.utils.StreamUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加/解密
 * 非对称算法；签名算法默认为SHA1WithRSA
 */
public class RSAEncrypter {
	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";
	private static Map<String, RSAEncrypter> encryptMap = new HashMap<String, RSAEncrypter>();

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private String workPattern = "ECB";
	private String paddingPattern = "PKCS1Padding";
	private String signatureAlgorithm = "SHA1WithRSA";
	private Cipher prvEnCipher = null;
	private Cipher prvDeCipher = null;
	private Cipher pbcEnCipher = null;
	private Cipher pbcDeCipher = null;
	private B64Encrypter b64Encrypter = null;
	private int MAX_ENCRYPT_BLOCK = 53;
	private int MAX_DECRYPT_BLOCK = 64;

	private RSAEncrypter(String publicKey, String privateKey) {
		init(publicKey, privateKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	private RSAEncrypter(String publicKey, String privateKey, boolean useHex) {
		init(publicKey, privateKey, useHex ? null : B64Encrypter.DEFAULT_ALPHABET);
	}

	private RSAEncrypter(String publicKey, String privateKey, String b64Key) {
		init(publicKey, privateKey, b64Key);
	}

	/**
	 * 
	 * @param publicKey
	 *            公钥字符串
	 * @param privateKey
	 *            私钥字符串
	 * @param b64Key
	 *            自定义base64字符串(不用则填Null)
	 * @param useHex
	 *            是否使用十六位字符表示(true则不使用Base64)
	 * @param useMD5
	 *            是否使用MD5withRSA签名(默认SHA1WithRSA)
	 */
	public RSAEncrypter(String publicKey, String privateKey, String b64Key, boolean useHex, boolean useMD5) {
		if (useMD5)
			signatureAlgorithm = SIGNATURE_ALGORITHM_MD5;
		if (!StringUtils.isBlank(b64Key) && !B64Encrypter.checkAlphabet(b64Key))
			throw new RuntimeException("Base64 key error format!");
		init(publicKey, privateKey, useHex ? null : StringUtils.isBlank(b64Key) ? B64Encrypter.DEFAULT_ALPHABET : b64Key);
	}

	/**
	 * 
	 * @param publicKey
	 * @param privateKey
	 * @param storeAlias
	 *            私钥文件user name
	 * @param storePassword
	 *            私钥文件 member pass
	 */
	private RSAEncrypter(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword) {
		init(publicKey, privateKey, storeAlias, storePassword, B64Encrypter.DEFAULT_ALPHABET);
	}

	private RSAEncrypter(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword, boolean useHex) {
		init(publicKey, privateKey, storeAlias, storePassword, useHex ? null : B64Encrypter.DEFAULT_ALPHABET);
	}

	private RSAEncrypter(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword, String b64Key) {
		init(publicKey, privateKey, storeAlias, storePassword, b64Key);
	}

	private void init(String publicKey, String privateKey, String b64Key) {
		if (!StringUtils.isBlank(b64Key))
			b64Encrypter = B64Encrypter.getInstance(b64Key);
		try {
			this.publicKey = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decodeTransfer(publicKey)));
			this.privateKey = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decodeTransfer(privateKey)));

			initCipher();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error initializing RSAEncrypter class. Cause: " + e);
		}
	}

	/**
	 * 
	 * @param publicKey
	 *            <br/>
	 *            CER/CRT
	 * @param privateKey
	 *            <br/>
	 *            默认JSK
	 */
	private void init(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword, String b64Key) {
		if (!StringUtils.isBlank(b64Key))
			b64Encrypter = B64Encrypter.getInstance(b64Key);
		BufferedInputStream prvBis = null;
		BufferedInputStream pbcBis = null;
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			prvBis = new BufferedInputStream(privateKey);

			char[] storePwdArr = storePassword.toCharArray();
			ks.load(prvBis, storePwdArr);
			this.privateKey = (RSAPrivateKey) ks.getKey(storeAlias, storePwdArr);

			pbcBis = new BufferedInputStream(publicKey);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(pbcBis);
			this.publicKey = (RSAPublicKey) cert.getPublicKey();

			initCipher();
		} catch (Exception e) {
			throw new RuntimeException("Error initializing RSAEncrypter class. Cause: " + e);
		} finally {
			StreamUtils.close(prvBis, privateKey);
			StreamUtils.close(pbcBis, publicKey);
		}
	}

	private void initCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		StringBuilder pattern = new StringBuilder(KEY_ALGORITHM);
		pattern.append("/").append(workPattern);
		pattern.append("/").append(paddingPattern);
		prvEnCipher = Cipher.getInstance(pattern.toString());
		prvEnCipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
		prvDeCipher = Cipher.getInstance(pattern.toString());
		prvDeCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		pbcEnCipher = Cipher.getInstance(pattern.toString());
		pbcEnCipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		pbcDeCipher = Cipher.getInstance(pattern.toString());
		pbcDeCipher.init(Cipher.DECRYPT_MODE, this.publicKey);
	}

	private String encodeTransfer(byte[] data) {
		return b64Encrypter == null ? Hex.bytes2Hex(data) : b64Encrypter.encode(data);
	}

	private byte[] decodeTransfer(String data) throws UnsupportedEncodingException {
		return b64Encrypter == null ? Hex.hexToBytes(data) : b64Encrypter.decode(data);
	}

	// 私钥对信息生成数字签名
	public String sign(byte[] data) throws Exception {
		Signature signature = Signature.getInstance(signatureAlgorithm);
		signature.initSign(privateKey);
		signature.update(data);
		return encodeTransfer(signature.sign());
	}

	// 校验数字签名
	public boolean verify(byte[] data, String sign) throws Exception {
		Signature signature = Signature.getInstance(signatureAlgorithm);
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(decodeTransfer(sign));
	}

	// 私钥加密
	public String encryptByPrivateKey(byte[] data) throws Exception {
		return encodeTransfer(doCipher(data, prvEnCipher, MAX_ENCRYPT_BLOCK));
	}

	// 私钥解密
	public byte[] decryptByPrivateKey(String encryptText) throws Exception {
		return doCipher(decodeTransfer(encryptText), prvDeCipher, MAX_DECRYPT_BLOCK);
	}

	// 公钥加密
	public String encryptByPublicKey(byte[] data) throws Exception {
		return encodeTransfer(doCipher(data, pbcEnCipher, MAX_ENCRYPT_BLOCK));
	}

	// 公钥解密
	public byte[] decryptByPublicKey(String encryptText) throws Exception {
		return doCipher(decodeTransfer(encryptText), pbcDeCipher, MAX_DECRYPT_BLOCK);
	}

	private byte[] doCipher(byte[] data, Cipher cipher, int MAX_LENGTH) throws Exception {
		int inputLen = data.length;
		int offSet = 0;
		byte[] cache;
		int i = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_LENGTH) {
					cache = cipher.doFinal(data, offSet, MAX_LENGTH);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_LENGTH;
			}
			return out.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			StreamUtils.close(out);
		}
	}

	/********************** static method ***********************/
	public static RSAEncrypter getInstance(String publicKey, String privateKey) {
		return getInstance(publicKey, privateKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	public static RSAEncrypter getInstance(String publicKey, String privateKey, String b64Key) {
		if (StringUtils.isBlank(privateKey) || StringUtils.isBlank(publicKey) || !B64Encrypter.checkAlphabet(b64Key)) {
			return null;
		}
		String key = new StringBuilder().append(privateKey).append("_").append(publicKey).append("_").append(b64Key).toString();
		RSAEncrypter rsaEncrypter = encryptMap.get(key);
		if (rsaEncrypter == null) {
			rsaEncrypter = new RSAEncrypter(publicKey, privateKey, b64Key);
			encryptMap.put(key, rsaEncrypter);
		}
		return rsaEncrypter;
	}

	public static RSAEncrypter getInstance(String publicKey, String privateKey, boolean useHex) {
		if (StringUtils.isBlank(privateKey) || StringUtils.isBlank(publicKey)) {
			return null;
		}
		StringBuilder key = new StringBuilder().append(privateKey).append("_").append(publicKey);
		if (!useHex)
			key.append("_").append(B64Encrypter.DEFAULT_ALPHABET);
		RSAEncrypter rsaEncrypter = encryptMap.get(key.toString());
		if (rsaEncrypter == null) {
			rsaEncrypter = new RSAEncrypter(publicKey, privateKey, useHex);
			encryptMap.put(key.toString(), rsaEncrypter);
		}
		return rsaEncrypter;
	}

	public static RSAEncrypter newInstance(String publicKey, String privateKey) {
		return new RSAEncrypter(publicKey, privateKey);
	}

	public static RSAEncrypter newInstance(String publicKey, String privateKey, boolean useHex) {
		return new RSAEncrypter(publicKey, privateKey, useHex);
	}

	public static RSAEncrypter newInstance(String publicKey, String privateKey, String b64Key) {
		return new RSAEncrypter(publicKey, privateKey, b64Key);
	}

	public static RSAEncrypter newInstance(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword) {
		return new RSAEncrypter(publicKey, privateKey, storeAlias, storePassword);
	}

	public static RSAEncrypter newInstance(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword, boolean useHex) {
		return new RSAEncrypter(publicKey, privateKey, storeAlias, storePassword, useHex);
	}

	public static RSAEncrypter newInstance(InputStream publicKey, InputStream privateKey, String storeAlias, String storePassword, String b64Key) {
		return new RSAEncrypter(publicKey, privateKey, storeAlias, storePassword, b64Key);
	}
}
