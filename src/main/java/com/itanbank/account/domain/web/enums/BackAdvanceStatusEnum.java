package com.itanbank.account.domain.web.enums;

/**
 * 返款到垫付公司状态
 * Created by SDD on 2016/2/28.
 */
public enum BackAdvanceStatusEnum {
    SUCCESS("0", "完成"),
    BACK_IN("1", "返款中"),
    FAIL("2", "返款失败");

    private String code;

    private String desc;

    private BackAdvanceStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BackAdvanceStatusEnum getByCode(String code) {
        for (BackAdvanceStatusEnum enumObj : BackAdvanceStatusEnum.values()) {
            if (enumObj.getCode() == code) {
                return enumObj;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
