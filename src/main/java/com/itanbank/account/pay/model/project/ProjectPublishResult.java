package com.itanbank.account.pay.model.project;

/**
 * 发标结果参数
 * @author wn
 *
 */
public class ProjectPublishResult {

	/** 商户对账日期*/
	private String mer_check_date;
	/** 商户编号*/
	private String mer_id;
	/** 标的账户号*/
	private String project_account_id;
	/** 标的账户状态  建标成功92 建标失败93*/
	private String project_state;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	/** 签名方式*/
	private String sign_type;
	/** 版本号*/
	private String version;
	/** 签名*/
	private String sign;
	
	public String getMer_check_date() {
		return mer_check_date;
	}
	public void setMer_check_date(String mer_check_date) {
		this.mer_check_date = mer_check_date;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getProject_account_id() {
		return project_account_id;
	}
	public void setProject_account_id(String project_account_id) {
		this.project_account_id = project_account_id;
	}
	public String getProject_state() {
		return project_state;
	}
	public void setProject_state(String project_state) {
		this.project_state = project_state;
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
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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
		return "ProjectPublishResult [mer_check_date=" + mer_check_date + ", mer_id=" + mer_id + ", project_account_id="
				+ project_account_id + ", project_state=" + project_state + ", ret_code=" + ret_code + ", ret_msg="
				+ ret_msg + ", sign_type=" + sign_type + ", version=" + version + ", sign=" + sign + "]";
	}
	
}
