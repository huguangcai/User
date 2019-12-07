package com.ysxsoft.common_base.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinYinUtils {
	/**
	 * 拼音首字母转换
	 *
	 * @param word
	 * @return
	 */
	public static String getFirst(String word) {
		if (word == null) {
			return "#";
		}
		String result = "";
		char[] c = word.toCharArray();
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			if (c.length > 0) {
				if (c[0] > 128) {
					result = PinyinHelper.toHanyuPinyinStringArray(c[0], outputFormat)[0];
					result = String.valueOf(result.charAt(0));
				} else {
					Pattern pattern = Pattern.compile("^[a-zA-Z]$");
					Matcher matcher = pattern.matcher(String.valueOf(c[0]));
					if (matcher.matches()) {
						result = String.valueOf(String.valueOf(c[0]).toUpperCase());
					} else {
						result = "#";
					}
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
			badHanyuPinyinOutputFormatCombination.printStackTrace();
		}
		return result;
	}
}
