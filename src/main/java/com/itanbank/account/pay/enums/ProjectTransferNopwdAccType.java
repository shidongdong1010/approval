package com.itanbank.account.pay.enums;

/**
 * 无密标的转账账户类型
 * @author wn
 *
 */
public enum ProjectTransferNopwdAccType {

	PERSON("01", "对私（个人用户）"),
	ENTERPRISE("02", "对公（企业用户）");
	
	private String code;
    private String desc;

    ProjectTransferNopwdAccType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProjectTransferNopwdAccType getByCode(String code) {
        for (ProjectTransferNopwdAccType enumObj : ProjectTransferNopwdAccType.values()) {
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
