package com.itanbank.account.domain.app.resultcode;

import com.itanbank.account.common.ResultCode;

/**
 * Created by dongdongshi on 16/2/2.
 */
public enum UserResultCode implements ResultCode {

    /** 成功 */
    SUCCESS(200, "SUCCESS"),

    /** 系统错误 */
    SYSTEM_ERROR(500, "SYSTEM_ERROR"),

    /** 签名异常 */
    SIGN_ERROR(500, "SIGN_ERROR"),

    /** 未登陆 */
    TOKEN_IS_BLANK(50001,"TOKEN_IS_BLANK"),

    /** 登陆的用户名不存在 **/
    LOGIN_USER_NAME_NOT_EXISTS(5001, "LOGIN_USER_NAME_NOT_EXISTS"),

    /** 登陆的用户名已存在 **/
    LOGIN_USER_NAME_ESISTS(5001, "LOGIN_USER_NAME_ESISTS"),

    /** 帐户锁定 **/
    LOGIN_ACCOUNT_LOCKED(5001, "LOGIN_ACCOUNT_LOCKED"),

    /** 密码错误 **/
    LOGIN_PASSWORD_ERROR(5001, "LOGIN_PASSWORD_ERROR"),

    /** 密码错误 **/
    LOGIN_PASSWORD_ERROR2(5001, "LOGIN_PASSWORD_ERROR2"),

    /** 帐户已禁用 **/
    LOGIN_ACCOUNT_DISABLED(5001, "LOGIN_ACCOUNT_DISABLED"),

    /** 密码已过期 **/
    LOGIN_CREDENTIAL_EXPIRED(5001, "LOGIN_CREDENTIAL_EXPIRED"),
    ;

    private String errorCode;

    private int    statusCode;

    UserResultCode(int statusCode, String errorCode) {
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