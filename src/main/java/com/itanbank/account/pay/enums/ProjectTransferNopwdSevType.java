package com.itanbank.account.pay.enums;

/**
 * 无密标的转账业务类型
 * @author wn
 *
 */
public enum ProjectTransferNopwdSevType {

	INVEST("01", "投标"),
	BUYDEBT("02", "债权购买"),
	REPAY("03", "还款");
	
	private String code;
    private String desc;

    ProjectTransferNopwdSevType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectTransferNopwdSevType getByCode(String code) {
        for (ProjectTransferNopwdSevType enumObj : ProjectTransferNopwdSevType.values()) {
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
