package io.github.shuoros.allAboutSpring.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This class takes the password and salt and hashes them with the
 * PBKDF2WithHmacSHA512 algorithm.
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
public class PasswordEncoder {

	private String password;
	private String salt;

	public PasswordEncoder(String password, String salt) {
		this.password = password;
		this.salt = salt;
	}

	public String encode() throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeySpec spec = new PBEKeySpec(this.password.toCharArray(), this.salt.getBytes(), 32749, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		return new String(factory.generateSecret(spec).getEncoded());
	}

}
