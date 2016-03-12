package com.itanbank.account.domain.web.enums;

/**
 * 订单状态
 * Created by SDD on 2016/2/25.
 */
public enum OrderStatusEnum {

    CREATE("0", "创建"),
    PAYMENT("1", "支付中"),
    COMPLETE_PAYMENT("2", "支付完成"),
    FAILURE_TO_PAY("3", "支付失败");

    private String code;

    private String desc;

    private OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatusEnum getByCode(String code) {
        for (OrderStatusEnum enumObj : OrderStatusEnum.values()) {
            if (enumObj.getCode().equals(code)) {
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