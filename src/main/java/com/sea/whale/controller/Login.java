package com.sea.whale.controller;

import com.sea.whale.entity.R;
import com.sea.whale.entity.dto.UserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录验证控制器
 * </p>
 *
 * @author chengyunbo03@gyyx.cn
 * @since 2025-02-24 11:18
 */
@Tag(name = "登录验证")
@RestController
@RequestMapping("/login")
public class Login {

    @PostMapping("/")
    public R login(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        return null;
    }


}
