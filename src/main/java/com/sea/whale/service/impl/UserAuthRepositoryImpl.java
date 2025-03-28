package com.sea.whale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.whale.mapper.UserAuthRepositoryMapper;
import com.sea.whale.security.oauth2.UserAuth;
import com.sea.whale.service.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthRepositoryImpl extends ServiceImpl<UserAuthRepositoryMapper, UserAuth> implements UserAuthRepository {

    private final UserAuthRepositoryMapper userAuthRepositoryMapper;


    @Override
    public Optional<UserAuth> findByAuthTypeAndAuthId(String authType, Integer authId) {
        return userAuthRepositoryMapper.findByAuthTypeAndAuthId(authType, authId);
    }
}
