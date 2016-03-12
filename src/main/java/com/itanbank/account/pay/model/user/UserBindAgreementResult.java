package com.itanbank.account.pay.model.user;

/**
 * 用户签约协议
 * @author wn
 *
 */
public class UserBindAgreementResult {

	/** 商户编号*/
	private String mer_id;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	/** 接口名称*/
	private String service;
	/** 签名方式*/
	private String sign_type;
	/** 用户签约的协议列表信息*/
	private String user_bind_agreement_list;
	/** 联动平台用户号*/
	private String user_id;
	/** 版本号*/
	private String version;
	/** 签名*/
	private String sign;
	
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
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
	public String getUser_bind_agreement_list() {
		return user_bind_agreement_list;
	}
	public void setUser_bind_agreement_list(String user_bind_agreement_list) {
		this.user_bind_agreement_list = user_bind_agreement_list;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "UserBindAgreementResult [mer_id=" + mer_id + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg
				+ ", service=" + service + ", sign_type=" + sign_type + ", user_bind_agreement_list="
				+ user_bind_agreement_list + ", user_id=" + user_id + ", version=" + version + ", sign=" + sign + "]";
	}
	
}
