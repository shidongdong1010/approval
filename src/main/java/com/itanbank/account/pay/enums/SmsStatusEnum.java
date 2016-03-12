package com.itanbank.account.pay.enums;

/**
 * 短信发送状态
 * Created by SDD on 2016/3/1.
 */
public enum SmsStatusEnum {

    SEND_WAIT("0", "待发送"),

    SUCCESS("1", "成功"),

    FAIL("2", "发送失败"),

    SEND_TO("3", "发送中");

    private String code;

    private String desc;

    private SmsStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SmsStatusEnum getByCode(String code) {
        for (SmsStatusEnum enumObj : SmsStatusEnum.values()) {
            if (enumObj.getCode() .equals(code)) {
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
