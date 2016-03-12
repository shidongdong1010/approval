package com.itanbank.account.domain.app.enums;

public enum AppInfoStatusEnum {
	
	APPINFO_STATUS_ADOPT(0, "通过"),
	APPINFO_STATUS_SAVA(1, "保存"),
	APPINFO_STATUS_TEL_CHECK(2, "电话核查"),
	APPINFO_STATUS_TEL_CHECK_NO(3, "电话核查拒绝"),
	APPINFO_STATUS_TEL_CHECK_YES(4, "电话核查通过"),
	APPINFO_STATUS_BILL_CHECK(5, "票据检查"),
	APPINFO_STATUS_BILL_CHECK_NO(6, "票据检查验票拒绝"),
	APPINFO_STATUS_BILL_COMPANY_NO(7, "票据检查核心企业拒绝"),
	APPINFO_STATUS_BILL_CHECK_YES(8, "票据检查验票通过"),
	APPINFO_STATUS_BILL_COMPANY_YES(9, "票据检查核心企业通过"),
	APPINFO_STATUS_BILL_ALL_YES(10, "票据检查通过"),
	APPINFO_STATUS_MISTINESS_YES(11, "脱敏处理"),
	APPINFO_STATUS_MISTINESS_NO(12, "脱敏拒绝");

	private int code;

	private String desc;

	private AppInfoStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

    public static YesOrNoEnum getByCode(int code) {
        for (YesOrNoEnum enumObj : YesOrNoEnum.values()) {
            if (enumObj.getCode() == code) {
                return enumObj;
            }
        }
        return null;
    }
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
