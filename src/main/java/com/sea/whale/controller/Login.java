package com.sea.whale.controller;

import com.sea.whale.entity.R;
import com.sea.whale.security.LoginUser;
import com.sea.whale.security.ResUser;
import com.sea.whale.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 登录验证控制器
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-24 11:18
 */
@Tag(name = "登录验证")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class Login {

    private final UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody ResUser user) {
        return R.ok().data("data", userService.login(user));
    }

    @GetMapping("/getMenu")
    public R getMenu(@RequestParam String username) {
        return R.ok().data("data", userService.getMenu(username));
    }

    @GetMapping("/sendVerificationCode")
    public R sendVerificationCode(@RequestParam String email) {
        userService.sendVerificationCode(email);
        return R.ok();
    }


}
