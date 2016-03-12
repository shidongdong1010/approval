package com.itanbank.account.domain.web.enums;

/**
 * 代偿记录状态枚举
 * @author wn
 *
 */
public enum IsAddAmountEnum {

	NO("0", "否"),

	YES("1", "是");

    private String code;

    private String desc;

    private IsAddAmountEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IsAddAmountEnum getByCode(String code) {
        for (IsAddAmountEnum enumObj : IsAddAmountEnum.values()) {
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
