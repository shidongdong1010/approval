package com.itanbank.account.pay.enums;

/**
 * 第三方标的状态枚举
 * @author wn
 *
 */
public enum ProjectUpdateStateEnum {


	OPEN("0", "开标"),

	INVEST("1", "投标中"),

	REPAY("2", "还款中"),
	
	REPAYED("3", "已还款"),
	
	END("4", "结束");


    private String code;

    private String desc;

    private ProjectUpdateStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectUpdateStateEnum getByCode(String code) {
        for (ProjectUpdateStateEnum enumObj : ProjectUpdateStateEnum.values()) {
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
