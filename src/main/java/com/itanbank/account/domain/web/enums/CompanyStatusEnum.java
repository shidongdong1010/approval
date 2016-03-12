package com.itanbank.account.domain.web.enums;

/**
 * 企业账户状态枚举
 * @author wn
 *
 */
public enum CompanyStatusEnum {


	ENABLE("0", "启用"),

	UNENABLE("1", "禁用");


    private String code;

    private String desc;

    private CompanyStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CompanyStatusEnum getByCode(String code) {
        for (CompanyStatusEnum enumObj : CompanyStatusEnum.values()) {
            if (enumObj.getCode() == code) {
                return enumObj;
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
