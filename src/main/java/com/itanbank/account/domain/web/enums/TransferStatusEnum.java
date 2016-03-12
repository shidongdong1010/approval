package com.itanbank.account.domain.web.enums;

/**
 * 转账表状态的枚举
 * @author wp
 *
 */
public enum TransferStatusEnum {

	SUCCESS("0", "成功"),

	FAIL("1","失败"),
	;

	private String code;

	private String desc;

	private TransferStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static TransferStatusEnum getByCode(String code) {
        for (TransferStatusEnum enumObj : TransferStatusEnum.values()) {
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
