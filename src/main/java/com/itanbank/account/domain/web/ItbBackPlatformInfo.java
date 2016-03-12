package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 
 * 返款平台收延迟服务费
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_back_platform_info")
public class ItbBackPlatformInfo implements Serializable {

    /**  **/
    private Long id;

    /** 标的ID **/
    private Long projectId;

    /** 期数 **/
    private Long term;

    /** 借款用户ID **/
    private Long loanUserId;

    /** 还款ID **/
    private Long repayId;

    /** 还款日期 **/
    private Date repayDate;

    /** 平台公司ID **/
    private Long companyId;

    /** 应还延迟还款服务费 **/
    private Double duetoLateRepayServerAmount;

    /** 已还延迟还款服务费 **/
    private Double paidLateRepayServerAmount;

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

    @Column(name = "loan_user_id", length = 11)
    public Long getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Long loanUserId) {
        this.loanUserId = loanUserId;
    }

    @Column(name = "repay_id", length = 11)
    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "repay_date")
    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    @Column(name = "company_id", length = 11)
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
