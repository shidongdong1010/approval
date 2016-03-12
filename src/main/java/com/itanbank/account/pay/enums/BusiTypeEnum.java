package com.itanbank.account.pay.enums;

public enum BusiTypeEnum {
	ONE("01", "充值"),
    TWO("02", "提现"),
    THREE("03", "标的转账"),
    FOUR("04", "转账");

    private String code;

    private String desc;

    BusiTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BusiTypeEnum getByCode(String code) {
        for (BusiTypeEnum enumObj : BusiTypeEnum.values()) {
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
