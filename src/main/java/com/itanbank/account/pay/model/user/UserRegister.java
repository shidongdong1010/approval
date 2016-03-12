package com.itanbank.account.pay.model.user;

import com.itanbank.account.pay.model.BasicModel;

/**
 * 个人用户注册请求参数
 * @author wn
 *
 */
public class UserRegister {

	/** 接口名称*/
	private String service = "mer_register_person";
	/** 签名方式*/
	private String sign_type = BasicModel.SIGN_TYPE;
	/** 字符集编码*/
	private String charset= BasicModel.CHARSET;
	/** 响应格式*/
	private String res_format= BasicModel.RES_FORMAT;
	/** 商户编号*/
	private String mer_id= BasicModel.MER_ID;
	/** 版本号*/
	private String version= BasicModel.VERSION;
	/** 商户用户标识*/
	private String mer_cust_id;  
	/** 用户姓名*/
	private String mer_cust_name;
	/** 证件类型*/ 
	private String identity_type = BasicModel.IDENTITY_TYPE; 
	/** 证件号*/
	private String identity_code;  
	/** 手机号*/
	private String mobile_id; 
	/** 邮箱*/
	private String email;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getRes_format() {
		return res_format;
	}
	public void setRes_format(String res_format) {
		this.res_format = res_format;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMer_cust_id() {
		return mer_cust_id;
	}
	public void setMer_cust_id(String mer_cust_id) {
		this.mer_cust_id = mer_cust_id;
	}
	public String getMer_cust_name() {
		return mer_cust_name;
	}
	public void setMer_cust_name(String mer_cust_name) {
		this.mer_cust_name = mer_cust_name;
	}
	public String getIdentity_type() {
		return identity_type;
	}
	public void setIdentity_type(String identity_type) {
		this.identity_type = identity_type;
	}
	public String getIdentity_code() {
		return identity_code;
	}
	public void setIdentity_code(String identity_code) {
		this.identity_code = identity_code;
	}
	public String getMobile_id() {
		return mobile_id;
	}
	public void setMobile_id(String mobile_id) {
		this.mobile_id = mobile_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserRegister [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", mer_cust_id="
				+ mer_cust_id + ", mer_cust_name=" + mer_cust_name + ", identity_type=" + identity_type
				+ ", identity_code=" + identity_code + ", mobile_id=" + mobile_id + ", email=" + email + "]";
	}
	
}
