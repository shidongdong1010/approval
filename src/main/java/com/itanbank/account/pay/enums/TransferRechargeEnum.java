package com.itanbank.account.pay.enums;

public enum TransferRechargeEnum {
	
	TRANSFER_RRCHARGE_IMIT("0", "初始"),
	TRANSFER_RRCHARGE_OK("2", "成功"),
	TRANSFER_RRCHARGE_NO("3", "失败"),
	TRANSFER_RRCHARGE_UNKNOWN("4", "不明"),
	TRANSFER_RRCHARGE_CLOSE("5", "交易关闭"),
	TRANSFER_RRCHARGE_OTHER("6", "其他");
	
	private String code;
    private String desc;

    TransferRechargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TransferRechargeEnum getByCode(String code) {
        for (TransferRechargeEnum enumObj : TransferRechargeEnum.values()) {
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
