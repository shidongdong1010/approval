package com.itanbank.account.domain.web.enums;

/**
 * 返款到投资人状态
 * Created by SDD on 2016/2/28.
 */
public enum BackUserStatusEnum {
    SUCCESS("0", "完成"),
    BACK_IN("1", "返款中"),
    FAIL("2", "返款失败");

    private String code;

    private String desc;

    private BackUserStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BackUserStatusEnum getByCode(String code) {
        for (BackUserStatusEnum enumObj : BackUserStatusEnum.values()) {
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
