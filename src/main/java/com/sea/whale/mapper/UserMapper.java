package com.sea.whale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.entity.vo.MenuVO;
import com.sea.whale.security.ResUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO> {


    void insertUser(UserDTO userDTO);

    List<MenuVO> getMenuList(@Param("username") String username);

    List<MenuVO> getNodeList();

    ResUser getLoginUserByUsername(@Param("username") String username);

    ResUser getLoginUserByEmail(@Param("email") String email);

}
