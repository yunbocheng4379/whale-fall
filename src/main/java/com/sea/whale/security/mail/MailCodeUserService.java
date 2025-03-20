package com.sea.whale.security.mail;

import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import com.sea.whale.mapper.UserMapper;
import com.sea.whale.security.ResUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮箱查数据库认证
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-20 11:02
 */
@Service("mailUserDetailsService")
public class MailCodeUserService implements UserDetailsService {

    private final UserMapper userMapper;

    public MailCodeUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // 根据邮箱查询用户
        ResUser user = userMapper.getLoginUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "用户不存在", new AppException(ResultEnum.MAIL_NOT_EXIST)
            );
        }
        return user;
    }

}
