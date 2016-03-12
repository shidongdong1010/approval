package com.itanbank.account.domain.web.enums;

public enum PenInterestDayStatusEnum {

	NORMAL("0", "正常"),

	CLOSED("1", "已结清");

	private String code;

	private String desc;

	private PenInterestDayStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static PenInterestDayStatusEnum getByCode(String code) {
        for (PenInterestDayStatusEnum enumObj : PenInterestDayStatusEnum.values()) {
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
