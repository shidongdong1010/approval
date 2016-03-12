package com.itanbank.account.pay.model.query;

/**
 * 商户账户查询请求参数
 * @author wn
 *
 */
public class EnterpriseResult {

	/** 账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户*/
	private String account_state;
	/** 账户类型:01：商户现金账户02：商户手续费账户*/
	private String account_type;
	/** 商户结算账户的可用余额，单位为分*/
	private String balance;
	/** 商户编号*/
	private String mer_id;
	/** 联动开立的商户号*/
	private String query_mer_id;
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
	
	public String getAccount_state() {
		return account_state;
	}
	public void setAccount_state(String account_state) {
		this.account_state = account_state;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
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
	public String getQuery_mer_id() {
		return query_mer_id;
	}
	public void setQuery_mer_id(String query_mer_id) {
		this.query_mer_id = query_mer_id;
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
		return "EnterpriseResult [account_state=" + account_state + ", account_type=" + account_type + ", balance="
				+ balance + ", mer_id=" + mer_id + ", query_mer_id=" + query_mer_id + ", ret_code=" + ret_code
				+ ", ret_msg=" + ret_msg + ", sign_type=" + sign_type + ", version=" + version + ", sign=" + sign + "]";
	}
	
}
