package com.sea.whale.security;

import com.sea.whale.exception.AppException;
import com.sea.whale.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.sea.whale.enums.ResultEnum.USER_NOT_EXIST;

/**
 * <p>
 * 用户名登录查哭认证
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-20 11:03
 */
@Service("usernameUserDetailsService")
@Slf4j
public class UsernameUserService implements UserDetailsService {

    private final UserMapper userMapper;

    public UsernameUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 根据用户名查询用户
        ResUser resUser = userMapper.getLoginUserByUsername(username);
        log.info("查询用户信息:{}", resUser);
        if (resUser == null) {
            throw new UsernameNotFoundException(
                    "用户不存在", new AppException(USER_NOT_EXIST)
            );
        }
        return resUser;
    }

}
