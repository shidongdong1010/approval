package com.itanbank.account.pay.enums;

public enum ProjectStateEnum {
	CANCEL("-1", "取消"),
	ZERO("0", "开标"),
	ONE("1", "投资中"),
	TWO("2", "还款中"),
	THREE("3", "已还款"),
	FOUR("4", "结束"),
	FIVE("5", "满标"),
	SIX("6", "流标"),
	NINEZERO("90", "初始"),
	NINEONE("91", "建标中"),
    NINETWO("92", "建标成功"),
    NINETHREE("93", "建标失败"),
    NINEFOUR("94", "标的锁定");

    private String code;

    private String desc;

    ProjectStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectStateEnum getByCode(String code) {
        for (ProjectStateEnum enumObj : ProjectStateEnum.values()) {
            if (enumObj.getCode().equals(code)) {
                return enumObj;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
