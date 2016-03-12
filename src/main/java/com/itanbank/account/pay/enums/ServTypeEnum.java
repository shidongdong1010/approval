package com.itanbank.account.pay.enums;

/**
 * 业务类型
 * Created by SDD on 2016/2/24.
 */
public enum ServTypeEnum {
    /** 投资 **/
    INVEST("01","投资"),
    /** 债权购买 **/
    DEBT_BUY("02","债权购买"),
    /** 还款 **/
    REPAY("03","还款"),
    /** 偿付 **/
    ADVANCE("04","偿付"),
    /** 贴现 **/
    ADD_AMOUNT("05","贴现"),
    /** 流标后返款 **/
    FAIL_REPAY("51","流标后返款"),
    /** 平台收费 **/
    SERVER_FEE("52","平台收费"),
    /** 放款 **/
    PAY("53","放款"),
    /** 还款后返款 **/
    REPAY_BACK("54","还款后返款"),
    /** 偿付后返款 **/
    ADVANCE_BACK("55","偿付后返款"),
    /** 债权转让的返款 **/
    BEBT_BACK("56","债权转让的返款"),
    /** 撤资后的返款 **/
    DIS_BACK("57","撤资后的返款");

    /** 业务代码 **/
    private String code;
    /** 业务描述 **/
    private String desc;

    ServTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ServTypeEnum getByCode(String code) {
        for (ServTypeEnum enumObj :  ServTypeEnum.values()) {
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
