package com.itanbank.account.domain.web.enums;


public enum SettleTypeEnum {
	ONE("01", "充值对账文件"),
    TWO("02", "提现对账文件"),
    THREE("03", "标的对账文件"),
    FOUR("04", "标的转账对账文件"),
    FIVE("05", "转账对账文件"),
    SIX("06", "托管用户开户对账文件");

    private String code;

    private String desc;

    SettleTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SettleTypeEnum getByCode(String code) {
        for (SettleTypeEnum enumObj : SettleTypeEnum.values()) {
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
