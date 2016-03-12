package com.itanbank.account.pay.enums;

/**
 * 第三方标的更新类型枚举
 * @author wn
 *
 */
public enum ProjectUpdateChangeTypeEnum {


	STATUS("01", "更新标的"),

	LOAN("02", "标的融资人"),

	ADVANCE("03", "标的代偿方"),
	
	USE("04", "标的资金使用方");


    private String code;

    private String desc;

    private ProjectUpdateChangeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectUpdateChangeTypeEnum getByCode(String code) {
        for (ProjectUpdateChangeTypeEnum enumObj : ProjectUpdateChangeTypeEnum.values()) {
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
