package com.sea.whale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.whale.entity.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO> {


    void insertUser(UserDTO userDTO);

}
