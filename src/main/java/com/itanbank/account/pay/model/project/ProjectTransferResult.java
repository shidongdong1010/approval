package com.itanbank.account.pay.model.project;

/**
 * 标的转账参数
 * @author wn
 *
 */
public class ProjectTransferResult {

	/** 签名*/
	private String sign;
	/** 平台返回流水号*/
	private String trade_no;
	/** 返回码*/
	private String ret_code;
	/** 商户订单日期*/
	private String mer_date;
	/** 资金账户托管平台对账日期*/
	private String mer_check_date;
	/** 商户编号*/
	private String mer_id;
	/** 签名方式*/
	private String sign_type;
	/** 返回信息*/
	private String ret_msg;
	/** 接口名称*/
	private String service;
	/** 商户唯一订单号*/
	private String order_id;
	/** 版本号*/
	private String version;
	
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
		return "ProjectTransferResult [sign=" + sign + ", trade_no=" + trade_no + ", ret_code=" + ret_code
				+ ", mer_date=" + mer_date + ", mer_check_date=" + mer_check_date + ", mer_id=" + mer_id
				+ ", sign_type=" + sign_type + ", ret_msg=" + ret_msg + ", service=" + service + ", order_id="
				+ order_id + ", version=" + version + "]";
	}
	
}
