package com.sea.whale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sea.whale.entity.dto.UserDTO;

public interface UserService extends IService<UserDTO> {

    void insertUser(UserDTO userDTO);

}
