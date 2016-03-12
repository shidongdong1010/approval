package com.itanbank.account.pay.model.query;

import com.itanbank.account.pay.model.BasicModel;
import org.springframework.stereotype.Component;

/**
 * 账户流水查询请求参数
 * @author wn
 *
 */
@Component
public class AccountFlow {

	/** 接口名称*/
	private String service= "transeq_search";
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
	/** 资金账户平台账户号*/
	private String account_id= "";
	/** 转账方用户号*/
	private String partic_user_id= "";
	/** 账户类型 01：个人 02：商户03：标的*/
	private String account_type= "";
	/** 交易开始日期*/
	private String start_date= "";
	/** 交易结束日期*/
	private String end_date= ""; 
	/** 起始页*/
	private String page_num= "";
	
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
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getPartic_user_id() {
		return partic_user_id;
	}
	public void setPartic_user_id(String partic_user_id) {
		this.partic_user_id = partic_user_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getPage_num() {
		return page_num;
	}
	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}
	@Override
	public String toString() {
		return "AccountFlow [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset + ", res_format="
				+ res_format + ", mer_id=" + mer_id + ", version=" + version + ", account_id=" + account_id
				+ ", partic_user_id=" + partic_user_id + ", account_type=" + account_type + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", page_num=" + page_num + "]";
	}
	
}
