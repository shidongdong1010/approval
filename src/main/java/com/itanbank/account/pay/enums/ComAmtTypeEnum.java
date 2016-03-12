package com.itanbank.account.pay.enums;

public enum ComAmtTypeEnum {
	 	ONE("1", "交易方承担"),
	    TWO("2", "平台商户（手续费账户）承担"),
	    NIGHT("-99", "按手续费账户上线时间区分交易");

	    private String code;

	    private String desc;

	    ComAmtTypeEnum(String code, String desc) {
	        this.code = code;
	        this.desc = desc;
	    }

	    public static ComAmtTypeEnum getByCode(String code) {
	        for (ComAmtTypeEnum enumObj : ComAmtTypeEnum.values()) {
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
