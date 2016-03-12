package com.itanbank.account.domain.web;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 企业表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_company")
public class ItbCompany implements Serializable {

    /** 主键 **/
    private Long id;

    /** 企业代码 **/
    private String companyCode;

    /** 企业电话 **/
    private String companyTel;

    /** 企业名称 **/
    private String companyName;

    /** 行业代码 **/
    private String industryCode;

    /** 行业名称 **/
    private String industryName;

    /** 行业规模[字典] **/
    private String industryScale;

    /** 信用等级[字典] **/
    private String creditLevel;

    /** 注册时间[yyyy-MM-dd] **/
    private Date registerTime;

    /** 注册资本(万元) **/
    private Double registerCapital;

    /** 总资产(万元) **/
    private Double totalAssets;

    /** 上年销售收入(万元) **/
    private Double lastYearSalesRevenue;

    /** 上年利润总额(万元) **/
    private Double lastYearProfit;

    /** 经营范围[字典] **/
    private String businessScope;

    /** 企业地址-省 **/
    private String addrProvince;

    /** 企业地址-市 **/
    private String addrCity;

    /** 企业地址-区县 **/
    private String addrDistrict;

    /** 企业地址-详细地址 **/
    private String addrDetail;

    /** 企业简介 **/
    private String companyDesc;

    /** 工商营业执照 **/
    private String businessLicenseNo;

    /** 公司注册号 **/
    private String companyRegNo;

    /** 税务登记证号 **/
    private String taxRegNo;

    /** 组织机构代码号 **/
    private String orgCodeNo;

    /** 法人代表 **/
    private String legalPerson;

    /** 法人手机号码 **/
    private String legalMobile;

    /** 法人身份证 **/
    private String legalIdcard;

    /** 认定情况[字典] **/
    private String identifyCase;

    /** 认定到期日[yyyy-MM-dd] **/
    private Date identifyExpDate;

    /** 认定地区 **/
    private String identifyArea;

    /** 认定用户ID **/
    private Long identifyUserId;

    /** 认定用户名字 **/
    private String identifyUserName;

    /** 状态(0-启用,1-禁用) **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 是否核心企业[0-否1-是] **/
    private String isCoreCompany;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "company_code", length = 20)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Column(name = "company_tel", length = 20)
    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    @Column(name = "company_name", length = 45)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "industry_code", length = 45)
    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    @Column(name = "industry_name", length = 45)
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @Column(name = "industry_scale", length = 1)
    public String getIndustryScale() {
        return industryScale;
    }

    public void setIndustryScale(String industryScale) {
        this.industryScale = industryScale;
    }

    @Column(name = "credit_level", length = 1)
    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_time")
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Column(name = "register_capital", precision = 15, scale = 2)
    public Double getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Double registerCapital) {
        this.registerCapital = registerCapital;
    }

    @Column(name = "total_assets", precision = 15, scale = 2)
    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    @Column(name = "last_year_sales_revenue", precision = 15, scale = 2)
    public Double getLastYearSalesRevenue() {
        return lastYearSalesRevenue;
    }

    public void setLastYearSalesRevenue(Double lastYearSalesRevenue) {
        this.lastYearSalesRevenue = lastYearSalesRevenue;
    }

    @Column(name = "last_year_profit", precision = 15, scale = 2)
    public Double getLastYearProfit() {
        return lastYearProfit;
    }

    public void setLastYearProfit(Double lastYearProfit) {
        this.lastYearProfit = lastYearProfit;
    }

    @Column(name = "business_scope", length = 1)
    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Column(name = "addr_province", length = 20)
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Column(name = "addr_city", length = 20)
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Column(name = "addr_district", length = 20)
    public String getAddrDistrict() {
        return addrDistrict;
    }

    public void setAddrDistrict(String addrDistrict) {
        this.addrDistrict = addrDistrict;
    }

    @Column(name = "addr_detail", length = 45)
    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    @Column(name = "company_desc", length = 200)
    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    @Column(name = "business_license_no", length = 45)
    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    @Column(name = "company_reg_no", length = 45)
    public String getCompanyRegNo() {
        return companyRegNo;
    }

    public void setCompanyRegNo(String companyRegNo) {
        this.companyRegNo = companyRegNo;
    }

    @Column(name = "tax_reg_no", length = 45)
    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo;
    }

    @Column(name = "org_code_no", length = 45)
    public String getOrgCodeNo() {
        return orgCodeNo;
    }

    public void setOrgCodeNo(String orgCodeNo) {
        this.orgCodeNo = orgCodeNo;
    }

    @Column(name = "legal_person", length = 45)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Column(name = "legal_mobile", length = 11)
    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile;
    }

    @Column(name = "legal_idcard", length = 18)
    public String getLegalIdcard() {
        return legalIdcard;
    }

    public void setLegalIdcard(String legalIdcard) {
        this.legalIdcard = legalIdcard;
    }

    @Column(name = "identify_case", length = 1)
    public String getIdentifyCase() {
        return identifyCase;
    }

    public void setIdentifyCase(String identifyCase) {
        this.identifyCase = identifyCase;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "identify_exp_date")
    public Date getIdentifyExpDate() {
        return identifyExpDate;
    }

    public void setIdentifyExpDate(Date identifyExpDate) {
        this.identifyExpDate = identifyExpDate;
    }

    @Column(name = "identify_area", length = 45)
    public String getIdentifyArea() {
        return identifyArea;
    }

    public void setIdentifyArea(String identifyArea) {
        this.identifyArea = identifyArea;
    }

    @Column(name = "identify_user_id", length = 11)
    public Long getIdentifyUserId() {
        return identifyUserId;
    }

    public void setIdentifyUserId(Long identifyUserId) {
        this.identifyUserId = identifyUserId;
    }

    @Column(name = "identify_user_name", length = 45)
    public String getIdentifyUserName() {
        return identifyUserName;
    }

    public void setIdentifyUserName(String identifyUserName) {
        this.identifyUserName = identifyUserName;
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

    @Column(name = "is_core_company", length = 1)
    public String getIsCoreCompany() {
        return isCoreCompany;
    }

    public void setIsCoreCompany(String isCoreCompany) {
        this.isCoreCompany = isCoreCompany;
    }

}
