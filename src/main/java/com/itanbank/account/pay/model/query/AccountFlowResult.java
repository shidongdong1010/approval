package com.itanbank.account.pay.model.query;

import java.util.List;

/**
 * 账户流水查询请求参数
 * 
 * 注意：查询时间范围：开始时间和结束时间只能是相差30天
 * @author wn
 *
 */
public class AccountFlowResult {

	/** 商户编号*/
	private String mer_id;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	/** 签名方式*/
	private String sign_type;
	/** 总记录数*/
	private String total_num;
	/** 交易记录字符串 每个记录之间以| 分隔，每个字段之间用，分隔。值传递通过key=value的形式。*/
	private String trans_detail;
	/** 交易记录对象列表*/
	private List<AccountFlowDetail> details;
	/** 版本*/
	private String version;
	/** 签名*/
	private String sign;
	
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getTrans_detail() {
		return trans_detail;
	}
	public void setTrans_detail(String trans_detail) {
		this.trans_detail = trans_detail;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public List<AccountFlowDetail> getDetails(){
		return details;
	}
	public void setDetails(List<AccountFlowDetail> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		
		return "AccountFlowResult [mer_id=" + mer_id + ", ret_code=" + ret_code + ", ret_msg=" + ret_msg
				+ ", sign_type=" + sign_type + ", total_num=" + total_num + ", trans_detail=" + trans_detail
				+  ", version=" + version + ", sign=" + sign + "]";
	}
	
}
