package com.sea.whale.security;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * 封装Security安全验证用户信息实体
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-25 11:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user")
@Builder
public class ResUser implements Serializable, UserDetails, OAuth2User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    private String username;

    // UserDetails接口中定义的密码字段名必须为password
    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(exist = false)
    private String code;

    @TableField(exist = false)
    private String loginType;

    //表中没有该字段 用于封装角色
    @TableField(exist = false)
    private String role = "ROLE_USER";

    @TableField(exist = false)
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//表示账号没有过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//表示账号没有被锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//表示密码没有过期
    }

    @Override
    public boolean isEnabled() {
        return true;//表示账号可用
    }

}
