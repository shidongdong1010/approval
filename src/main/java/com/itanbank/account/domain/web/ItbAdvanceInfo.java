package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 垫付表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_advance_info")
public class ItbAdvanceInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 笔数 **/
    private Long num;

    /** 垫付方第三主支付帐号 **/
    private String payNo;

    /** 垫付本金 **/
    private Double capitalAmount;

    /** 垫付利息 **/
    private Double interestAmount;

    /** 垫付时间 **/
    private Date advanceTime;

    /** 公司ID **/
    private Long companyId;

    /** 公司代码 **/
    private String companyNo;

    /** 公司名称 **/
    private String companyName;

    /** 创建时间 **/
    private Date createTime;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "num", length = 11)
    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    @Column(name = "pay_no", length = 20)
    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    @Column(name = "capital_amount", precision = 15, scale = 2)
    public Double getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(Double capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    @Column(name = "interest_amount", precision = 15, scale = 2)
    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "advance_time")
    public Date getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(Date advanceTime) {
        this.advanceTime = advanceTime;
    }

    @Column(name = "company_id", length = 11)
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Column(name = "company_no", length = 20)
    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    @Column(name = "company_name", length = 50)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
