package com.itanbank.account.pay.model.project;

import com.itanbank.account.pay.model.BasicModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 标的转账参数
 * @author wn
 *
 */
public class ProjectTransferRequest {

	/** 接口名称*/
	private String service = "project_transfer"; 
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
	private String ret_url = BasicModel.NOTIFY_SERVER+"project/transferReturn.html";
	/** 服务器异步通知页面路径*/
	private String notify_url = BasicModel.NOTIFY_SERVER+"project/transferNotify.html";
	/** 视图类型*/
	private String sourceV = ""; 
	/** 商户订单号*/
	private String order_id = ""; 
	/** 商户订单日期*/
	private String mer_date = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
	/** 标的号*/
	private String project_id = ""; 
	/** 标的账户号*/
	private String project_account_id = ""; 
	/** 业务类型*/
	private String serv_type = ""; 
	/** 转账方向*/
	private String trans_action = ""; 
	/** 转账方类型*/
	private String partic_type = ""; 
	/** 转账方账户类型*/
	private String partic_acc_type = ""; 
	/** 转账方用户号*/
	private String partic_user_id = ""; 
	/** 转账方账户号*/
	private String partic_account_id = ""; 
	/** 金额*/
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
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProject_account_id() {
		return project_account_id;
	}
	public void setProject_account_id(String project_account_id) {
		this.project_account_id = project_account_id;
	}
	public String getServ_type() {
		return serv_type;
	}
	public void setServ_type(String serv_type) {
		this.serv_type = serv_type;
	}
	public String getTrans_action() {
		return trans_action;
	}
	public void setTrans_action(String trans_action) {
		this.trans_action = trans_action;
	}
	public String getPartic_type() {
		return partic_type;
	}
	public void setPartic_type(String partic_type) {
		this.partic_type = partic_type;
	}
	public String getPartic_acc_type() {
		return partic_acc_type;
	}
	public void setPartic_acc_type(String partic_acc_type) {
		this.partic_acc_type = partic_acc_type;
	}
	public String getPartic_user_id() {
		return partic_user_id;
	}
	public void setPartic_user_id(String partic_user_id) {
		this.partic_user_id = partic_user_id;
	}
	public String getPartic_account_id() {
		return partic_account_id;
	}
	public void setPartic_account_id(String partic_account_id) {
		this.partic_account_id = partic_account_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "ProjectTransferRequest [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", ret_url=" + ret_url
				+ ", notify_url=" + notify_url + ", sourceV=" + sourceV + ", order_id=" + order_id + ", mer_date="
				+ mer_date + ", project_id=" + project_id + ", project_account_id=" + project_account_id
				+ ", serv_type=" + serv_type + ", trans_action=" + trans_action + ", partic_type=" + partic_type
				+ ", partic_acc_type=" + partic_acc_type + ", partic_user_id=" + partic_user_id + ", partic_account_id="
				+ partic_account_id + ", amount=" + amount + "]";
	} 
}
