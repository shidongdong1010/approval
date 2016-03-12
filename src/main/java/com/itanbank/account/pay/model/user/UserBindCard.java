package com.itanbank.account.pay.model.user;

import com.itanbank.account.pay.model.BasicModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户绑定银行卡请求参数
 * @author wn
 *
 */
public class UserBindCard {

	/** 接口名称*/
	private String service = "ptp_mer_bind_card";
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
	private String ret_url = BasicModel.NOTIFY_SERVER+"user/bindCardReturn.html";
	/** 服务器异步通知页面路径*/
	private String notify_url = BasicModel.NOTIFY_SERVER+"user/bindCardNotify.html";
	/** 视图类型-传入HTML5为访问手机页面，web不需要传入*/
	private String sourceV = "";
	/** 商户订单号-支持数字英文*/
	private String order_id;
	/** 商户订单日期-商户生成订单的日期，格式YYYYMMDD*/
	private String mer_date = new SimpleDateFormat("yyyyMMdd").format(new Date());
	/** 联动平台用户号-个人用户注册时，平台响应用户号*/
	private String user_id;
	/** 卡号*/
	private String card_id;
	/** 开卡户名*/
	private String account_name;
	/** 证件类型-只支持身份证*/
	private String identity_type="IDENTITY_CARD";
	/** 证件号-需与用户姓名匹配*/
	private String identity_code;
	/** 联行号- 接口预留字段，目前无需关注*/
	private String cnaps_code = "";
	/** 开卡地区- 接口预留字段，目前无需关注*/
	private String account_area = "";
	/** 开户支行名称 -接口预留字段，目前无需关注*/
	private String card_branch_name = "";
	/** 快捷协议标志 -0 不开通快捷支付，1 强制开通*/
	private String is_open_fastPayment;
	
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getIdentity_type() {
		return identity_type;
	}
	public void setIdentity_type(String identity_type) {
		this.identity_type = identity_type;
	}
	public String getIdentity_code() {
		return identity_code;
	}
	public void setIdentity_code(String identity_code) {
		this.identity_code = identity_code;
	}
	public String getCnaps_code() {
		return cnaps_code;
	}
	public void setCnaps_code(String cnaps_code) {
		this.cnaps_code = cnaps_code;
	}
	public String getAccount_area() {
		return account_area;
	}
	public void setAccount_area(String account_area) {
		this.account_area = account_area;
	}
	public String getCard_branch_name() {
		return card_branch_name;
	}
	public void setCard_branch_name(String card_branch_name) {
		this.card_branch_name = card_branch_name;
	}
	public String getIs_open_fastPayment() {
		return is_open_fastPayment;
	}
	public void setIs_open_fastPayment(String is_open_fastPayment) {
		this.is_open_fastPayment = is_open_fastPayment;
	}
	@Override
	public String toString() {
		return "UserBindCard [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", ret_url=" + ret_url
				+ ", notify_url=" + notify_url + ", sourceV=" + sourceV + ", order_id=" + order_id + ", mer_date="
				+ mer_date + ", user_id=" + user_id + ", card_id=" + card_id + ", account_name=" + account_name
				+ ", identity_type=" + identity_type + ", identity_code=" + identity_code + ", cnaps_code=" + cnaps_code
				+ ", account_area=" + account_area + ", card_branch_name=" + card_branch_name + ", is_open_fastPayment="
				+ is_open_fastPayment + "]";
	}
}
