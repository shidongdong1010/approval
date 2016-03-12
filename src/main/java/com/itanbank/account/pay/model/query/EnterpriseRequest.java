package com.itanbank.account.pay.model.query;

import com.itanbank.account.pay.model.BasicModel;

/**
 * 商户账户查询请求参数
 * @author wn
 *
 */
public class EnterpriseRequest {

	/** 接口名称*/
	private String service = "ptp_mer_query";
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
	/** 查询的商户号*/
	private String query_mer_id = "";
	/** 被充值企业资金账户托管平台的账户类型：01:现金账户*/
	private String account_type = "01";
	
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
	public String getQuery_mer_id() {
		return query_mer_id;
	}
	public void setQuery_mer_id(String query_mer_id) {
		this.query_mer_id = query_mer_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	@Override
	public String toString() {
		return "Enterprise [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset + ", res_format="
				+ res_format + ", mer_id=" + mer_id + ", version=" + version + ", query_mer_id=" + query_mer_id
				+ ", account_type=" + account_type + "]";
	}
	
}
