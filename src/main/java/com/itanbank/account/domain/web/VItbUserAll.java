package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 用户表和用户账户表合并视图
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "v_itb_user_all")
public class VItbUserAll implements Serializable {

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

    /** 帐户总额 **/
    private Double totalAmount;

    /** 可用余额 **/
    private Double usedAmount;

    /** 投资金额 **/
    private Double investAmount;

    /** 金币 **/
    private Double gold;

    /** 积分 **/
    private Double point;

    /** 版本号 **/
    private Long version;


    @Id
    @Column(name = "id", length = 11)
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

    @Column(name = "total_amount", precision = 15, scale = 2)
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Column(name = "used_amount", precision = 15, scale = 2)
    public Double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Double usedAmount) {
        this.usedAmount = usedAmount;
    }

    @Column(name = "invest_amount", precision = 15, scale = 2)
    public Double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(Double investAmount) {
        this.investAmount = investAmount;
    }

    @Column(name = "gold", precision = 15, scale = 2)
    public Double getGold() {
        return gold;
    }

    public void setGold(Double gold) {
        this.gold = gold;
    }

    @Column(name = "point", precision = 15, scale = 2)
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    @Column(name = "version", length = 11)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
