package com.itanbank.account.pay.model.query;

import com.itanbank.account.pay.model.BasicModel;

public class ProjectRequest {

	/** 接口名称*/
	private String service="project_account_search"; 
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
	/** 商户端标的号*/
	private String project_id="";
	
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
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	@Override
	public String toString() {
		return "Project [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset + ", res_format="
				+ res_format + ", mer_id=" + mer_id + ", version=" + version + ", project_id=" + project_id + "]";
	} 
	
}
