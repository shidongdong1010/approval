package com.itanbank.account.pay.enums;

/**
 * 转账方类型
 * Created by SDD on 2016/2/28.
 */
public enum ParticTypeEnum {
    INVESTOR("01", "投资者"),
    LOAN("02", "融资人"),
    P2P("03", "P2P平台"),
    ADVANCE("04", "担保方"),
    USE_OF_USER("05", "资金使用方");


    private String code;

    private String desc;

    ParticTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ParticTypeEnum getByCode(String code) {
        for (ParticTypeEnum enumObj : ParticTypeEnum.values()) {
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
