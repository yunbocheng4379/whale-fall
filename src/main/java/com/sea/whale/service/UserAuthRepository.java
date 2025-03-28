package com.sea.whale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sea.whale.security.oauth2.UserAuth;

import java.util.Optional;

public interface UserAuthRepository extends IService<UserAuth> {


    Optional<UserAuth> findByAuthTypeAndAuthId(String authType, Integer authId);

}
