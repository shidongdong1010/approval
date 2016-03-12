package com.itanbank.account.pay.enums;

public enum DcMarkEnum {
	ONE("01", "入账"),
    TWO("02", "出账"),
    NINENINE("99", "其他");

    private String code;

    private String desc;

    DcMarkEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DcMarkEnum getByCode(String code) {
        for (DcMarkEnum enumObj : DcMarkEnum.values()) {
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
