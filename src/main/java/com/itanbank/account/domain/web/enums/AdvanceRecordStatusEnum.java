package com.itanbank.account.domain.web.enums;

/**
 * 代偿记录状态枚举
 * @author wn
 *
 */
public enum AdvanceRecordStatusEnum {

	SUCCESS("0", "已垫付"),

	SAVE("1", "保存"),

	FAIL("2", "垫付失败"),

	ADVANCING("3", "垫付中");

    private String code;

    private String desc;

    private AdvanceRecordStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AdvanceRecordStatusEnum getByCode(String code) {
        for (AdvanceRecordStatusEnum enumObj : AdvanceRecordStatusEnum.values()) {
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
