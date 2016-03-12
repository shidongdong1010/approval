package com.itanbank.account.pay.model.query;

/**
 * 标的查询结果参数
 * @author wn
 *
 */
public class ProjectResult {

	/** 标的余额*/
	private String balance;
	/** 商户编号*/
	private String mer_id;
	/** 标的账户号*/
	private String project_account_id;
	/** 标的账户状态：01正常02冻结*/
	private String project_account_state;
	/** 商户端标的号*/
	private String project_id;
	/** 标的账户状态：
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
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
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
	public String getProject_account_state() {
		return project_account_state;
	}
	public void setProject_account_state(String project_account_state) {
		this.project_account_state = project_account_state;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
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
		return "ProjectResult [balance=" + balance + ", mer_id=" + mer_id + ", project_account_id=" + project_account_id
				+ ", project_account_state=" + project_account_state + ", project_id=" + project_id + ", project_state="
				+ project_state + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg + ", sign_type=" + sign_type
				+ ", version=" + version + ", sign=" + sign + "]";
	}
	
}
