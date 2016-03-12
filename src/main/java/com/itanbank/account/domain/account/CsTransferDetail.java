package com.itanbank.account.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 标的转账明细
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_transfer_detail")
public class CsTransferDetail implements Serializable {

    /** 主键 **/
    private Long id;

    /** P2P平台请求流水号 **/
    private String orderNo;

    /** P2P平台交易日期 **/
    private Date tradeDate;

    /** 转出方账户号 **/
    private String outAccountNo;

    /** 转入方账户号 **/
    private String inAccountNo;

    /** 金额 **/
    private Double amount;

    /** 支付平台日期 **/
    private String merDate;

    /** 支付平台时间 **/
    private String merTime;

    /** 交易平台流水号 **/
    private String tradeNo;

    /** 账户日期 **/
    private Date accountDate;

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

    @Column(name = "out_account_no", length = 20)
    public String getOutAccountNo() {
        return outAccountNo;
    }

    public void setOutAccountNo(String outAccountNo) {
        this.outAccountNo = outAccountNo;
    }

    @Column(name = "in_account_no", length = 20)
    public String getInAccountNo() {
        return inAccountNo;
    }

    public void setInAccountNo(String inAccountNo) {
        this.inAccountNo = inAccountNo;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_date")
    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
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
