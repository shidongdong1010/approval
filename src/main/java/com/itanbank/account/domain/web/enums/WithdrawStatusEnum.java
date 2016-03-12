package com.itanbank.account.domain.web.enums;

/**
 * 提现状态
 * Created by SDD on 2016/2/25.
 */
public enum  WithdrawStatusEnum {
    SUCCESS("0", "成功"),
    FAILURE("1", "失败"),
    APP_SUCESS("2", "提现申请成功");

    private String code;

    private String desc;

    private WithdrawStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WithdrawStatusEnum getByCode(String code) {
        for (WithdrawStatusEnum enumObj : WithdrawStatusEnum.values()) {
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
