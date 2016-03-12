package com.itanbank.account.domain.web.resultcode;

import com.itanbank.account.common.ResultCode;

/**
 * Created by SDD on 2016/2/28.
 */
public enum BackResultCode implements ResultCode {
    /** 成功 */
    SUCCESS(200, "SUCCESS"),

    /** 系统错误 */
    SYSTEM_ERROR(500, "SYSTEM_ERROR"),

    /** 返款申请已经处理，本次无需重复操作 **/
    BACK_REPEAT_ACTION(500, "BACK_REPEAT_ACTION");


    private String errorCode;

    private int    statusCode;

    BackResultCode(int statusCode, String errorCode) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
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
