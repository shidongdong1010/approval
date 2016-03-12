package com.itanbank.account.domain.web.enums;

/**
 * 代偿记录状态枚举
 * @author wn
 *
 */
public enum AddAmountStatusEnum {

	SUCCESS("0", "成功"),

	FAIL("1", "失败");

    private String code;

    private String desc;

    private AddAmountStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AddAmountStatusEnum getByCode(String code) {
        for (AddAmountStatusEnum enumObj : AddAmountStatusEnum.values()) {
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
