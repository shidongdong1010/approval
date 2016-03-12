package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * VIEW
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "v_itb_projectandback_platform")
public class VItbProjectandbackPlatform implements Serializable {

    /** 主键 **/
    private Long id;

    /** 申请件ID **/
    private Long appId;

    /** 借款人ID **/
    private Long loanUserId;

    /** 借款人类型[0-个人用户, 1-企业用户] **/
    private String loanUserType;

    /** 借款人姓名 **/
    private String loanName;

    /** 借款人手机 **/
    private String loanMobile;

    /** 借款合同编号 **/
    private String loanContractNo;

    /** 借款合同金额 **/
    private Double loanAmount;

    /** 贷款利率(年) **/
    private Double loanRate;

    /** 最低投标金额 **/
    private Double lowBidAmount;

    /** 最高投标总额 **/
    private Double higBidAmount;

    /** 补贴利率(年) **/
    private Double addRate;

    /** 收益率(年) **/
    private Double returnRate;

    /** 项目期限 **/
    private Long projectTerm;

    /** 时间单位[字典](1-月,2-天) **/
    private String termUnit;

    /** 开标时间 **/
    private Date bidBeginTime;

    /** 招标结束时间 **/
    private Date bidEndTime;

    /** 标的名称 **/
    private String projectName;

    /** 项目详细 **/
    private String projectDesc;

    /** 状态(0-保存,1-发标中,2-投标中,3-已满标, 4-流标, 5-放款中, 6-还款中, 7-逾期中, 8-返款中, 9-已还完, 10-逾期待还,11-满标中, 12已平台收费,13-平台收费中,14-偿付中,15-贴现中, 16-返款完成) **/
    private String status;

    /** 是否逾期[0-否，1-是] **/
    private String isOverdue;

    /** 计息开始日期 **/
    private Date beginDate;

    /** 计息结束日期 **/
    private Date endDate;

    /** 创建用户ID **/
    private Long createUserId;

    /** 创建时间 **/
    private Date createTime;

    /** 是否锁定[0-否，1-是] **/
    private String locked;

    /** 活动类型(0-无活动,1-新手体验，2-亲友推) **/
    private String activityType;

    /** 标的ID **/
    private Long projectId;

    /** 期数 **/
    private Long term;

    /** 还款ID **/
    private Long repayId;

    /** 还款日期 **/
    private Date repayDate;

    /** 应还延迟还款服务费 **/
    private Double platformDuetoLateRepayServerAmount;

    /** 已还延迟还款服务费 **/
    private Double platformPaidLateRepayServerAmount;

    /** 垫付本金 **/
    private Double advanceCapital;

    /** 垫付利息 **/
    private Double advanceInterest;

    /** 应还延迟还款服务费 **/
    private Double duetoLateRepayServerAmount;

    /** 是否垫付[0-否, 1-是] **/
    private String isAdvance;

    /** 当前标的账户余额 **/
    private Double balance;

    /** 状态[0-完成, 1-返款中, 2-返款失败] **/
    private String platformstatus;

    @Id
    @Column(name = "id", length = 11)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "app_id", length = 11)
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Column(name = "loan_user_id", length = 11)
    public Long getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Long loanUserId) {
        this.loanUserId = loanUserId;
    }

    @Column(name = "loan_user_type", length = 1)
    public String getLoanUserType() {
        return loanUserType;
    }

    public void setLoanUserType(String loanUserType) {
        this.loanUserType = loanUserType;
    }

    @Column(name = "loan_name", length = 20)
    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    @Column(name = "loan_mobile", length = 11)
    public String getLoanMobile() {
        return loanMobile;
    }

    public void setLoanMobile(String loanMobile) {
        this.loanMobile = loanMobile;
    }

    @Column(name = "loan_contract_no", length = 45)
    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    @Column(name = "loan_amount", precision = 15, scale = 2)
    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Column(name = "loan_rate", precision = 15, scale = 4)
    public Double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(Double loanRate) {
        this.loanRate = loanRate;
    }

    @Column(name = "low_bid_amount", precision = 15, scale = 2)
    public Double getLowBidAmount() {
        return lowBidAmount;
    }

    public void setLowBidAmount(Double lowBidAmount) {
        this.lowBidAmount = lowBidAmount;
    }

    @Column(name = "hig_bid_amount", precision = 15, scale = 2)
    public Double getHigBidAmount() {
        return higBidAmount;
    }

    public void setHigBidAmount(Double higBidAmount) {
        this.higBidAmount = higBidAmount;
    }

    @Column(name = "add_rate", precision = 15, scale = 4)
    public Double getAddRate() {
        return addRate;
    }

    public void setAddRate(Double addRate) {
        this.addRate = addRate;
    }

    @Column(name = "return_rate", precision = 15, scale = 4)
    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    @Column(name = "project_term", length = 11)
    public Long getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(Long projectTerm) {
        this.projectTerm = projectTerm;
    }

    @Column(name = "term_unit", length = 1)
    public String getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(String termUnit) {
        this.termUnit = termUnit;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bid_begin_time")
    public Date getBidBeginTime() {
        return bidBeginTime;
    }

    public void setBidBeginTime(Date bidBeginTime) {
        this.bidBeginTime = bidBeginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bid_end_time")
    public Date getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(Date bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    @Column(name = "project_name", length = 50)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "project_desc", length = 500)
    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    @Column(name = "status", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "is_overdue", length = 2)
    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "create_user_id", length = 11)
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "locked", length = 1)
    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Column(name = "activity_type", length = 1)
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
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

    @Column(name = "platform_dueto_late_repay_server_amount", precision = 15, scale = 2)
    public Double getPlatformDuetoLateRepayServerAmount() {
        return platformDuetoLateRepayServerAmount;
    }

    public void setPlatformDuetoLateRepayServerAmount(Double platformDuetoLateRepayServerAmount) {
        this.platformDuetoLateRepayServerAmount = platformDuetoLateRepayServerAmount;
    }

    @Column(name = "platform_paid_late_repay_server_amount", precision = 15, scale = 2)
    public Double getPlatformPaidLateRepayServerAmount() {
        return platformPaidLateRepayServerAmount;
    }

    public void setPlatformPaidLateRepayServerAmount(Double platformPaidLateRepayServerAmount) {
        this.platformPaidLateRepayServerAmount = platformPaidLateRepayServerAmount;
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

    @Column(name = "dueto_late_repay_server_amount", precision = 15, scale = 2)
    public Double getDuetoLateRepayServerAmount() {
        return duetoLateRepayServerAmount;
    }

    public void setDuetoLateRepayServerAmount(Double duetoLateRepayServerAmount) {
        this.duetoLateRepayServerAmount = duetoLateRepayServerAmount;
    }

    @Column(name = "is_advance", length = 1)
    public String getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(String isAdvance) {
        this.isAdvance = isAdvance;
    }

    @Column(name = "balance", precision = 15, scale = 2)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Column(name = "platformstatus", length = 1)
    public String getPlatformstatus() {
        return platformstatus;
    }

    public void setPlatformstatus(String platformstatus) {
        this.platformstatus = platformstatus;
    }

}
