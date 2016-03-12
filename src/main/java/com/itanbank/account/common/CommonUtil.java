package com.itanbank.account.common;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	/**
	 * 处理用户身份证显示
	 * @param idCard
	 * @return
	 */
	public static String dealIdCard(String idCard){
		if(StringUtils.isBlank(idCard)) return "";
		StringBuffer idCardNew = new StringBuffer();
		idCardNew.append(idCard.substring(0, 5));
		idCardNew.append("***********");
		idCardNew.append(idCard.substring(16));
		return idCardNew.toString();
	}

	/**
	 * 处理卡号后四位
	 * @param bankNo
	 * @return
	 */
	public static String dealBankNo(String bankNo){
		if(StringUtils.isBlank(bankNo)) return "";
		StringBuffer bankNoNew = new StringBuffer();
		bankNoNew.append(bankNo.substring(0, 6));
		bankNoNew.append("*****");
		bankNoNew.append(bankNo.substring(bankNo.length()-4));
		return bankNoNew.toString();
	}

	/**
	 * 处理姓名
	 * @param realName
	 * @return
	 */
	public static String dealRealName(String realName){
		if(StringUtils.isBlank(realName)) return "";
		return StringUtils.substring(realName, realName.length() - 1);
	}

	/**
	 * 处理手机
	 * @param mobile
	 * @return
	 */
	public static String dealMobile(String mobile){
		if(StringUtils.isBlank(mobile)) return "";
		StringBuffer mobileNew = new StringBuffer();
		mobileNew.append(mobile.substring(0, 3));
		mobileNew.append("*****");
		mobileNew.append(mobile.substring(8));
		return mobileNew.toString();
	}
}
