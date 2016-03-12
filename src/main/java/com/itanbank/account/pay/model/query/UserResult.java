package com.itanbank.account.pay.model.query;
/**
 * 用户查询请求参数组装
 * @author wn
 *
 */
public class UserResult {

	/** 资金账户托管平台的账户号*/
	private String account_id;
	/** 账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户*/
	private String account_state;
	/** 账户余额，以分为单位*/
	private String balance;
	/** 提现银行卡 遮挡处理，返回卡号，前六位、后四位明文*/
	private String card_id;
	/** 手机*/
	private String contact_mobile;
	/** 姓名*/
	private String cust_name;
	/** 发卡银行编号*/
	private String gate_id;
	/** 证件号*/
	private String identity_code;
	/** 证件类型*/
	private String identity_type;
	/** 邮箱*/
	private String mail_addr;
	/** 商户编号*/
	private String mer_id;
	/** 资金账户托管平台用户号*/
	private String plat_user_id;
	/** 返回码*/
	private String ret_code;
	/** 返回信息*/
	private String ret_msg;
	/** 签名方式*/
	private String sign_type;
	/** 用户签约约的协议列表信息
	 * 查询成功并且存在签约的信息时时，才返回该值
	 * 协议与协议之间用竖线“|”分割，每个协议包含的数据有：协议类型（见协议类型列表）。字段之间用英文半角逗号“,”分割。
	 * 如：
	 * 1|2
	 */
	private String user_bind_agreement_list;
	/** 版本号*/
	private String version;
	/** 签名*/
	private String sign;
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getAccount_state() {
		return account_state;
	}
	public void setAccount_state(String account_state) {
		this.account_state = account_state;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getGate_id() {
		return gate_id;
	}
	public void setGate_id(String gate_id) {
		this.gate_id = gate_id;
	}
	public String getIdentity_code() {
		return identity_code;
	}
	public void setIdentity_code(String identity_code) {
		this.identity_code = identity_code;
	}
	public String getIdentity_type() {
		return identity_type;
	}
	public void setIdentity_type(String identity_type) {
		this.identity_type = identity_type;
	}
	public String getMail_addr() {
		return mail_addr;
	}
	public void setMail_addr(String mail_addr) {
		this.mail_addr = mail_addr;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}
	public String getPlat_user_id() {
		return plat_user_id;
	}
	public void setPlat_user_id(String plat_user_id) {
		this.plat_user_id = plat_user_id;
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
	public String getUser_bind_agreement_list() {
		return user_bind_agreement_list;
	}
	public void setUser_bind_agreement_list(String user_bind_agreement_list) {
		this.user_bind_agreement_list = user_bind_agreement_list;
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
		return "UserResult [account_id=" + account_id + ", account_state=" + account_state + ", balance=" + balance
				+ ", card_id=" + card_id + ", contact_mobile=" + contact_mobile + ", cust_name=" + cust_name
				+ ", gate_id=" + gate_id + ", identity_code=" + identity_code + ", identity_type=" + identity_type
				+ ", mail_addr=" + mail_addr + ", mer_id=" + mer_id + ", plat_user_id=" + plat_user_id + ", ret_code="
				+ ret_code + ", ret_msg=" + ret_msg + ", sign_type=" + sign_type + ", user_bind_agreement_list="
				+ user_bind_agreement_list + ", version=" + version + ", sign=" + sign + "]";
	}
}
