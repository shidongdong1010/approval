package com.itanbank.account.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * json处理结果
 * <p>定义了返回码、信息、以及数据对象</p>
 *
 * @version $Id: JsonResult.java
 */
@JsonAutoDetect
@JsonPropertyOrder(value = { "code", "msg", "data" })
public class JsonResult {

    /** 返回码，默认正常：200 */
    private int    code = 200;

    /** 信息 */
    private String msg ="操作成功";

    /** 数据对象 */
    private Object data;

    public JsonResult() {
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public JsonResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }
}

