package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 短信记录
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_sms_info")
public class ItbSmsInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 业务类型[0-注册, 1-找回密码] **/
    private String businessType;

    /** 用户ID **/
    private Long userId;

    /** 手机 **/
    private String mobile;

    /** 短信内容 **/
    private String msg;

    /** 发送状态[1-待发送, 2-成功, 3-发送失败,  4-发送中] **/
    private String status;

    /** 发送的ip **/
    private String ip;

    /** 创建时间 **/
    private Date createTime;

    /** 流水ID **/
    private Long seqId;

    /** 错误代码 **/
    private String errorCode;

    /** 返款状态 **/
    private String retStatus;

    /** 短信通道流水ID **/
    private String sysSeq;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "business_type", length = 1)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "user_id", length = 11)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "mobile", length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "msg", length = 200)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "ip", length = 20)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "seq_id", length = 18)
    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    @Column(name = "error_code", length = 20)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "ret_status", length = 20)
    public String getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(String retStatus) {
        this.retStatus = retStatus;
    }

    @Column(name = "sys_seq", length = 30)
    public String getSysSeq() {
        return sysSeq;
    }

    public void setSysSeq(String sysSeq) {
        this.sysSeq = sysSeq;
    }

}
