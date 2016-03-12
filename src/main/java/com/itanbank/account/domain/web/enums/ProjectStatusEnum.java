package com.itanbank.account.domain.web.enums;

/**
 * 标的状态枚举
 *
 * @author wn
 * @version 1.0
 */
public enum ProjectStatusEnum {
	
	SAVE("0", "保存"),
	
	PUBLISHING("1", "发标中"),
	
	INVESTMENT("2", "投标中"),

	FULLING("11", "满标中"),

	FULL("3", "已满标"),
	
	FLOW("4", "流标"),

	CHARGING("13", "平台收费中"),

	CHARGED("12", "已平台收费"),

	PAY("5", "放款中"),
	
	REPAY("6", "还款中"),
	
	LATEREPAY("7", "逾期中"),

	ADVANCEING("14", "偿付中"),

	DISCOUNTING("15", "贴现中"),
	
	BACK("8", "返款中"),
	
	END("9", "已还完"),


	/** 业务状态 不与数据库对应，仅处理业务逻辑**/
	ADVANCELIST("99", "偿付列表"),
	
	DISCOUNTLIST("98", "贴现列表")
	
	;

	private String code;

	private String desc;

	private ProjectStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static ProjectStatusEnum getByCode(String code) {
        for (ProjectStatusEnum enumObj : ProjectStatusEnum.values()) {
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
