package io.github.shuoros.allAboutSpring.util;

public class PersianNumber {

	public final String DIGIT_ZERO = "۰";
	public final String DIGIT_ONE = "۱";
	public final String DIGIT_TWO = "۲";
	public final String DIGIT_THREE = "۳";
	public final String DIGIT_FOUR = "۴";
	public final String DIGIT_FIVE = "۵";
	public final String DIGIT_SIX = "۶";
	public final String DIGIT_SEVEN = "۷";
	public final String DIGIT_EIGHT = "۸";
	public final String DIGIT_NINE = "۹";

	public String english2Persian(int number) {
		return english2Persian(String.valueOf(number));
	}

	public String english2Persian(float number) {
		return english2Persian(String.valueOf(number));
	}

	public String english2Persian(String number) {
		String result = "";
		for (int i = 0; i < number.length(); i++) {
			switch (number.charAt(i)) {
			case '.':
				result += ".";
				break;
			case '0':
				result += DIGIT_ZERO;
				break;
			case '1':
				result += DIGIT_ONE;
				break;
			case '2':
				result += DIGIT_TWO;
				break;
			case '3':
				result += DIGIT_THREE;
				break;
			case '4':
				result += DIGIT_FOUR;
				break;
			case '5':
				result += DIGIT_FIVE;
				break;
			case '6':
				result += DIGIT_SIX;
				break;
			case '7':
				result += DIGIT_SEVEN;
				break;
			case '8':
				result += DIGIT_EIGHT;
				break;
			case '9':
				result += DIGIT_NINE;
				break;
			}
		}
		return result;
	}

	public String persian2English(String number) {
		String result = "";
		for (int i = 0; i < number.length(); i++) {
			switch (number.charAt(i)) {
			case '.':
				result += ".";
				break;
			case '۰':
				result += "0";
				break;
			case '۱':
				result += "1";
				break;
			case '۲':
				result += "2";
				break;
			case '۳':
				result += "3";
				break;
			case '۴':
				result += "4";
				break;
			case '۵':
				result += "5";
				break;
			case '۶':
				result += "6";
				break;
			case '۷':
				result += "7";
				break;
			case '۸':
				result += "8";
				break;
			case '۹':
				result += "9";
				break;
			}
		}
		return result;
	}
}
