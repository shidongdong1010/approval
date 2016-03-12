package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收益率配置表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_return_rate_config")
public class ItbReturnRateConfig implements Serializable {

    /** 主键 **/
    private Long id;

    /** 收益率 **/
    private Double returnRate;

    /** 开始时间 **/
    private Date beginTime;

    /** 结束时间 **/
    private Date endTime;

    /** 状态[0-启用, 1-禁用] **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 创建用户ID **/
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

    @Column(name = "return_rate", precision = 15, scale = 4)
    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
