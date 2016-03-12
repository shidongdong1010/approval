package com.itanbank.account.pay.model.project;

import com.itanbank.account.pay.model.BasicModel;

/**
 * 标的更新请求参数
 * @author wn
 *
 */
public class ProjectUpdateRequest {
	/** 接口名称*/
	private String service = "mer_update_project";
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
	private String project_id = "";
	/** 标的更新类型 
	 * 01：更新标的
	 * 02：标的融资人 即为借款人，借款人不一定是资金使用方（注意：仅限建标状态下可以替换。）
	 * 03：标的代偿方 
	 * 04：标的资金使用方 即为标的资金使用方，个人和企业均支持
	 * 每次操作只能选择一种更新类型；
	 */
	private String change_type = "";
	/** 标的状态
	 *  取值范围：0：开标、1：投标中、2：还款中、3：已还款、4：结束（前提条件：余额为0）建标后，标的状态为[建标成功]，由商户设定为开标状态，然后在更新为投标中才可以投资；change_type=01时，才使用
	 */
	private String project_state = "";
	/** 标的名称*/
	private String project_name = "";
	/** 标的金额*/
	private String project_amount = "";
	/** 标的有效期*/
	private String project_expire_date = "";
	/** 关系人操作类型*/
	private String option_type = "";
	/** 标的融资人资金账户托管平台用户号*/
	private String loan_user_id = "";
	/** 资金账户托管平台的账户号*/
	private String loan_account_id = "";
	/** 借款方账户类型*/
	private String loan_acc_type = "";
	/** 标的代偿方平台用户号*/
	private String warranty_user_id = "";
	/** 资金账户托管平台的账户号*/
	private String warranty_account_id = "";
	/** 标的资金使用方平台用户号*/
	private String receive_user_id = "";
	/** 资金账户托管平台的账户号*/
	private String receive_account_id = "";
	
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
	public String getChange_type() {
		return change_type;
	}
	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}
	public String getProject_state() {
		return project_state;
	}
	public void setProject_state(String project_state) {
		this.project_state = project_state;
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
	public String getOption_type() {
		return option_type;
	}
	public void setOption_type(String option_type) {
		this.option_type = option_type;
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
	public String getReceive_user_id() {
		return receive_user_id;
	}
	public void setReceive_user_id(String receive_user_id) {
		this.receive_user_id = receive_user_id;
	}
	public String getReceive_account_id() {
		return receive_account_id;
	}
	public void setReceive_account_id(String receive_account_id) {
		this.receive_account_id = receive_account_id;
	}
	@Override
	public String toString() {
		return "ProjectUpdate [service=" + service + ", sign_type=" + sign_type + ", charset=" + charset
				+ ", res_format=" + res_format + ", mer_id=" + mer_id + ", version=" + version + ", project_id="
				+ project_id + ", change_type=" + change_type + ", project_state=" + project_state + ", project_name="
				+ project_name + ", project_amount=" + project_amount + ", project_expire_date=" + project_expire_date
				+ ", option_type=" + option_type + ", loan_user_id=" + loan_user_id + ", loan_account_id="
				+ loan_account_id + ", loan_acc_type=" + loan_acc_type + ", warranty_user_id=" + warranty_user_id
				+ ", warranty_account_id=" + warranty_account_id + ", receive_user_id=" + receive_user_id
				+ ", receive_account_id=" + receive_account_id + "]";
	}

}
