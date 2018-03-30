package com.changxiang.vod.common.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceBlank {
	public static String replaceBlankAll(String str) {

		String dest = "";

		if (str != null) {
			Pattern p= Pattern.compile("[.,，。？\"\\?!:']");//增加对应的标点，英文标点符号
//			Pattern p = Pattern.compile( "\r|\n" );

			Matcher m = p.matcher( str );

			dest = m.replaceAll( "" );

		}
		return dest;

	}
}
