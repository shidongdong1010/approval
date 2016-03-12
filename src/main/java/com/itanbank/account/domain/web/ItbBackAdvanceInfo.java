package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 返款到垫付方记录
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_back_advance_info")
public class ItbBackAdvanceInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 借款用户ID **/
    private Long loanUserId;

    /** 标的ID **/
    private Long projectId;

    /** 期数 **/
    private Long term;

    /** 还款日期 **/
    private Date repayDate;

    /** 还款ID **/
    private Long repayId;

    /** 垫付公司ID **/
    private Long companyId;

    /** 还款本金 **/
    private Double repayCapital;

    /** 还款利息 **/
    private Double repayInterest;

    /** 违约金 **/
    private Double penaltyAmount;

    /** 状态[0-完成, 1-返款中, 2-返款失败] **/
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

    @Column(name = "loan_user_id", length = 11)
    public Long getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Long loanUserId) {
        this.loanUserId = loanUserId;
    }

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column(name = "term", length = 11)
    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "repay_date")
    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    @Column(name = "repay_id", length = 11)
    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
    }

    @Column(name = "company_id", length = 11)
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Column(name = "repay_capital", precision = 15, scale = 2)
    public Double getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(Double repayCapital) {
        this.repayCapital = repayCapital;
    }

    @Column(name = "repay_interest", precision = 15, scale = 2)
    public Double getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(Double repayInterest) {
        this.repayInterest = repayInterest;
    }

    @Column(name = "penalty_amount", precision = 15, scale = 2)
    public Double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
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
