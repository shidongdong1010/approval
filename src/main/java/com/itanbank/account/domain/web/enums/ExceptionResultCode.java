package com.itanbank.account.domain.web.enums;

import com.itanbank.account.common.ResultCode;

/**
 * 异常解析器枚举
 *
 * @author admin
 * @version $v: 1.0.0, $time:2015-11-06 14:30:00 Exp $
 */
public enum ExceptionResultCode implements ResultCode {

    /** 系统错误 */
    SYSTEM_ERROR(500, "SYSTEM_ERROR");

    private String errorCode;

    private int    statusCode;

    ExceptionResultCode(int statusCode, String errorCode) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
