package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 资金流水表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_capital_flow_info")
public class ItbCapitalFlowInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 业务id **/
    private Long businessId;

    /** 业务类型(90-充值, 91-提现, 01-投资中, 02-债权购买, 03-还款, 04-偿付, 05-贴现, 51-流标后返款, 52-平台收费, 53-放款, 54-还款后返款, 55-偿付后返款, 56-债权转让的返款, 57-撤资后的返款) **/
    private String businessType;

    /** 订单ID **/
    private String orderNo;

    /** 交易平台流水号 **/
    private String tradeNo;

    /** 金额 **/
    private Double amount;

    /** 时间 **/
    private Date time;

    /** 用户ID **/
    private Long userId;

    /** 用户类型 **/
    private String userType;

    /** 转账方向(01-标的转入,02-标的转出) **/
    private String transAction;

    /** 标的ID **/
    private Long projectId;

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

    @Column(name = "business_id", length = 11)
    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Column(name = "business_type", length = 1)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "order_no", length = 45)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "trade_no", length = 20)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Column(name = "amount", precision = 15, scale = 2)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "user_id", length = 11)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long loanUserId) {
        this.userId = userId;
    }

    @Column(name = "user_type", length = 1)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "trans_action", length = 2)
    public String getTransAction() {
        return transAction;
    }

    public void setTransAction(String transAction) {
        this.transAction = transAction;
    }

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
