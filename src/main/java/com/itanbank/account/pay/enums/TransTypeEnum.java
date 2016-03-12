package com.itanbank.account.pay.enums;

public enum TransTypeEnum {
	ONE("01", "充值"),
    TWO("02", "提现"),
    THREE("03", "标的转账"),
    FOUR("04", "转账"),
    FIVE("05", "提现失败退回"),
    NINENINE("99", "其他");

    private String code;

    private String desc;

    TransTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TransTypeEnum getByCode(String code) {
        for (TransTypeEnum enumObj : TransTypeEnum.values()) {
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
