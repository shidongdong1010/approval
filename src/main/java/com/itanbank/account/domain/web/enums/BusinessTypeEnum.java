package com.itanbank.account.domain.web.enums;

/**
 * 业务类型
 * Created by SDD on 2016/2/24.
 */
public enum BusinessTypeEnum {
    /** 充值 **/
    RECHARGE("90","充值",null,"01"),
    /** 提现 **/
    WITHDRAW("91","提现",null,"02"),
    /** 企业充值 **/
    ENTERPRISE_RECHARGE("92","企业充值",null,"01"),
    /** 企业提现 **/
    ENTERPRISE_WITHDRAW("93","企业提现",null,"02"),

    /** 迟延还款服务费 **/
    LATE_REPAY_SERVER_AMOUNT("94", "迟延还款服务费", "52","03"),

    /** 投资 **/
    INVEST("01","投资","01","03"),
    /** 债权购买 **/
    DEBT_BUY("02","债权购买","","02"),
    /** 还款 **/
    REPAY("03","还款","03","03"),
    /** 偿付 **/
    ADVANCE("04","偿付","04","03"),
    /** 贴现 **/
    ADD_AMOUNT("05","贴现","05","03"),
    /** 流标后返款 **/
    FAIL_REPAY("51","流标后返款","51","03"),
    /** 平台收费 **/
    SERVER_FEE("52","平台收费","52","03"),
    /** 放款 **/
    PAY("53","放款","53","03"),
    /** 还款后返款 **/
    REPAY_BACK("54","还款后返款","54","03"),
    /** 偿付后返款 **/
    ADVANCE_BACK("55","偿付后返款","55","03"),
    /** 债权转让的返款 **/
    BEBT_BACK("56","债权转让的返款","56","03"),
    /** 撤资后的返款 **/
    DIS_BACK("57","撤资后的返款","57","03"),

    /**用户开户**/
    OPEN_USER_ACCOUNT("95","用户开户","","");

    /** 业务代码 **/
    private String code;
    /** 业务描述 **/
    private String desc;
    /** 第三方业务代码 **/
    private String payCode;
    
    /**第三方流水查询类型**/
    private String queryType;
    
    BusinessTypeEnum(String code, String desc, String payCode,String queryType) {
        this.code = code;
        this.desc = desc;
        this.payCode = payCode;
        this.queryType = queryType;
    }

    public static BusinessTypeEnum getByCode(String code) {
        for (BusinessTypeEnum enumObj :  BusinessTypeEnum.values()) {
            if (enumObj.getCode().equals(code)) {
                return enumObj;
            }
        }
        return null;
    }

    public static BusinessTypeEnum getByPayCode(String payCode) {
        for (BusinessTypeEnum enumObj :  BusinessTypeEnum.values()) {
            if (enumObj.getPayCode().equals(payCode)) {
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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
    
    
}
