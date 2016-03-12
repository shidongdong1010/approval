package com.itanbank.account.pay.model.query;

import com.itanbank.account.pay.model.BasicModel;

public class DowmLoanFileRequest {
	/** 接口名称*/
	private String service = "download_settle_file_p";
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
	/**对账日期*/
	private String settle_date_p2p = "";
	/**对账类型*/
	private String settle_type_p2p = "";
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
	public String getSettle_date_p2p() {
		return settle_date_p2p;
	}
	public void setSettle_date_p2p(String settle_date_p2p) {
		this.settle_date_p2p = settle_date_p2p;
	}
	public String getSettle_type_p2p() {
		return settle_type_p2p;
	}
	public void setSettle_type_p2p(String settle_type_p2p) {
		this.settle_type_p2p = settle_type_p2p;
	}
	@Override
	public String toString() {
		return "DowmLoanFileRequest [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", settle_date_p2p="
				+ settle_date_p2p + ", settle_type_p2p=" + settle_type_p2p + "]";
	}
}
