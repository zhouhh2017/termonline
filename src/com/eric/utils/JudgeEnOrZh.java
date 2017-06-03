package com.eric.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeEnOrZh {
	 public static boolean isEnglish(String charaString){

	      return charaString.matches("^[a-zA-Z| ]*");

	    }

	  public static boolean isChinese(String str){

	      String regEx = "[\\u4e00-\\u9fa5]+";

	      Pattern p = Pattern.compile(regEx);

	      Matcher m = p.matcher(str);

	     if(m.find())

	       return true;

	     else

	       return false;

	   }
}
