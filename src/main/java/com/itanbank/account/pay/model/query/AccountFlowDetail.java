package com.itanbank.account.pay.model.query;

import java.io.Serializable;

/**
 * 账户流水查询请求参数
 * @author wn
 *
 */
public class AccountFlowDetail implements Serializable{

	/** 交易记账日期*/
	private String acc_check_date;
	/** 交易金额*/
	private String amount;
	/** 资金类型 01 订单交易金额02 交易手续费99其他*/
	private String amt_type;
	/** 该笔交易后余额*/
	private String balance;
	/** 借贷方向:
	 * 01入账（以账户类型为基准，进入账户的资金）
	 * 02出账（以账户类型为基准，从账户划出的资金）
	 * 99其他
	 */
	private String dc_mark;
	/** 商户订单日期 :商户发起的交易才有，联动内部的账户操作存在空的情况，商户不需要关注*/
	private String order_date;
	/** 商户订单号:商户发起的交易才有，联动内部的账户操作存在空的情况，商户不需要关注*/
	private String order_id;
	/** 交易日期*/
	private String trans_date;
	/** 交易状态:01成功02冲正99其他*/
	private String trans_state;
	/** 交易时间*/
	private String trans_time;
	/** 交易代码:
	 * 01充值
	 * 02提现
	 * 03标的转账
	 * 04转账
	 * 05提现失败退回
	 * 99 退费等其他交易
	 */
	private String trans_type;
	
	public String getAcc_check_date() {
		return acc_check_date;
	}
	public void setAcc_check_date(String acc_check_date) {
		this.acc_check_date = acc_check_date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmt_type() {
		return amt_type;
	}
	public void setAmt_type(String amt_type) {
		this.amt_type = amt_type;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getDc_mark() {
		return dc_mark;
	}
	public void setDc_mark(String dc_mark) {
		this.dc_mark = dc_mark;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	public String getTrans_state() {
		return trans_state;
	}
	public void setTrans_state(String trans_state) {
		this.trans_state = trans_state;
	}
	public String getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	@Override
	public String toString() {
		return "AccountFlowDetail [acc_check_date=" + acc_check_date + ", amount=" + amount + ", amt_type=" + amt_type
				+ ", balance=" + balance + ", dc_mark=" + dc_mark + ", order_date=" + order_date + ", order_id="
				+ order_id + ", trans_date=" + trans_date + ", trans_state=" + trans_state + ", trans_time="
				+ trans_time + ", trans_type=" + trans_type + "]";
	}
	
	
}
