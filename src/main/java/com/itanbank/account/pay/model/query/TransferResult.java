package com.itanbank.account.pay.model.query;

/**
 * 交易查询结果参数组装
 * @author wn
 *
 */
public class TransferResult {

	/** 交易金额*/
	private String amount;
	/** 业务类型:01充值02提现03标的转账04转账*/
	private String busi_type;
	/** 手续费*/
	private String com_amt;
	/** 手续费承担方类型*/
	private String com_amt_type;
	/** 对账日期*/
	private String mer_check_date;
	/** 商户订单日期*/
	private String mer_date;
	/** 商户编号*/
	private String mer_id;
	/** 商户唯一订单号*/
	private String order_id;
	/** 原交易金额*/
	private String orig_amt;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	/** 签名方式*/
	private String sign_type;
	/** 短信个数*/
	private String sms_num;
	/** 交易平台流水号*/
	private String trade_no;
	/** 交易状态：
	 *  01充值：
	 *	0初始、2成功、3失败、4不明、6其他
	 *	
	 *	02提现：
	 *	提现交易中间状态：0初始、1受理中、4不明（待确认）、12已冻结、13待解冻、14财务已审核、6其他（需人工查询跟进）
	 *	提现交易最终态：
	 *	成功：2成功
	 *	失败：3失败、5交易关闭（提现）、15财务审核失败
	 *	
	 *	03标的转账：
	 *	0初始、2成功、3失败、4不明、6其他
	 *	04转账：
	 *	0初始、2成功、3失败、4不明、6其他
	 */
	private String tran_state;
	/** 版本号*/
	private String version;
	/** 签名*/
	private String sign;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getCom_amt() {
		return com_amt;
	}
	public void setCom_amt(String com_amt) {
		this.com_amt = com_amt;
	}
	public String getCom_amt_type() {
		return com_amt_type;
	}
	public void setCom_amt_type(String com_amt_type) {
		this.com_amt_type = com_amt_type;
	}
	public String getMer_check_date() {
		return mer_check_date;
	}
	public void setMer_check_date(String mer_check_date) {
		this.mer_check_date = mer_check_date;
	}
	public String getMer_date() {
		return mer_date;
	}
	public void setMer_date(String mer_date) {
		this.mer_date = mer_date;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrig_amt() {
		return orig_amt;
	}
	public void setOrig_amt(String orig_amt) {
		this.orig_amt = orig_amt;
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
	public String getSms_num() {
		return sms_num;
	}
	public void setSms_num(String sms_num) {
		this.sms_num = sms_num;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getTran_state() {
		return tran_state;
	}
	public void setTran_state(String tran_state) {
		this.tran_state = tran_state;
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
	@Override
	public String toString() {
		return "TransferResult [amount=" + amount + ", busi_type=" + busi_type + ", com_amt=" + com_amt
				+ ", com_amt_type=" + com_amt_type + ", mer_check_date=" + mer_check_date + ", mer_date=" + mer_date
				+ ", mer_id=" + mer_id + ", order_id=" + order_id + ", orig_amt=" + orig_amt + ", ret_code=" + ret_code
				+ ", ret_msg=" + ret_msg + ", sign_type=" + sign_type + ", sms_num=" + sms_num + ", trade_no="
				+ trade_no + ", tran_state=" + tran_state + ", version=" + version + ", sign=" + sign + "]";
	}
	
}
