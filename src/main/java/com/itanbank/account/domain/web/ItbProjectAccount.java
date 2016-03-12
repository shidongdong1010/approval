package com.itanbank.account.domain.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 标的账户信息
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_project_account")
public class ItbProjectAccount implements Serializable {

    /** 主键与标的ID **/
    private Long id;

    /** 第三方支付标的ID **/
    private String payProjectId;

    /** 第三方支付标的账号 **/
    private String payProjectNo;

    /** 放款金额(合同额-服务费) **/
    private Double payAmount;

    /** 服务费 **/
    private Double serverAmount;

    /** 实际收取服务费 **/
    private Double grantServerAmount;

    /** 预收利息 **/
    private Double preInterest;

    /** 实际放款金额 **/
    private Double grantPayAmount;

    /** 已筹金额 **/
    private Double putAmount;

    /** 已投人数 **/
    private Long putNum;

    /** 可投金额 **/
    private Double canAmount;

    /** 是否垫付[0-否, 1-是] **/
    private String isAdvance;

    /** 代偿方企业用户ID **/
    private Long advanceCompanyId;

    /** 当前标的账户余额 **/
    private Double balance;

    /** 版本号 **/
    private Long version;

    /** 是否贴现 0否1是 **/
    private String isAddAmount;

    /** 贴现金额 **/
    private Double addAmount;


    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "pay_project_id", length = 32)
    public String getPayProjectId() {
        return payProjectId;
    }

    public void setPayProjectId(String payProjectId) {
        this.payProjectId = payProjectId;
    }

    @Column(name = "pay_project_no", length = 15)
    public String getPayProjectNo() {
        return payProjectNo;
    }

    public void setPayProjectNo(String payProjectNo) {
        this.payProjectNo = payProjectNo;
    }

    @Column(name = "pay_amount", precision = 15, scale = 2)
    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    @Column(name = "server_amount", precision = 15, scale = 2)
    public Double getServerAmount() {
        return serverAmount;
    }

    public void setServerAmount(Double serverAmount) {
        this.serverAmount = serverAmount;
    }

    @Column(name = "grant_server_amount", precision = 15, scale = 2)
    public Double getGrantServerAmount() {
        return grantServerAmount;
    }

    public void setGrantServerAmount(Double grantServerAmount) {
        this.grantServerAmount = grantServerAmount;
    }

    @Column(name = "pre_interest", precision = 15, scale = 2)
    public Double getPreInterest() {
        return preInterest;
    }

    public void setPreInterest(Double preInterest) {
        this.preInterest = preInterest;
    }

    @Column(name = "grant_pay_amount", precision = 15, scale = 2)
    public Double getGrantPayAmount() {
        return grantPayAmount;
    }

    public void setGrantPayAmount(Double grantPayAmount) {
        this.grantPayAmount = grantPayAmount;
    }

    @Column(name = "put_amount", precision = 15, scale = 2)
    public Double getPutAmount() {
        return putAmount;
    }

    public void setPutAmount(Double putAmount) {
        this.putAmount = putAmount;
    }

    @Column(name = "put_num", length = 11)
    public Long getPutNum() {
        return putNum;
    }

    public void setPutNum(Long putNum) {
        this.putNum = putNum;
    }

    @Column(name = "can_amount", precision = 15, scale = 2)
    public Double getCanAmount() {
        return canAmount;
    }

    public void setCanAmount(Double canAmount) {
        this.canAmount = canAmount;
    }

    @Column(name = "is_advance", length = 1)
    public String getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(String isAdvance) {
        this.isAdvance = isAdvance;
    }

    @Column(name = "advance_company_id", length = 11)
    public Long getAdvanceCompanyId() {
        return advanceCompanyId;
    }

    public void setAdvanceCompanyId(Long advanceCompanyId) {
        this.advanceCompanyId = advanceCompanyId;
    }

    @Column(name = "balance", precision = 15, scale = 2)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Column(name = "version", length = 11)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "is_add_amount", length = 1)
    public String getIsAddAmount() {
        return isAddAmount;
    }

    public void setIsAddAmount(String isAddAmount) {
        this.isAddAmount = isAddAmount;
    }

    @Column(name = "add_amount", precision = 15, scale = 2)
    public Double getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(Double addAmount) {
        this.addAmount = addAmount;
    }

}
