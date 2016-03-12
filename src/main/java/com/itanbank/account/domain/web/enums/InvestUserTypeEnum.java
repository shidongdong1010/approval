package com.itanbank.account.domain.web.enums;

/**
 * 用户类型枚举
 * @author wn
 *
 */
public enum InvestUserTypeEnum {

	PERSON("0", "个人用户"),

	ENTERPRISE("1", "企业用户");


    private String code;

    private String desc;

    private InvestUserTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InvestUserTypeEnum getByCode(String code) {
        for (InvestUserTypeEnum enumObj : InvestUserTypeEnum.values()) {
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
