package com.sea.whale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.entity.vo.MenuVO;
import com.sea.whale.security.LoginUser;
import com.sea.whale.security.ResUser;

import java.util.List;

public interface UserService extends IService<UserDTO> {

    void insertUser(UserDTO userDTO);

     List<MenuVO> getMenu(String username);

    void sendVerificationCode(String email);

    LoginUser login(ResUser user);

    UserDTO getUserById(Integer userId);

}
