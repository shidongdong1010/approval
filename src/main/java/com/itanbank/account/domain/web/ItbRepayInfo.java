package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 还款信息表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_repay_info")
public class ItbRepayInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 借款人ID **/
    private Long loanUserId;

    /** 借款申请ID **/
    private Long appId;

    /** 标的ID **/
    private Long projectId;

    /** 借款合同编号 **/
    private String loanContractNo;

    /** 期数 **/
    private Long term;

    /** 应还日期 **/
    private Date duetoRepayDate;

    /** 应还本金 **/
    private Double duetoReapyCapital;

    /** 应还利息 **/
    private Double duetoReapyInterest;

    /** 已还本金 **/
    private Double paidReapyCapital;

    /** 已还利息 **/
    private Double paidReapyInterest;

    /** 应还违约金 **/
    private Double duetoReapyPenalty;

    /** 已还违约金 **/
    private Double paidReapyPenalty;

    /** 应还延迟还款服务费 **/
    private Double duetoLateRepayServerAmount;

    /** 已还延迟还款服务费 **/
    private Double paidLateRepayServerAmount;

    /** 垫付本金 **/
    private Double advanceCapital;

    /** 垫付利息 **/
    private Double advanceInterest;

    /** 垫付方第三方支付帐号 **/
    private String advancePayNo;

    /** 状态[0-还款中,1-已还,2-逾期,3-逾期已垫付,4-垫付后已还,5,还款处理中] **/
    private String status;

    /** 还款时间 **/
    private Date repayTime;

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

    @Column(name = "loan_user_id", length = 11)
    public Long getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Long loanUserId) {
        this.loanUserId = loanUserId;
    }

    @Column(name = "app_id", length = 11)
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column(name = "loan_contract_no", length = 45)
    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    @Column(name = "term", length = 11)
    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dueto_repay_date")
    public Date getDuetoRepayDate() {
        return duetoRepayDate;
    }

    public void setDuetoRepayDate(Date duetoRepayDate) {
        this.duetoRepayDate = duetoRepayDate;
    }

    @Column(name = "dueto_reapy_capital", precision = 15, scale = 2)
    public Double getDuetoReapyCapital() {
        return duetoReapyCapital;
    }

    public void setDuetoReapyCapital(Double duetoReapyCapital) {
        this.duetoReapyCapital = duetoReapyCapital;
    }

    @Column(name = "dueto_reapy_interest", precision = 15, scale = 2)
    public Double getDuetoReapyInterest() {
        return duetoReapyInterest;
    }

    public void setDuetoReapyInterest(Double duetoReapyInterest) {
        this.duetoReapyInterest = duetoReapyInterest;
    }

    @Column(name = "paid_reapy_capital", precision = 15, scale = 2)
    public Double getPaidReapyCapital() {
        return paidReapyCapital;
    }

    public void setPaidReapyCapital(Double paidReapyCapital) {
        this.paidReapyCapital = paidReapyCapital;
    }

    @Column(name = "paid_reapy_interest", precision = 15, scale = 2)
    public Double getPaidReapyInterest() {
        return paidReapyInterest;
    }

    public void setPaidReapyInterest(Double paidReapyInterest) {
        this.paidReapyInterest = paidReapyInterest;
    }

    @Column(name = "dueto_reapy_penalty", precision = 15, scale = 2)
    public Double getDuetoReapyPenalty() {
        return duetoReapyPenalty;
    }

    public void setDuetoReapyPenalty(Double duetoReapyPenalty) {
        this.duetoReapyPenalty = duetoReapyPenalty;
    }

    @Column(name = "paid_reapy_penalty", precision = 15, scale = 2)
    public Double getPaidReapyPenalty() {
        return paidReapyPenalty;
    }

    public void setPaidReapyPenalty(Double paidReapyPenalty) {
        this.paidReapyPenalty = paidReapyPenalty;
    }

    @Column(name = "dueto_late_repay_server_amount", precision = 15, scale = 2)
    public Double getDuetoLateRepayServerAmount() {
        return duetoLateRepayServerAmount;
    }

    public void setDuetoLateRepayServerAmount(Double duetoLateRepayServerAmount) {
        this.duetoLateRepayServerAmount = duetoLateRepayServerAmount;
    }

    @Column(name = "paid_late_repay_server_amount", precision = 15, scale = 2)
    public Double getPaidLateRepayServerAmount() {
        return paidLateRepayServerAmount;
    }

    public void setPaidLateRepayServerAmount(Double paidLateRepayServerAmount) {
        this.paidLateRepayServerAmount = paidLateRepayServerAmount;
    }

    @Column(name = "advance_capital", precision = 15, scale = 2)
    public Double getAdvanceCapital() {
        return advanceCapital;
    }

    public void setAdvanceCapital(Double advanceCapital) {
        this.advanceCapital = advanceCapital;
    }

    @Column(name = "advance_interest", precision = 15, scale = 2)
    public Double getAdvanceInterest() {
        return advanceInterest;
    }

    public void setAdvanceInterest(Double advanceInterest) {
        this.advanceInterest = advanceInterest;
    }

    @Column(name = "advance_pay_no", length = 20)
    public String getAdvancePayNo() {
        return advancePayNo;
    }

    public void setAdvancePayNo(String advancePayNo) {
        this.advancePayNo = advancePayNo;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "repay_time")
    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
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
