package com.ws.spring.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ws.spring.controller.HomeController;

public class AESAlgorithm {

	private static SecretKeySpec keySpec;
	private static byte[] key;
	private static String secretKeyValue = "walkinsoft-2020";
	private static final Logger logger = LoggerFactory.getLogger(AESAlgorithm.class);

	public static void setKey(String myKey) {
		MessageDigest messageDigest = null;
		try {
			key = myKey.getBytes("UTF-8");
			messageDigest = MessageDigest.getInstance("SHA-1");
			key = messageDigest.digest(key);
			key = Arrays.copyOf(key, 16);

			keySpec = new SecretKeySpec(key, "AES");

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("Exception occured while encrypting : {}", e.getMessage(), e);
		}
	}

	public static String encrypt(String strToENcrypt, String secretKey) {
		try {

			if (null == secretKey) {
				secretKey = secretKeyValue;
			}
			setKey(secretKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToENcrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.error("Exception occured while doing encryption - {}", e.getMessage(), e);
		}
		return null;
	}

	public static String decript(String srtToDecrypt, String secretKey) {
		try {
			if (null == secretKey)
				secretKey = secretKeyValue;

			setKey(secretKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(srtToDecrypt)));
		} catch (Exception e) {
			logger.error("Exception occured while doing decryption - {}", e.getMessage(), e);
		}
		return null;
	}

}
