package com.sea.whale.security.mail;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * <p>
 * 邮箱认证信息存储对象
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-20 10:29
 */
public class MailCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;

    public MailCodeAuthenticationToken(String email, String code) {
        super(null);
        this.principal = email;
        this.credentials = code;
        setAuthenticated(false);
    }

    public MailCodeAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
        this.credentials = null;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
