package com.itanbank.account.pay.model.query;

import com.itanbank.account.pay.model.BasicModel;
import org.springframework.stereotype.Component;

/**
 * 用户查询请求参数组装
 * @author wn
 *
 */
@Component
public class UserRequest {

	/** 接口名称*/
	private String service = "user_search";
	/** 签名方式*/
	private String sign_type = BasicModel.SIGN_TYPE;
	/** 字符集编码*/
	private String charset = BasicModel.CHARSET;
	/** 响应格式*/
	private String res_format = BasicModel.RES_FORMAT;
	/** 商户编号*/
	private String mer_id = BasicModel.MER_ID;
	/** 版本号*/
	private String version = BasicModel.VERSION;
	/** 资金账户托管平台用户号*/
	private String user_id = "";
	/** 是否查询余额*/
	private String is_find_account = "01";
	/** 是否查询授权协议*/
	private String is_select_agreement = "";
	
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getIs_find_account() {
		return is_find_account;
	}
	public void setIs_find_account(String is_find_account) {
		this.is_find_account = is_find_account;
	}
	public String getIs_select_agreement() {
		return is_select_agreement;
	}
	public void setIs_select_agreement(String is_select_agreement) {
		this.is_select_agreement = is_select_agreement;
	}
	@Override
	public String toString() {
		return "User [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset + ", res_format="
				+ res_format + ", mer_id=" + mer_id + ", version=" + version + ", user_id=" + user_id
				+ ", is_find_account=" + is_find_account + ", is_select_agreement=" + is_select_agreement + "]";
	}
}
