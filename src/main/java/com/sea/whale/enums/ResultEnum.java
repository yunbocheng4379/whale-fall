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
    UNSUPPORTED_OPERATE_EXCEPTION(2000, "不支持操作异常"),
    // --- JWT相关验证相关 ---
    JWT_LOSE_CODE(401, "JWT令牌缺失,请重新登录"),
    JWT_FAIL_CODE(402, "JWT令牌验证失败,请重新登录"),
    // --- 用户权限验证相关,状态码以600开始 ---
    USER_NOT_EXIST(600, "用户名或密码输入错误，登录失败"),
    MAIL_NOT_EXIST(601, "邮箱未注册，请重新输入"),
    // --- OAuth2相关,状态以700开始 ---
    OAUTH_PARAMETER_EXCEPTION(700, "OAuth2登录参数异常"),
    OAUTH_TRIPARTITE_EXCEPTION(701, "三方登录参数异常"),
    UNSUPPORTED_AUTH_TYPE(702, "不支持的认证类型"),
    OAUTH_DATA_ERROR(703, "平台唯一标识提取异常"),
    ACCOUNT_DATA_INCONSISTENT(704, "本地账户与三方账户绑定数据不一致"),

    // --- 验证码相关,状态以800开始 ---
    VERIFICATION_CODE_SEND_FAIL(800, "获取验证码失败"),
    VERIFICATION_CODE_ERROR(801, "验证码不正确"),
    VERIFICATION_CODE_EXPIRED(802, "验证码已过期"),
    VERIFICATION_CODE_SEND_SUCCESS(803, "验证码发送成功");



    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
