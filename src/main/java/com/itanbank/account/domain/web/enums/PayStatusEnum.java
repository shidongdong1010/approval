package com.itanbank.account.domain.web.enums;

/**
 * 放款状态
 */
public enum PayStatusEnum {

	SUCCESS("0", "成功"),
	FAIL("1", "失败");

    private String code;

    private String desc;

    private PayStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PayStatusEnum getByCode(String code) {
        for (PayStatusEnum enumObj : PayStatusEnum.values()) {
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