package com.itanbank.account.pay.enums;

public enum TranStateEnum {
	ZEROONEZERO("010", "初始"),
	ZEROONETWO("012", "成功"),
	ZEROONETHREE("013", "失败"),
	ZEROONEFOUR("014", "不明"),
	ZEROONEFIVE("015", "交易关闭"),
	ZEROONESIX("016", "其他"),
	ZEROTWOZERO("020", "初始"),
	ZEROTWOONE("021", "受理中"),
	ZEROTWOTWO("022", "成功"),
	ZEROTWOTHREE("023", "失败"),
	ZEROTWOFOUR("024", "不明"),
	ZEROTWOFIVE("025", "交易关闭（提现）"),
	ZEROTWOSIX("026", "其他（需人工查询跟进）"),
	ZEROTWOTWI("0212", "已冻结"),
	ZEROTWOTHEN("0213", "待解冻"),
	ZEROTWOFOTEN("0214", "财务已审核"),
	ZEROTWOFITEN("0215", "财务审核失败"),
	ZEROTHREEZERO("030", "初始"),
	ZEROTHREETEO("032", "初始"),
	ZEROTHREETHREE("033", "失败"),
	ZEROTHREEFOUR("034", "不明"),
	ZEROTHREEFIVE("035", "交易关闭"),
	ZEROTHREESIX("036", "其他"),
	ZEROFOURZERO("040", "初始"),
	ZEROFOURTEO("042", "初始"),
	ZEROFOURTHREE("043", "失败"),
	ZEROFOURFOUR("044", "不明"),
	ZEROFOURFIVE("045", "交易关闭"),
	ZEROFOURSIX("046", "其他");

    private String code;

    private String desc;

    TranStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TranStateEnum getByCode(String code) {
        for (TranStateEnum enumObj : TranStateEnum.values()) {
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
