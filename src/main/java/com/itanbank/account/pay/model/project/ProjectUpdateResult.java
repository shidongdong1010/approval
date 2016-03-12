package com.itanbank.account.pay.model.project;
/**
 * 标的更新请求参数
 * @author wn
 *
 */
public class ProjectUpdateResult {
	/** 商户对账日期 YYYYMMDD*/
	private String mer_check_date;
	/** 商户编号*/
	private String mer_id;
	/** 标的账户状态
	 * 取消-1
	 * 初始90
	 * 建标中91
	 * 建标成功92
	 * 建标失败93
	 * 标的锁定94
	 * 开标0
	 * 投资中1
	 * 还款中2
	 * 已还款3
	 * 结束4
	 * 满标5（目前不支持）
	 * 流标6（目前不支持）
	 */
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
		return "ProjectUpdateResult [mer_check_date=" + mer_check_date + ", mer_id=" + mer_id + ", project_state="
				+ project_state + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg + ", sign_type=" + sign_type
				+ ", version=" + version + ", sign=" + sign + "]";
	}
	
}
