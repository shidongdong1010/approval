package com.itanbank.account.pay.enums;

/**
 * Created by SDD on 2016/2/25.
 */
public enum WithdrawTradeStateEnum {

    FAIL("3", "失败"),
    SUCCESS("4", "成功");

    private String code;

    private String desc;

    WithdrawTradeStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WithdrawTradeStateEnum getByCode(String code) {
        for (WithdrawTradeStateEnum enumObj : WithdrawTradeStateEnum.values()) {
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
