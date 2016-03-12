package com.itanbank.account.pay.enums;

public enum TransStateEnum {
	ONE("01", "成功"),
    TWO("02", "冲正"),
    NINENINE("99", "其他");

    private String code;

    private String desc;

    TransStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TransStateEnum getByCode(String code) {
        for (TransStateEnum enumObj : TransStateEnum.values()) {
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
