package com.itanbank.account.pay.model.query;

import com.itanbank.account.pay.model.BasicModel;

/**
 * 交易查询请求参数组装
 * @author wn
 *
 */
public class TransferRequest {
	
	public static  String  BUSI_TYPE_RECHARGE = "01";
	public static  String  BUSI_TYPE_WITH_DRAWALS = "02";
	public static  String  BUSI_TYPE_PROJECT_ACCOUNT = "03";
	public static  String  BUSI_TYPE_ACCOUNT = "04";
	/** 接口名称*/
	private String service = "transfer_search";
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
	/** 商户订单号*/
	private String order_id = "";
	/** 商户订单日期*/
	private String mer_date = "";
	/** 交易类型：01充值02提现03标的转账04转账 */
	private String busi_type = "";
	
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
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	@Override
	public String toString() {
		return "Transfer [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset + ", res_format="
				+ res_format + ", mer_id=" + mer_id + ", version=" + version + ", order_id=" + order_id + ", mer_date="
				+ mer_date + ", busi_type=" + busi_type + "]";
	}
	
}
