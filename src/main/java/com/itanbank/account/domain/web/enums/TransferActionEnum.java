package com.itanbank.account.domain.web.enums;

/**
 * 转账方向的枚举
 * @author wp
 *
 */
public enum TransferActionEnum {

	PROJECT_IN("01", "标的转入"),

	PROJECT_OUT("02","标的转出"),

	;

	private String code;

	private String desc;

	private TransferActionEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static TransferActionEnum getByCode(String code) {
        for (TransferActionEnum enumObj : TransferActionEnum.values()) {
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
