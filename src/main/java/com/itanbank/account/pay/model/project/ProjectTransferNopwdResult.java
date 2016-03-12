package com.itanbank.account.pay.model.project;

/**
 * 无密标的转入返回结果
 * @author Administrator
 *
 */
public class ProjectTransferNopwdResult {

	/** 商户编号*/
	private String mer_id;
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
	/** 交易流水号*/
	private String trade_no;
	/** 对账日期*/
	private String mer_check_date;
	/** 订单日期*/
	private String mer_date;
	
	/**订单号**/
	private String order_id;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
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
	
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getMer_check_date() {
		return mer_check_date;
	}
	public void setMer_check_date(String mer_check_date) {
		this.mer_check_date = mer_check_date;
	}
	public String getMer_date() {
		return mer_date;
	}
	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}
	@Override
	public String toString() {
		return "ProjectTransferNopwdResult [mer_id=" + mer_id + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg
				+ ", sign_type=" + sign_type + ", version=" + version + ", sign=" + sign + ", trade_no=" + trade_no
				+ ", mer_check_date=" + mer_check_date + ", mer_date=" + mer_date + "]";
	}
}
