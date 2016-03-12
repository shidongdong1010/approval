package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_user")
public class ItbUser implements Serializable {

    /** 主键 **/
    private Long id;

    /** 手机 **/
    private String mobile;

    /** 密码 **/
    private String password;

    /** 姓名 **/
    private String realName;

    /** 身份证 **/
    private String idCard;

    /** 是否实名认证(0-是,1-否) **/
    private String isRealNameAuth;

    /** 银行类型[字典] **/
    private String bankType;

    /** 银行卡号 **/
    private String bankNo;

    /** 银行支行 **/
    private String bankDesc;

    /** 是否银行卡绑定(0-是,1-否) **/
    private String isBankBind;

    /** 第三方账户ID **/
    private String payId;

    /** 第三方账户账号 **/
    private String payNo;

    /** 是否绑定第三方(0-是,1-否) **/
    private String isPayNo;

    /** 注册来源(0-网站注册, 1-APP, 2-微信) **/
    private String regSource;

    /** 是否启用(0-是,1-否) **/
    private Long enabled;

    /** 密码是否过期(0-正常,1-已过期) **/
    private Long expired;

    /** 是否锁定(0-正常,1-锁定) **/
    private Long locked;

    /** 最后登陆时间 **/
    private Date lastLoginTime;

    /** 登录错误次数 **/
    private Long loginErrorCount;

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

    @Column(name = "mobile", length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "password", length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "real_name", length = 45)
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Column(name = "id_card", length = 18)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Column(name = "is_real_name_auth", length = 1)
    public String getIsRealNameAuth() {
        return isRealNameAuth;
    }

    public void setIsRealNameAuth(String isRealNameAuth) {
        this.isRealNameAuth = isRealNameAuth;
    }

    @Column(name = "bank_type", length = 10)
    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    @Column(name = "bank_no", length = 45)
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Column(name = "bank_desc", length = 45)
    public String getBankDesc() {
        return bankDesc;
    }

    public void setBankDesc(String bankDesc) {
        this.bankDesc = bankDesc;
    }

    @Column(name = "is_bank_bind", length = 1)
    public String getIsBankBind() {
        return isBankBind;
    }

    public void setIsBankBind(String isBankBind) {
        this.isBankBind = isBankBind;
    }

    @Column(name = "pay_id", length = 45)
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    @Column(name = "pay_no", length = 45)
    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    @Column(name = "is_pay_no", length = 1)
    public String getIsPayNo() {
        return isPayNo;
    }

    public void setIsPayNo(String isPayNo) {
        this.isPayNo = isPayNo;
    }

    @Column(name = "reg_source", length = 1)
    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    @Column(name = "enabled", length = 1)
    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    @Column(name = "expired", length = 1)
    public Long getExpired() {
        return expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }

    @Column(name = "locked", length = 1)
    public Long getLocked() {
        return locked;
    }

    public void setLocked(Long locked) {
        this.locked = locked;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "login_error_count", length = 6)
    public Long getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(Long loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
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
