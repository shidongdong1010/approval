package com.itanbank.account.pay.model.project;

import com.itanbank.account.pay.model.BasicModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发标参数组装
 * @author wn
 *
 */
public class ProjectPublish {

	/** 接口名称*/
	private String service = "mer_bind_project"; 
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
	/** 标的号*/
	private String project_id= ""; 
	/** 标的名称 命名规范：中文、数字、英文、下划线、逗号、点、空格、斜杠、冒号、中括号、减号（符号是半角）*/
	private String project_name= ""; 
	/** 标的金额  以分为单位*/
	private String project_amount= ""; 
	/** 标的有效期*/
	private String project_expire_date=new SimpleDateFormat("yyyyMMdd").format(new Date()); 
	/** 标的融资方平台用户号*/
	private String loan_user_id= ""; 
	/** 标的融资方平台账户号*/
	private String loan_account_id = ""; 
	/** 借款方账户类型 01:个人，02:商户，不传默认为个人*/
	private String loan_acc_type = ""; 
	/** 标的代偿方平台用户号*/
	private String warranty_user_id= ""; 
	/** 标的代偿方平台账户号*/
	private String warranty_account_id = "";
	
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
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_amount() {
		return project_amount;
	}
	public void setProject_amount(String project_amount) {
		this.project_amount = project_amount;
	}
	public String getProject_expire_date() {
		return project_expire_date;
	}
	public void setProject_expire_date(String project_expire_date) {
		this.project_expire_date = project_expire_date;
	}
	public String getLoan_user_id() {
		return loan_user_id;
	}
	public void setLoan_user_id(String loan_user_id) {
		this.loan_user_id = loan_user_id;
	}
	public String getLoan_account_id() {
		return loan_account_id;
	}
	public void setLoan_account_id(String loan_account_id) {
		this.loan_account_id = loan_account_id;
	}
	public String getLoan_acc_type() {
		return loan_acc_type;
	}
	public void setLoan_acc_type(String loan_acc_type) {
		this.loan_acc_type = loan_acc_type;
	}
	public String getWarranty_user_id() {
		return warranty_user_id;
	}
	public void setWarranty_user_id(String warranty_user_id) {
		this.warranty_user_id = warranty_user_id;
	}
	public String getWarranty_account_id() {
		return warranty_account_id;
	}
	public void setWarranty_account_id(String warranty_account_id) {
		this.warranty_account_id = warranty_account_id;
	}
	@Override
	public String toString() {
		return "ProjectPublish [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", project_id="
				+ project_id + ", project_name=" + project_name + ", project_amount=" + project_amount
				+ ", project_expire_date=" + project_expire_date + ", loan_user_id=" + loan_user_id
				+ ", loan_account_id=" + loan_account_id + ", loan_acc_type=" + loan_acc_type + ", warranty_user_id="
				+ warranty_user_id + ", warranty_account_id=" + warranty_account_id + "]";
	}
	
}
