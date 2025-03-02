package com.sea.whale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.whale.entity.R;
import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.mapper.UserMapper;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDTO> implements UserService {

    private final UserMapper userMapper;

    @Override
    public void insertUser(UserDTO userDTO) {
        userMapper.insertUser(userDTO);
    }

    @Override
    public R getMenu(String username) {
        return null;
    }
}
