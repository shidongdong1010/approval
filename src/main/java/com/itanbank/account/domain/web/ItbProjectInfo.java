package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 标的信息
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_project_info")
public class ItbProjectInfo implements Serializable {
	
    /** 主键 **/
    private Long id;

    /** 申请件ID **/
    private Long appId;

    /** 借款人姓名 **/
    private String loanName;

    /** 借款人类型 **/
    private String loanUserType;

    /** 借款人手机 **/
    private String loanMobile;

    /** 借款合同编号 **/
    private String loanContractNo;

    /** 借款合同金额 **/
    private Double loanAmount;

    /** 最低投标金额 **/
    private Double lowBidAmount;

    /** 最高投标总额 **/
    private Double higBidAmount;

    /** 贷款利率(年) **/
    private Double loanRate;

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

    /** 状态(0-保存,1-发标中,2-投标中,3-已满标, 4-流标, 5-放款中, 6-还款中, 7-逾期中, 8-返款中, 9-已还完, 10-逾期待还,11-满标中, 12已平台收费,13-平台收费中,14-偿付中,15-贴现中) **/
    private String status;

    /** 是否逾期[0-否，1-是] **/
    private String isOverdue;

    /** 锁定状态(0-未锁定, 1-锁定) **/
    private String locked;

    /** 活动类型(0-无活动,1-新手体验，2-亲友推) **/
    private String activityType;

    /** 计息开始日期 **/
    private Date beginDate;

    /** 计息结束日期 **/
    private Date endDate;

    /** 创建用户ID **/
    private Long createUserId;

    /** 创建时间 **/
    private Date createTime;
    
    /**借款人ID**/
    private Long loanUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
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

    @Column(name = "loan_name", length = 20)
    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    @Column(name = "loan_user_type", length = 1)
    public String getLoanUserType() {
        return loanUserType;
    }

    public void setLoanUserType(String loanUserType) {
        this.loanUserType = loanUserType;
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

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "is_overdue", length = 1)
    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
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
    
    @Column(name = "loan_user_id", length = 11)
	public Long getLoanUserId() {
		return loanUserId;
	}
	
	public void setLoanUserId(Long loanUserId) {
		this.loanUserId = loanUserId;
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
}
