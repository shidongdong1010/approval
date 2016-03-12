package com.itanbank.account.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 提现交易明细
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_withdraw_detail")
public class CsWithdrawDetail implements Serializable {

    /** 主键 **/
    private Long id;

    /** 商户号 **/
    private String merNo;
    
    /** 交易类型 **/
    private String tradeType;

    /** 商户订单号 **/
    private String orderNo;

    /** 订单日期 **/
    private Date orderDate;

    /** 交易金额 **/
    private Double amount;

    /** 手续费 **/
    private Double comAmt;

    /** 对账日期 **/
    private Date csDate;

    /** 记账日期 **/
    private Date writeDate;

    /** 交易状态(中文描述) **/
    private String tradeStatusCn;

    /** 交易平台流水号 **/
    private String tradeNo;

    /** 手续费承担方类型 **/
    private String comAmtType;

    /** 提现产品号 **/
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
    
    @Column(name = "mer_no", length = 20)
    public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	@Column(name = "trade_type", length = 20)
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Column(name = "order_no", length = 45)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Column(name = "amount", precision = 15, scale = 2)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "com_amt", precision = 15, scale = 2)
    public Double getComAmt() {
        return comAmt;
    }

    public void setComAmt(Double comAmt) {
        this.comAmt = comAmt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cs_date")
    public Date getCsDate() {
        return csDate;
    }

    public void setCsDate(Date csDate) {
        this.csDate = csDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "write_date")
    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    @Column(name = "trade_status_cn", length = 50)
    public String getTradeStatusCn() {
        return tradeStatusCn;
    }

    public void setTradeStatusCn(String tradeStatusCn) {
        this.tradeStatusCn = tradeStatusCn;
    }

    @Column(name = "trade_no", length = 45)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Column(name = "com_amt_type", length = 20)
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
