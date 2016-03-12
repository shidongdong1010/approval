package com.itanbank.account.domain.web.enums;

/**
 * 企业类型枚举
 * @author wn
 *
 */
public enum CompanyTypeEnum {


	PLATFORM("0", "平台"),

	ADVANCE("1", "代偿"),

	INVESTMENT("2", "投资");


    private String code;

    private String desc;

    private CompanyTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CompanyTypeEnum getByCode(String code) {
        for (CompanyTypeEnum enumObj : CompanyTypeEnum.values()) {
            if (enumObj.getCode() == code) {
                return enumObj;
            }
        }
        return null;
    }
    
    public static String getDescByCode(String code) {
        for (CompanyTypeEnum enumObj : CompanyTypeEnum.values()) {
            if (enumObj.getCode() == code) {
                return enumObj.getDesc();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
