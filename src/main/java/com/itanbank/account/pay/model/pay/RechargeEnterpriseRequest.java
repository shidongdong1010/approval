package com.itanbank.account.pay.model.pay;

import com.itanbank.account.pay.model.BasicModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 企业客户充值接口请求参数
 * @author wn
 *
 */
public class RechargeEnterpriseRequest {

	/** 接口名称*/
	private String service = "mer_recharge";
	/** 签名方式*/
	private String sign_type = BasicModel.SIGN_TYPE;
	/** 字符集编码*/
	private String charset= BasicModel.CHARSET;
	/** 响应格式*/
	private String res_format= BasicModel.RES_FORMAT;
	/** 商户编号*/
	private String mer_id= BasicModel.MER_ID;
	/** 版本号*/
	private String version= BasicModel.VERSION;
	/** 页面跳转同步通知页面路径*/
	private String ret_url = BasicModel.NOTIFY_SERVER+"pay/recharge/enterprise/return.html";
	/** 服务器异步通知页面路径*/
	private String notify_url = BasicModel.NOTIFY_SERVER+"pay/recharge/enterprise/notify.html";
	/** 商户订单号*/
	private String order_id = "";
	/** 商户订单日期*/
	private String mer_date = new SimpleDateFormat("yyyyMMdd").format(new Date());
	/** 支付方式*/
	private String pay_type = "";
	/** 被充值企业资金账户托管平台商户号*/
	private String recharge_mer_id = "";
	/** 被充值企业资金账户托管平台的账户号*/
	private String account_id = "";
	/** 被充值企业资金账户托管平台的账户类型 01：现金账户*/
	private String account_type = "01";
	/** 充值金额*/
	private String amount = "";
	/** 默认银行*/
	private String gate_id = "";
	/** 用户IP地址*/
	private String user_ip = "";
	/** 手续费承担方类型 1 前向手续费：交易方承担
	 2 前向手续费：平台商户（手续费账户）承担
	 */
	private String com_amt_type="2";
	
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
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
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
	public String getRet_url() {
		return ret_url;
	}
	public void setRet_url(String ret_url) {
		this.ret_url = ret_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMer_date() {
		return mer_date;
	}
	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getRecharge_mer_id() {
		return recharge_mer_id;
	}
	public void setRecharge_mer_id(String recharge_mer_id) {
		this.recharge_mer_id = recharge_mer_id;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getGate_id() {
		return gate_id;
	}
	public void setGate_id(String gate_id) {
		this.gate_id = gate_id;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getCom_amt_type() {
		return com_amt_type;
	}
	public void setCom_amt_type(String com_amt_type) {
		this.com_amt_type = com_amt_type;
	}
	@Override
	public String toString() {
		return "RechargeEnterprise [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", ret_url=" + ret_url
				+ ", notify_url=" + notify_url + ", order_id=" + order_id + ", mer_date=" + mer_date + ", pay_type="
				+ pay_type + ", recharge_mer_id=" + recharge_mer_id + ", account_id=" + account_id + ", account_type="
				+ account_type + ", amount=" + amount + ", gate_id=" + gate_id + ", user_ip=" + user_ip
				+ ", com_amt_type=" + com_amt_type + "]";
	}
	
}
