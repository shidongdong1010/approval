package com.itanbank.account.pay.enums;

public enum AmtTypeEnum {
	ONE("01", "订单交易金额"),
    TWO("02", "交易手续费"),
    NINENINE("99", "其他");

    private String code;

    private String desc;

    AmtTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AmtTypeEnum getByCode(String code) {
        for (AmtTypeEnum enumObj : AmtTypeEnum.values()) {
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
