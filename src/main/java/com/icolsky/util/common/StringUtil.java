package com.icolsky.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FuChang Liu
 */
public class StringUtil {

    /**
     * 判断值是否为空
     */
    public static boolean isEmpty(Object value){
        if(value == null){
            return true;
        }if(value instanceof String){
            return ((String) value).isEmpty() || "null".equals(value) || "Null".equals(value);
        }
        return false;
    }


    /**
     * 去掉后面指定字符
     */
    public static String trimEndChar(String strInput, String strChar) {
        if (isEmpty(strInput) || isEmpty(strChar)) {
            return "";
        }
        while (true) {
            if (strInput.endsWith(strChar)) {
                strInput = strInput.substring(0, strInput.length() - 1);
            } else {
                return strInput;
            }
        }
    }

    /**
     * 字符串正则判断
     * @param regex
     * @param str
     * @return
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

	// 返回指定长度的随机数
	public static String getRandom(int nLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nLength; i++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}
    public static void main(String[] args){
        System.out.println(isEmpty("null"));
    }
}
