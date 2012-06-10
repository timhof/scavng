package models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.yaml.snakeyaml.util.Base64Coder;

/**
 * Created by IntelliJ IDEA. User: rjudd Date: 2/7/12 Time: 1:24 PM
 */
public class Hash {

	protected static final int SALTYNESS = 599; // 829th prime number

	public static synchronized String SHA1(String text, String salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(salt.getBytes("UTF-8"));
		byte[] digested = md.digest(text.getBytes("UTF-8"));
		for (int i = 0; i < SALTYNESS; i++) {
			md.reset();
			digested = md.digest(digested);
		}
		return new String(Base64Coder.encode(digested));
	}

	public static synchronized String MD5Images(String text) {
		String returnValue;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			byte[] digested = md.digest(text.getBytes("UTF-8"));
			for (int i = 0; i < SALTYNESS; i++) {
				md.reset();
				digested = md.digest(digested);
			}
			returnValue = new String(Base64Coder.encode(digested));
			// Remove any illegal characters
			returnValue = returnValue.replaceAll("/", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return returnValue;
	}

	public static void main(String[] args) {
		System.out.println(MD5Images("test"));
	}

	public static synchronized byte[] generateSalt()
			throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return salt;
	}

	public static synchronized String generateSaltString()
			throws NoSuchAlgorithmException {
		byte[] salt = generateSalt();
		return new String(Base64Coder.encode(salt));
	}
}
