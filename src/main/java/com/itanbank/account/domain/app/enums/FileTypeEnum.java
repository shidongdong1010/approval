package com.itanbank.account.domain.app.enums;

/**
 * 文件类型枚举
 * Created by dongdongshi on 16/2/13.
 */
public enum FileTypeEnum {


    TICKET(0, "承兑汇票"),

    BUSINESS_LICENSE(1, "工商营业执照"),

    TAX_REG_NO(2, "税务登记证"),

    ORG_CODE_NO(3, "组织机构代码号"),
	
	IDCARD_POSITIVE(4,"身份证正面"),
	
	IDCARD_OTHER(5,"身份证反面");
	
    private int code;

    private String desc;

    private FileTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FileTypeEnum getByCode(int code) {
        for (FileTypeEnum enumObj : FileTypeEnum.values()) {
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
