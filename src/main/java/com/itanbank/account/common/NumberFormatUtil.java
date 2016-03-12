package com.itanbank.account.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormatUtil {
	
	/**
	 * 将字符串转化为千分位不带小数
	 * @param s
	 * @return
	 */
	public static String getNumberInteger(String s){
		NumberFormat formatter = new DecimalFormat("###,###");  
	    return formatter.format(getValNum(s).longValue())+"";
	}
	
	/**
	 * 将字符串转化为BigDecimal
	 * @param s
	 * @return
	 */
	public static BigDecimal getValNum(String s){
		BigDecimal b = new BigDecimal(s);
		return b;
	}
	
	/**
	 * 将字符串转化为千分位带两位小数
	 * @param s
	 * @return
	 */
	public static String getNumberTwoDecimal(String s){
		NumberFormat formatter = new DecimalFormat("###,##0.##");  
	    return formatter.format(getValNum(s))+"";
	}
	
	
}
