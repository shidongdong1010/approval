package com.itanbank.account.domain.web.enums;

/**
 * 借款人类型
 * Created by SDD on 2016/2/26.
 */
public enum LoanUserTypeEnum {
    PERSON("0", "个人"),

    COMPANY("1", "企业");


    private String code;

    private String desc;

    private LoanUserTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoanUserTypeEnum getByCode(String code) {
        for (LoanUserTypeEnum enumObj : LoanUserTypeEnum.values()) {
            if (enumObj.getCode().equals(code)) {
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
