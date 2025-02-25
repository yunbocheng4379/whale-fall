package com.sea.whale.controller;

import com.sea.whale.entity.R;
import com.sea.whale.security.ResUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author chengyunbo03@gyyx.cn
 * @since 2025-02-25 18:22
 */
@Tag(name = "验证JWT拦截")
@RestController
@RequestMapping("/test")
public class Test {

    @PostMapping("/test")
    public R test(@RequestBody ResUser user) {
        System.out.println(user);
        return R.ok();
    }

}
