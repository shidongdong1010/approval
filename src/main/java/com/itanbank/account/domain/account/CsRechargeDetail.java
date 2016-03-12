package com.itanbank.account.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 充值交易明细
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_recharge_detail")
public class CsRechargeDetail implements Serializable {

    /** 主键 **/
    private Long id;

    /** P2P平台请求流水号 **/
    private String orderNo;

    /** P2P平台交易日期 **/
    private Date tradeDate;

    /** 账户号 **/
    private String payAccountNo;

    /** 资金账户托管平台用户号/商户号 **/
    private String merNo;

    /**  **/
    private Double amount;

    /** 资金账户托管平台日期 **/
    private String merDate;

    /** 资金账户托管平台时间 **/
    private String merTime;

    /** 交易平台流水号 **/
    private String tradeNo;

    /** 手续费金额 **/
    private Double comAmt;

    /** 手续费承担方类型 **/
    private String comAmtType;

    /** 充值产品号 **/
    private String productNo;

    /** 创建时间 **/
    private Date createTime;

    /** 批次号 **/
    private String batchNo;


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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trade_date")
    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    @Column(name = "pay_account_no", length = 20)
    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo;
    }

    @Column(name = "mer_no", length = 20)
    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    @Column(name = "amount", precision = 15, scale = 2)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "mer_date", length = 20)
    public String getMerDate() {
        return merDate;
    }

    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }

    @Column(name = "mer_time", length = 20)
    public String getMerTime() {
        return merTime;
    }

    public void setMerTime(String merTime) {
        this.merTime = merTime;
    }

    @Column(name = "trade_no", length = 45)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Column(name = "com_amt", precision = 15, scale = 2)
    public Double getComAmt() {
        return comAmt;
    }

    public void setComAmt(Double comAmt) {
        this.comAmt = comAmt;
    }

    @Column(name = "com_amt_type", length = 255)
    public String getComAmtType() {
        return comAmtType;
    }

    public void setComAmtType(String comAmtType) {
        this.comAmtType = comAmtType;
    }

    @Column(name = "product_no", length = 20)
    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "batch_no", length = 20)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

}
