package com.itanbank.account.domain.web.enums;

/**
 * 时间单位
 *
 * @author wn
 */
public enum TimeUnitEnum {
	
	MONTH("1", "月"),

	DAY("2", "天");

	private String code;

	private String desc;

	private TimeUnitEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static TimeUnitEnum getByCode(String code) {
        for (TimeUnitEnum enumObj : TimeUnitEnum.values()) {
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
