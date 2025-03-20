package com.sea.whale.security.mail;

import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * <p>
 * 邮箱登录认证器
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-20 10:25
 */
public class MailCodeAuthenticationProvider implements AuthenticationProvider {

    private static final String EMAIL_KEY = "email::";

    @Qualifier("mailUserDetailsService") private final UserDetailsService userDetailsService;

    private final RedisTemplate<String, String> redisTemplate;

    public MailCodeAuthenticationProvider(@Qualifier("mailUserDetailsService") UserDetailsService userDetailsService, RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String code = authentication.getCredentials().toString();
        String storedCode = redisTemplate.opsForValue().get(EMAIL_KEY + email);
        if (storedCode == null) {
            throw new AppException(ResultEnum.VERIFICATION_CODE_EXPIRED);
        }
        if (!Objects.equals(code, storedCode)) {
            throw new AppException(ResultEnum.VERIFICATION_CODE_ERROR);
        }
        // 加载用户（根据邮箱查找）
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        redisTemplate.delete(EMAIL_KEY + email);
        return new MailCodeAuthenticationToken(userDetails, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
