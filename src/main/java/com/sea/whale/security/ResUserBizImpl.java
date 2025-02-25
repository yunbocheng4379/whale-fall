package com.sea.whale.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sea.whale.mapper.ResUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * </p>
 *
 * @author chengyunbo03@gyyx.cn
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
        try{
            return resUserMapper.selectOne(queryWrapper);
        }catch (Exception e){
            log.error("用户不存在");
            return null;
        }
    }
}
