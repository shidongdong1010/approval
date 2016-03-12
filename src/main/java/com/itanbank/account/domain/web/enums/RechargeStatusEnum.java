package com.itanbank.account.domain.web.enums;

/**
 * 充值状态
 * Created by SDD on 2016/2/25.
 */
public enum RechargeStatusEnum {
    SUCCESS("0", "成功"),
    FAILURE("1", "失败"),
    MIDDLE_STATE("2", "结果不明");

    private String code;

    private String desc;

    private RechargeStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RechargeStatusEnum getByCode(String code) {
        for (RechargeStatusEnum enumObj : RechargeStatusEnum.values()) {
            if (enumObj.getCode().equals(code)) {
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
