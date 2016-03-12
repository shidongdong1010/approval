package com.itanbank.account.pay.enums;

/**
 * 无密标的转账转账类型
 * @author wn
 *
 */
public enum ProjectTransferNopwdParticType {

	INVEST("01", "投资人"),
	REPAY("02", "融资人");
	
	private String code;
    private String desc;

    ProjectTransferNopwdParticType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectTransferNopwdParticType getByCode(String code) {
        for (ProjectTransferNopwdParticType enumObj : ProjectTransferNopwdParticType.values()) {
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
