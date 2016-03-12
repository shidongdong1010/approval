package com.itanbank.account.domain.web.enums;

/**
 * 还款表状态的枚举
 * @author wp
 *
 */
public enum RepayStatusEnum {
	REPAYING("0", "还款中"),
	
	REPAY_OK("1", "已还"),
	
	REPAY_OVERDUE("2", "逾期"),

	REPAY_HANDLE("5","还款处理中"),
	;

	private String code;

	private String desc;

	private RepayStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static RepayStatusEnum getByCode(String code) {
        for (RepayStatusEnum enumObj : RepayStatusEnum.values()) {
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
