package com.itanbank.account.pay.model.user;

/**
 * 用户注册结果
 * @author admin
 *
 */
public class UserRegisterResult{

	/** 资金账户托管平台账户号*/
	private String account_id;
	/** 商户编号*/
	private String mer_id;
	/** 开户日期*/
	private String reg_date;
	/** 签名方式*/
	private String sign_type;
	/** 资金账户托管平台用户号*/
	private String user_id;
	/** 版本号*/
	private String version;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	/** 签名*/
	private String sign;
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "MerRegisterPerson [account_id=" + account_id + ", mer_id="
				+ mer_id + ", reg_date=" + reg_date + ", sign_type="
				+ sign_type + ", user_id=" + user_id + ", version=" + version
				+ ", ret_code=" + ret_code + ", ret_msg=" + ret_msg + ", sign="
				+ sign + "]";
	}
	
}
