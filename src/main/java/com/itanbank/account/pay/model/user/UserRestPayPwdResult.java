package com.itanbank.account.pay.model.user;

/**
 * 重置交易密码参数
 * @author wn
 *
 */
public class UserRestPayPwdResult {

	/** 加密方式*/
	private String sign_type;
	/** 响应格式*/
	private String res_format;
	/** 商户号*/
	private String mer_id;
	/** 版本号*/
	private String version;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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
	@Override
	public String toString() {
		return "UserRestPayPwdResult [sign_type=" + sign_type + ", res_format=" + res_format + ", mer_id=" + mer_id
				+ ", version=" + version + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg + "]";
	}
	
}
