package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 贴现记录
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_add_amount_info")
public class ItbAddAmountInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 贴现金额 **/
    private Double addAmount;

    /** 贴现利率 **/
    private Double addRate;

    /** 贴现时间 **/
    private Date addTime;

    /** 标的ID **/
    private Long projectId;

    /** 状态[0-成功, 1-失败] **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 创建人ID **/
    private Long createUserId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "add_amount", precision = 15, scale = 2)
    public Double getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(Double addAmount) {
        this.addAmount = addAmount;
    }

    @Column(name = "add_rate", precision = 15, scale = 4)
    public Double getAddRate() {
        return addRate;
    }

    public void setAddRate(Double addRate) {
        this.addRate = addRate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "project_id", length = 11)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    @Column(name = "create_user_id", length = 11)
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

}
