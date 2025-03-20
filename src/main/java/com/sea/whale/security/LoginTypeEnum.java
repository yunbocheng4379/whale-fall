package com.sea.whale.security;

import lombok.Getter;

/**
 * <p>
 * 登录验证类型枚举
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-20 18:12
 */
@Getter
public enum LoginTypeEnum {

    USER("account"),
    MAIL("mailbox");

    private final String type;

    LoginTypeEnum(String type) {
        this.type = type;
    }

}
