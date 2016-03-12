package com.itanbank.account.domain.web.enums;

/**
 * 用户类型枚举
 * @author wn
 *
 */
public enum UserTypeEnum {

	PERSON("0", "个人用户"),

	ENTERPRISE("1", "企业用户");


    private String code;

    private String desc;

    private UserTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserTypeEnum getByCode(String code) {
        for (UserTypeEnum enumObj : UserTypeEnum.values()) {
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
