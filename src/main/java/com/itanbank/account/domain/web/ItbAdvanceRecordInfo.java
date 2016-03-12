package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 垫付记录表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_advance_record_info")
public class ItbAdvanceRecordInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 垫付记录ID **/
    private Long advanceId;

    /** 垫付本金 **/
    private Double capitalAmount;

    /** 垫付利息 **/
    private Double interestAmount;

    /** 垫付第三方支付帐号 **/
    private String payNo;

    /** 还款记录ID **/
    private Long repayId;

    /** 借款人id **/
    private Long loanUserId;

    /** 标的ID **/
    private Long projectId;

    /** 垫付时间 **/
    private Date advanceTime;

    /** 垫付公司ID **/
    private Long companyId;

    /** 创建时间 **/
    private Date createTime;

    /** 垫付状态[0-已垫付内, 1-保存, 2-垫付失败] **/
    private String status;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "advance_id", length = 11)
    public Long getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(Long advanceId) {
        this.advanceId = advanceId;
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

    @Column(name = "pay_no", length = 20)
    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    @Column(name = "repay_id", length = 11)
    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "status", length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
