package com.itanbank.account.pay.model.user;

import com.itanbank.account.pay.model.BasicModel;

/**
 * 用户解约
 * @author wn
 *
 */
public class UserUnbindAgreement {

	/** 接口名称*/
	private String service = "mer_unbind_agreement";
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
	private String ret_url = BasicModel.NOTIFY_SERVER+"user/unbindAgreementReturn.html"; 
	/** 服务器异步通知页面路径*/
	private String notify_url = BasicModel.NOTIFY_SERVER+"user/unbindAgreementNotify.html"; 
	/** 视图类型-传入HTML5为访问手机页面，web不需要传入*/
	private String sourceV = ""; 
	/** 联动平台用户号-个人用户注册时，平台响应用户号*/
	private String user_id; 
	/** 托管平台账户号*/
	private String account_id = ""; 
	/** 用户需签约的协议列表*/
	private String user_unbind_agreement_list;
	
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
	public String getUser_unbind_agreement_list() {
		return user_unbind_agreement_list;
	}
	public void setUser_unbind_agreement_list(String user_unbind_agreement_list) {
		this.user_unbind_agreement_list = user_unbind_agreement_list;
	}
	@Override
	public String toString() {
		return "UserUnbindAgreement [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", ret_url=" + ret_url
				+ ", notify_url=" + notify_url + ", sourceV=" + sourceV + ", user_id=" + user_id + ", account_id="
				+ account_id + ", user_unbind_agreement_list=" + user_unbind_agreement_list + "]";
	}
}
