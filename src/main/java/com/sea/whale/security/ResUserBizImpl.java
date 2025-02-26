package com.sea.whale.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sea.whale.exception.AppException;
import com.sea.whale.mapper.ResUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.sea.whale.enums.ResultEnum.USER_NOT_EXIST;

/**
 * <p>
 * 查询库表用户信息
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-25 11:16
 */
@Service
@Slf4j
public class ResUserBizImpl implements UserDetailsService {


    private final ResUserMapper resUserMapper;

    public ResUserBizImpl(ResUserMapper resUserMapper) {
        this.resUserMapper = resUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<ResUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResUser::getUsername,username);
        ResUser resUser = resUserMapper.selectOne(queryWrapper);
        log.info("查询用户信息:{}", resUser);
        if (resUser != null) {
            return resUser;
        }else {
            throw new UsernameNotFoundException(
                    "用户不存在", new AppException(USER_NOT_EXIST)
            );
        }
    }
}
