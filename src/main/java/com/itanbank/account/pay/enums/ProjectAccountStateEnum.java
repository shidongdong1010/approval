package com.itanbank.account.pay.enums;

public enum ProjectAccountStateEnum {
	ONE("01", "正常"),
    TWO("02", "冻结");

    private String code;

    private String desc;

    ProjectAccountStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectAccountStateEnum getByCode(String code) {
        for (ProjectAccountStateEnum enumObj : ProjectAccountStateEnum.values()) {
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
