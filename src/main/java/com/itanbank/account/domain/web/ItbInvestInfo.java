package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 投标记录
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_invest_info")
public class ItbInvestInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 投资用户ID **/
    private Long userId;

    /** 投资用户姓名 **/
    private String userName;

    /** 投资人手机 **/
    private String userMobile;

    /** 借款合同编号 **/
    private String loanContractNo;

    /** 标的ID **/
    private Long projectId;

    /** 投资金额 **/
    private Double amount;

    /** 收益率(年) **/
    private Double returnRate;

    /** 预期收益 **/
    private Double returnAmount;

    /** 投资时间 **/
    private Date time;

    /** 状态(0-完成,1-取消,2-删除) **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 投资人类型[0-个人用户, 1-企业用户] **/
    private String investUserType;

    @Override
    public String toString() {
        return "ItbInvestInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", loanContractNo='" + loanContractNo + '\'' +
                ", projectId=" + projectId +
                ", amount=" + amount +
                ", returnRate=" + returnRate +
                ", returnAmount=" + returnAmount +
                ", time=" + time +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", investUserType='" + investUserType + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id", length = 11)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "user_name", length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_mobile", length = 11)
    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Column(name = "loan_contract_no", length = 45)
    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column(name = "amount", precision = 15, scale = 2)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "return_rate", precision = 15, scale = 4)
    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    @Column(name = "return_amount", precision = 15, scale = 2)
    public Double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    @Column(name = "invest_user_type", length = 1)
    public String getInvestUserType() {
        return investUserType;
    }

    public void setInvestUserType(String investUserType) {
        this.investUserType = investUserType;
    }

}
