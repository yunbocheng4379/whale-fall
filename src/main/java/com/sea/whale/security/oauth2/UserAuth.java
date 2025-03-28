package com.sea.whale.security.oauth2;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 * 三方认证对象
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-26 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user_auth")
@Builder
public class UserAuth {

    private Integer id;

    private Integer userId;

    private String authType;

    private Integer authId;

    private String authName;

}
