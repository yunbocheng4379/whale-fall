package com.sea.whale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.whale.mapper.ResUserMapper;
import com.sea.whale.security.ResUser;
import com.sea.whale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户操作实现类
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-25 10:54
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<ResUserMapper, ResUser> implements UserService {


}
