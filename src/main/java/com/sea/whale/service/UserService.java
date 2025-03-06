package com.sea.whale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sea.whale.entity.R;
import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.entity.vo.MenuVO;

import java.util.List;

public interface UserService extends IService<UserDTO> {

    void insertUser(UserDTO userDTO);

     List<MenuVO> getMenu(String username);

}
