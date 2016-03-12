package com.itanbank.account.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 标的对账数据明细
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_project_detail")
public class CsProjectDetail implements Serializable {

    /** 主键 **/
    private Long id;

    /** 标的ID **/
    private Long projectId;

    /** 标的账户号 **/
    private String projectAccountNo;

    /** 标的状态 **/
    private String status;

    /** 余额 **/
    private Double balance;

    /**  **/
    private Date createDate;

    /** 投资人列表 **/
    private String investPerson;

    /** 借款人列表 **/
    private String loanPerson;

    /** 资金使用人列表 **/
    private String usePerson;

    /** 担保人列表 **/
    private String advancePerson;

    /**  **/
    private String paymentPersion;

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

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column(name = "project_account_no", length = 20)
    public String getProjectAccountNo() {
        return projectAccountNo;
    }

    public void setProjectAccountNo(String projectAccountNo) {
        this.projectAccountNo = projectAccountNo;
    }

    @Column(name = "status", length = 5)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "balance", precision = 15, scale = 2)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "invest_person", length = 500)
    public String getInvestPerson() {
        return investPerson;
    }

    public void setInvestPerson(String investPerson) {
        this.investPerson = investPerson;
    }

    @Column(name = "loan_person", length = 20)
    public String getLoanPerson() {
        return loanPerson;
    }

    public void setLoanPerson(String loanPerson) {
        this.loanPerson = loanPerson;
    }

    @Column(name = "use_person", length = 20)
    public String getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }

    @Column(name = "advance_person", length = 20)
    public String getAdvancePerson() {
        return advancePerson;
    }

    public void setAdvancePerson(String advancePerson) {
        this.advancePerson = advancePerson;
    }

    @Column(name = "payment_persion", length = 20)
    public String getPaymentPersion() {
        return paymentPersion;
    }

    public void setPaymentPersion(String paymentPersion) {
        this.paymentPersion = paymentPersion;
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
