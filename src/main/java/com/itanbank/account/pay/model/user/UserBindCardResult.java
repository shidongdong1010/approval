package com.itanbank.account.pay.model.user;

/**
 * 绑卡结果
 * @author admin
 *
 */
public class UserBindCardResult {

	/** 签名*/
	private String sign;
	/** 返回码*/
	private String ret_code;
	/** 商户订单日期*/
	private String mer_date;
	/** 商户编号*/
	private String mer_id;
	/** 签名类型*/
	private String sign_type;
	/** 返回信息*/
	private String ret_msg;
	/** */
	private String service;
	/** 参数字符编码集*/
	private String charset;
	/** 联动平台用户号*/
	private String user_id;
	/** 商户订单号*/
	private String order_id;
	/** 版本号*/
	private String version;
	
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getMer_date() {
		return mer_date;
	}
	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "MerBindCard [sign=" + sign + ", ret_code=" + ret_code
				+ ", mer_date=" + mer_date + ", mer_id=" + mer_id
				+ ", sign_type=" + sign_type + ", ret_msg=" + ret_msg
				+ ", service=" + service + ", charset=" + charset
				+ ", user_id=" + user_id + ", order_id=" + order_id
				+ ", version=" + version + "]";
	}
	
}
