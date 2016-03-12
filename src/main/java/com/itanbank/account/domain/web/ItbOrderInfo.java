package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 订单表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_order_info")
public class ItbOrderInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 订单号 **/
    private String orderNo;

    /** 订单状态[0-创建, 1-支付中, 2-支付完成, 3-支付失败] **/
    private String orderStatus;

    /** 业务类型(90-充值, 91-提现, 01-投资中, 02-债权购买, 03-还款, 04-偿付, 05-贴现, 51-流标后返款, 52-平台收费, 53-放款, 54-还款后返款, 55-偿付后返款, 56-债权转让的返款, 57-撤资后的返款) **/
    private String businessType;

    /** 订单金额 **/
    private Double amount;

    /** 订单描述 **/
    private String orderDesc;

    /** 用户ID **/
    private Long userId;

    /** 用户名称 **/
    private String userName;

    /** 用户手机 **/
    private String userMobile;

    /** 用户类型[0-个人用户, 1-企业用户] **/
    private String userType;

    /** 银行代码 **/
    private String bankCode;

    /** 申请件id **/
    private Long appId;

    /** 合同编号 **/
    private String loanContractNo;

    /** 标的ID **/
    private Long projectId;

    /** 订单时间 **/
    private Date orderTime;

    /** 转入方第三方支付帐户号 **/
    private String inPayNo;

    /** 转出方第三方支付帐户号 **/
    private String outPayNo;

    /** 交易平台流水号 **/
    private String tradeNo;

    /** 用户IP地址 **/
    private String userIp;

    /** 创建时间 **/
    private Date createTime;

    /** 备注 **/
    private String remarks;

    /** 版本号 **/
    private Long version;


    @Override
    public String toString() {
        return "ItbOrderInfo{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", businessType='" + businessType + '\'' +
                ", amount=" + amount +
                ", orderDesc='" + orderDesc + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userType=" + userType +
                ", bankCode='" + bankCode + '\'' +
                ", appId=" + appId +
                ", loanContractNo='" + loanContractNo + '\'' +
                ", projectId=" + projectId +
                ", orderTime=" + orderTime +
                ", inPayNo='" + inPayNo + '\'' +
                ", outPayNo='" + outPayNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", userIp='" + userIp + '\'' +
                ", createTime=" + createTime +
                ", remarks='" + remarks + '\'' +
                ", version=" + version +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_no", length = 45)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "order_status", length = 1)
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "business_type", length = 2)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "amount", precision = 15, scale = 2)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "order_desc", length = 200)
    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    @Column(name = "user_id", length = 11)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "user_name", length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_mobile", length = 11)
    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Column(name = "user_type", length = 1)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "bank_code", length = 20)
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Column(name = "app_id", length = 11)
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Column(name = "loan_contract_no", length = 45)
    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_time")
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Column(name = "in_pay_no", length = 50)
    public String getInPayNo() {
        return inPayNo;
    }

    public void setInPayNo(String inPayNo) {
        this.inPayNo = inPayNo;
    }

    @Column(name = "out_pay_no", length = 50)
    public String getOutPayNo() {
        return outPayNo;
    }

    public void setOutPayNo(String outPayNo) {
        this.outPayNo = outPayNo;
    }

    @Column(name = "trade_no", length = 20)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Column(name = "user_ip", length = 20)
    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "remarks", length = 50)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "version", length = 11)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
