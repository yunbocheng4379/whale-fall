package com.sea.whale.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    // --- 常规状态码（非业务） ---
    SUCCESS(200, "成功"),
    UNKNOWN_ERROR(500, "未知异常"),
    // --- 代码及业务相关，状态码以1000开始 ---
    ARGS_VALID_ERROR(1000, "参数校验错误"),
    ASSETS_ADD_EXCEPTION(1001, "新增数据异常"),
    ASSETS_DELETE_EXCEPTION(1004, "删除数据异常"),
    UNSUPPORTED_OPERATE_EXCEPTION(2000, "不支持操作异常");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
