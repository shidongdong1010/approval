package com.itanbank.account.domain.web.enums;

/**
 * 是否垫付
 *
 * @author wn
 */
public enum IsAdvanceEnum {
	
	NO("0", "否"),

	YES("1", "是");

	private String code;

	private String desc;

	private IsAdvanceEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static IsAdvanceEnum getByCode(String code) {
        for (IsAdvanceEnum enumObj : IsAdvanceEnum.values()) {
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
