package com.itanbank.account.domain.web.enums;

/**
 * 投资记录状态枚举
 * @author wn
 *
 */
public enum ItbInvestStatusEnum {


	FINISH("0", "完成"),

	CANCEL("1", "取消"),

	REMOVE("2", "删除");

    private String code;

    private String desc;

    private ItbInvestStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ItbInvestStatusEnum getByCode(String code) {
        for (ItbInvestStatusEnum enumObj : ItbInvestStatusEnum.values()) {
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
