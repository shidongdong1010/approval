package com.itanbank.account.domain.web.enums;

/**
 * 返款到平台状态
 * Created by SDD on 2016/2/28.
 */
public enum BackPlatformStatusEnum {
    SUCCESS("0", "完成"),
    BACK_IN("1", "返款中"),
    FAIL("2", "返款失败");

    private String code;

    private String desc;

    private BackPlatformStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BackPlatformStatusEnum getByCode(String code) {
        for (BackPlatformStatusEnum enumObj : BackPlatformStatusEnum.values()) {
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
