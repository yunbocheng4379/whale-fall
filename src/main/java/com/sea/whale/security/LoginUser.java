package com.sea.whale.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-19 18:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUser {

    private String token;

    private String username;

}
