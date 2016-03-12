package com.itanbank.account.pay.enums;

/**
 * 转账方第三方账户类型
 * Created by SDD on 2016/2/28.
 */
public enum ParticAccTypeEnum {
    PERSON("01", "个人"),
    COMPANY("02", "商户");

    private String code;

    private String desc;

    ParticAccTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ParticAccTypeEnum getByCode(String code) {
        for (ParticAccTypeEnum enumObj : ParticAccTypeEnum.values()) {
            if (enumObj.getCode().equals(code)) {
                return enumObj;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
