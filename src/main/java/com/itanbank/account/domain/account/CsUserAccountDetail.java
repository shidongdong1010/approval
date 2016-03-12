package com.itanbank.account.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 托管用户开户明细
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_user_account_detail")
public class CsUserAccountDetail implements Serializable {

    /** 主键 **/
    private Long id;

    /** 商户用户标识 **/
    private Long userId;

    /** 资金账户托管平台用户号 **/
    private Long payAccountId;

    /** 资金账户托管平台账户号 **/
    private String payAccountNo;

    /** 姓名 **/
    private String name;

    /** 身份证 **/
    private String idCard;

    /** 手机 **/
    private String mobile;

    /** 邮箱 **/
    private String mail;

    /** 开户日期 **/
    private Date openDate;

    /** 创建时间 **/
    private Date createTime;

    /** 批次号 **/
    private String batchNo;


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

    @Column(name = "pay_account_id", length = 11)
    public Long getPayAccountId() {
        return payAccountId;
    }

    public void setPayAccountId(Long payAccountId) {
        this.payAccountId = payAccountId;
    }

    @Column(name = "pay_account_no", length = 20)
    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "id_card", length = 20)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Column(name = "mobile", length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "mail", length = 50)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "open_date")
    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "batch_no", length = 20)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

}
