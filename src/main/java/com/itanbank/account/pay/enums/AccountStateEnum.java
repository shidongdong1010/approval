package com.itanbank.account.pay.enums;

public enum AccountStateEnum {
	ONE("1", "正常"),
    TWO("2", "挂失"),
    THREE("3", "冻结"),
    FOUR("4", "锁定"),
    NINE("9", "销户");

    private String code;

    private String desc;

    AccountStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AccountStateEnum getByCode(String code) {
        for (AccountStateEnum enumObj : AccountStateEnum.values()) {
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
