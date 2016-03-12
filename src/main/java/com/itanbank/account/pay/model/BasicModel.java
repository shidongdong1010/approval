package com.itanbank.account.pay.model;

import java.util.ResourceBundle;

/**
 * 基础信息
 * @author wn
 *
 */
public class BasicModel {
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("application");

	/** 签名方式*/
	public static final String SIGN_TYPE = bundle.getString("ump.sign_type");
	/** 字符集编码*/
	public static final String CHARSET = bundle.getString("ump.charset");
	/** 响应格式*/
	public static final String RES_FORMAT = bundle.getString("ump.res_format");
	/** 商户编号*/
	public static final String MER_ID = bundle.getString("ump.mer_id");
	/** 版本号*/
	public static final String VERSION = bundle.getString("ump.version");
	/** 证件类型*/
	public static final String IDENTITY_TYPE = bundle.getString("ump.identity_type");
	/** 第三方回调服务地址*/
	public static final String NOTIFY_SERVER = bundle.getString("ump.notify_server");
	/** 成功返回码*/
	public static final String RET_CODE_SUCCESS = bundle.getString("ump.ret_code_success");
	

}
