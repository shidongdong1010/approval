package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户账户信息
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_user_account")
public class ItbUserAccount implements Serializable {

    /** 用户ID与user表ID一一对应 **/
    private Long id;

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
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
