package com.itanbank.account.domain.app.enums;

/**
 * 是否枚举
 *
 * @author wufan
 * @version 1.0
 */
public enum YesOrNoEnum {
	
	Y(1, "是"),

	N(0, "否");

	private int code;

	private String desc;

	private YesOrNoEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static YesOrNoEnum getByCode(int code) {
        for (YesOrNoEnum enumObj : YesOrNoEnum.values()) {
            if (enumObj.getCode() == code) {
                return enumObj;
            }
        }
        return null;
    }

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
