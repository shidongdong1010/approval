package com.itanbank.account.domain.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 罚息表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_pen_interest_day")
public class ItbPenInterestDay implements Serializable {

    /** 主键 **/
    private Long id;

    /** 标的ID **/
    private Long projectId;

    /** 借款用户ID **/
    private Long loanUserId;

    /** 借款合同 **/
    private String loanContractNo;

    /** 未还金额 **/
    private Double noRepayAmount;

    /** 未还本金 **/
    private Double noRepayCapital;

    /** 未还利息 **/
    private Double noRepayInterest;

    /** 逾期天数 **/
    private Long deliquencyDay;

    /** 延迟还款服务费 **/
    private Double lateRepayServerAmount;

    /** 违约金 **/
    private Double penaltyAmount;

    /** 罚息开始日期 **/
    private Date penaltyBeginDate;

    /** 罚息结束日期 **/
    private Date penaltyEndDate;

    /** 状态[0-正常, 1-已结清] **/
    private String status;

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

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column(name = "loan_user_id", length = 11)
    public Long getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Long loanUserId) {
        this.loanUserId = loanUserId;
    }

    @Column(name = "loan_contract_no", length = 45)
    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    @Column(name = "no_repay_amount", precision = 15, scale = 2)
    public Double getNoRepayAmount() {
        return noRepayAmount;
    }

    public void setNoRepayAmount(Double noRepayAmount) {
        this.noRepayAmount = noRepayAmount;
    }

    @Column(name = "no_repay_capital", precision = 15, scale = 2)
    public Double getNoRepayCapital() {
        return noRepayCapital;
    }

    public void setNoRepayCapital(Double noRepayCapital) {
        this.noRepayCapital = noRepayCapital;
    }

    @Column(name = "no_repay_interest", precision = 15, scale = 2)
    public Double getNoRepayInterest() {
        return noRepayInterest;
    }

    public void setNoRepayInterest(Double noRepayInterest) {
        this.noRepayInterest = noRepayInterest;
    }

    @Column(name = "deliquency_day", length = 11)
    public Long getDeliquencyDay() {
        return deliquencyDay;
    }

    public void setDeliquencyDay(Long deliquencyDay) {
        this.deliquencyDay = deliquencyDay;
    }

    @Column(name = "late_repay_server_amount", precision = 15, scale = 2)
    public Double getLateRepayServerAmount() {
        return lateRepayServerAmount;
    }

    public void setLateRepayServerAmount(Double lateRepayServerAmount) {
        this.lateRepayServerAmount = lateRepayServerAmount;
    }

    @Column(name = "penalty_amount", precision = 15, scale = 2)
    public Double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "penalty_begin_date")
    public Date getPenaltyBeginDate() {
        return penaltyBeginDate;
    }

    public void setPenaltyBeginDate(Date penaltyBeginDate) {
        this.penaltyBeginDate = penaltyBeginDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "penalty_end_date")
    public Date getPenaltyEndDate() {
        return penaltyEndDate;
    }

    public void setPenaltyEndDate(Date penaltyEndDate) {
        this.penaltyEndDate = penaltyEndDate;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
