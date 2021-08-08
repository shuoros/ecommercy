package io.github.shuoros.allAboutSpring.util;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

/**
 * This class generates random strings of arbitrary length from numbers,
 * lowercase letters, and uppercase letters.
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
public class RandomString {

	/**
	 * Generate a random string.
	 * 
	 * @return Generated random string.
	 * @since v1.0.0
	 */
	public String nextString() {
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}

	public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String lower = "abcdefghijklmnopqrstuvwxyz";

	public static final String digits = "0123456789";

	public static final String alphanum = upper + lower + digits;

	private final Random random;

	private final char[] symbols;

	private final char[] buf;

	public RandomString(int length, Random random, String symbols) {
		if (length < 1)
			throw new IllegalArgumentException();
		if (symbols.length() < 2)
			throw new IllegalArgumentException();
		this.random = Objects.requireNonNull(random);
		this.symbols = symbols.toCharArray();
		this.buf = new char[length];
	}

	/**
	 * Create an alphanumeric string generator.
	 * 
	 * @param length The desired length to generate a random string.
	 * @param random A <code>Random</code> class to generate random ints.
	 * @since v1.0.0
	 */
	public RandomString(int length, Random random) {
		this(length, random, alphanum);
	}

	/**
	 * Create an alphanumeric strings from a secure generator.
	 * 
	 * @param length The desired length to generate a random string.
	 * @since v1.0.0
	 */
	public RandomString(int length) {
		this(length, new SecureRandom());
	}

	/**
	 * Create session identifiers.
	 */
	public RandomString() {
		this(21);
	}

}
