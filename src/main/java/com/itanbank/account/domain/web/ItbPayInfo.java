package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 放款信息
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_pay_info")
public class ItbPayInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 标的ID **/
    private Long projectId;

    /** 借款用户ID **/
    private Long loanUserId;

    /** 借款人类型[0-个人用户, 1-企业用户] **/
    private String loanUserType;

    /** 放款金额 **/
    private Double payAmount;

    /** 放款时间 **/
    private Date time;

    /** 状态[0-成功, 1-失败] **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 放款用户ID **/
    private Long payUserId;

    /** 订单号 **/
    private String orderNo;


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

    @Column(name = "loan_user_type", length = 1)
    public String getLoanUserType() {
        return loanUserType;
    }

    public void setLoanUserType(String loanUserType) {
        this.loanUserType = loanUserType;
    }

    @Column(name = "pay_amount", precision = 15, scale = 2)
    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
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

    @Column(name = "pay_user_id", length = 11)
    public Long getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(Long payUserId) {
        this.payUserId = payUserId;
    }

    @Column(name = "order_no", length = 45)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
