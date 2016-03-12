package com.itanbank.account.pay.enums;

/**
 * 标的转账方向
 * Created by SDD on 2016/2/28.
 */
public enum TransActionEnum {

    IN("01", "转入"),
    OUT("02", "转出");

    private String code;

    private String desc;

    TransActionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TransActionEnum getByCode(String code) {
        for (TransActionEnum enumObj : TransActionEnum.values()) {
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
