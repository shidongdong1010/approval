package com.itanbank.account.pay.model.pay;

import com.itanbank.account.pay.model.BasicModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 个人客户提现请求参数
 * @author wn
 *
 */
public class WithdrawPersonRequest {

	/** 接口名称*/
	private String service = "cust_withdrawals";
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
	private String ret_url = BasicModel.NOTIFY_SERVER+"pay/withdrawals/person/return.html";
	/** 服务器异步通知页面路径*/
	private String notify_url = BasicModel.NOTIFY_SERVER+"pay/withdrawals/person/notify.html";
	/** 申请成功后台通知标识 默认不通知 0不通知1通知*/
	private String apply_notify_flag = "1";
	/** 试图类型*/
	private String sourceV = "";
	/** 商户订单号*/
	private String order_id = "";
	/** 商户订单日期*/
	private String mer_date = new SimpleDateFormat("yyyyMMdd").format(new Date());
	/** 资金账户托管平台的用户号*/
	private String user_id = "";
	/** 资金账户托管平台的账户号*/
	private String account_id = "";
	/** 提现金额*/
	private String amount = "";
	
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
	public String getApply_notify_flag() {
		return apply_notify_flag;
	}
	public void setApply_notify_flag(String apply_notify_flag) {
		this.apply_notify_flag = apply_notify_flag;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSourceV() {
		return sourceV;
	}
	public void setSourceV(String sourceV) {
		this.sourceV = sourceV;
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "WithdrawalsPerson [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", ret_url=" + ret_url
				+ ", notify_url=" + notify_url + ", apply_notify_flag=" + apply_notify_flag + ", sourceV=" + sourceV
				+ ", order_id=" + order_id + ", mer_date=" + mer_date + ", user_id=" + user_id + ", account_id="
				+ account_id + ", amount=" + amount + "]";
	}
	
}
