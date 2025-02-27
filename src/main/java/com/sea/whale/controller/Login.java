package com.sea.whale.controller;

import com.sea.whale.entity.R;
import com.sea.whale.security.JwtUtil;
import com.sea.whale.security.ResUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


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
public class Login {

    private final AuthenticationManager authenticationManager;

    public Login(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public R login(@RequestBody ResUser user) {
        //加入验证码 因为将验证码存在在session中，所以需要从session中取出来
        //使用authenticate()方法接收UsernamePasswordAuthenticationToken对象，并返回一个包含用户详细信息的 Authentication 对象。
        Authentication authentication = authenticationManager.authenticate(
                //将用户名和密码[和证书]封装到UsernamePasswordAuthenticationToken对象中，用来包装认证信息
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        //getContext()获取当前线程的SecurityContext，并返回一个SecurityContext对象。
        //setAuthentication()设置当前线程的SecurityContext，并将Authentication对象作为参数传入。
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //使用getPrincipal()方法获取认证信息，并转换为UserDetails对象
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResUser resuser = (ResUser) userDetails;
        String token = generateJwtToken(resuser, userDetails);
        //返回token，后面使用token进行认证
        return R.ok().data("token", token);
    }

    private String generateJwtToken(ResUser resuser, UserDetails userDetails) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", String.valueOf(resuser.getId()));
        payload.put("username", userDetails.getUsername());
        //后期加入系统角色时,在考虑该字段,目前先使用默认的
        payload.put("role", resuser.getRole());
        return JwtUtil.getToken(payload);
    }
}
