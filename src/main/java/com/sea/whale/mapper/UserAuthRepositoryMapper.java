package com.sea.whale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.whale.security.oauth2.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserAuthRepositoryMapper extends BaseMapper<UserAuth> {


    Optional<UserAuth> findByAuthTypeAndAuthId(@Param("authType") String authType, @Param("authId") Integer authId);

}
