package com.itanbank.account.pay.model.pay;

/**
 * 个人客户充值接口结果
 * @author wn
 *
 */
public class RechargePersonResult {

	/** 平台返回流水号*/
	private String trade_no;
	/** 手续费承担方类型*/
	private String com_amt_type;
	/** 商户订单日期*/
	private String mer_date;
	/** 签名方式*/
	private String sign_type;
	/** 返回信息*/
	private String ret_msg;
	/** 商户订单号*/
	private String order_id;
	/** 手续费*/
	private String com_amt;
	/** 版本号*/
	private String version;
	/** 签名*/
	private String sign;
	/** 余额*/
	private String balance;
	/** 返回码*/
	private String ret_code;
	/** 商户编号*/
	private String mer_id;
	/** 资金账户托管平台对账日期*/
	private String mer_check_date;
	/** 接口名称*/
	private String service;
	
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getCom_amt_type() {
		return com_amt_type;
	}
	public void setCom_amt_type(String com_amt_type) {
		this.com_amt_type = com_amt_type;
	}
	public String getMer_date() {
		return mer_date;
	}
	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCom_amt() {
		return com_amt;
	}
	public void setCom_amt(String com_amt) {
		this.com_amt = com_amt;
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
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getMer_check_date() {
		return mer_check_date;
	}
	public void setMer_check_date(String mer_check_date) {
		this.mer_check_date = mer_check_date;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	@Override
	public String toString() {
		return "RechargePersonResult [trade_no=" + trade_no + ", com_amt_type=" + com_amt_type + ", mer_date="
				+ mer_date + ", sign_type=" + sign_type + ", ret_msg=" + ret_msg + ", order_id=" + order_id
				+ ", com_amt=" + com_amt + ", version=" + version + ", sign=" + sign + ", balance=" + balance
				+ ", ret_code=" + ret_code + ", mer_id=" + mer_id + ", mer_check_date=" + mer_check_date + ", service="
				+ service + "]";
	}
	
	
}
