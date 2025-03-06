package com.sea.whale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.whale.entity.R;
import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.entity.vo.MenuVO;
import com.sea.whale.exception.AppException;
import com.sea.whale.mapper.UserMapper;
import com.sea.whale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<MenuVO> getMenu(String username) {
        List<MenuVO> nodeList = userMapper.getNodeList();
        //获取菜单
        List<MenuVO> menuVOList = userMapper.getMenuList(username);
        //获取节点
        if (!menuVOList.isEmpty()) {
            menuVOList.forEach(menuVO -> {
                List<MenuVO> filterNodeList = nodeList.stream().filter(node -> node.getMenuId().equals(menuVO.getMenuId())).collect(Collectors.toList());
                menuVO.setChildren(filterNodeList);
            });
        }
        return menuVOList;
    }
}
