package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务费率配置
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_server_rate_config")
public class ItbServerRateConfig implements Serializable {

    /** 主键 **/
    private Long id;

    /** 服务费率 **/
    private Double serverRate;

    /** 开始时间 **/
    private Date beginTime;

    /** 结束时间 **/
    private Date endTime;

    /** 状态[0-启用,1-禁用] **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 创建用户ID **/
    private Long createUserId;
    
    /**违约费费率**/
    private Double  penaltyRate;//penalty_rate
    
    /**延迟还款服务费费率**/
    private Double  lateRepayRate;//late_repay_rate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "server_rate", precision = 15, scale = 4)
    public Double getServerRate() {
        return serverRate;
    }

    public void setServerRate(Double serverRate) {
        this.serverRate = serverRate;
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
    
    @Column(name = "penalty_rate", precision = 15, scale = 4)
	public Double getPenaltyRate() {
		return penaltyRate;
	}

	public void setPenaltyRate(Double penaltyRate) {
		this.penaltyRate = penaltyRate;
	}
	
	 @Column(name = "late_repay_rate", precision = 15, scale = 4)
	public Double getLateRepayRate() {
		return lateRepayRate;
	}

	public void setLateRepayRate(Double lateRepayRate) {
		this.lateRepayRate = lateRepayRate;
	}
    
    
}
