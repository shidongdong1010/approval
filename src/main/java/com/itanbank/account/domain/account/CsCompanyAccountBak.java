package com.itanbank.account.domain.account;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 企业配置信息
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_company_account_bak")
public class CsCompanyAccountBak implements Serializable {

    /** 主键 **/
    private Long id;
    
    /** 公司Id**/
    private Long companyId;

    /** 公司代码 **/
    private String code;

    /** 公司名称 **/
    private String name;

    /** 银行代码 **/
    private String bankCode;

    /** 银行卡 **/
    private String bankNo;

    /** 第三方支付账户ID **/
    private String payId;

    /** 第三方支付帐户号 **/
    private String payNo;

    /** 创建时间 **/
    private Date createTime;

    /** 状态[0-启用, 1-禁用] **/
    private String status;

    /** 类型[0-平台, 1-垫付公司, 2-企业投资人,3-企业借款人] **/
    private String type;

    /** 企业账户余额 **/
    private Double balance;

    /** 版本号 **/
    private Long version;
    
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

    @Column(name = "code", length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "bank_code", length = 20)
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Column(name = "bank_no", length = 30)
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Column(name = "pay_id", length = 20)
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    @Column(name = "pay_no", length = 20)
    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "type", length = 1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "balance", precision = 15, scale = 2)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Column(name = "version", length = 11)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "batch_no", length = 20)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	 @Column(name = "company_id", length = 11)
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
    
    

}
