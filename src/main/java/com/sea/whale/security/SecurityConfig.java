package com.sea.whale.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Security配置类
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-25 11:09
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     - resUserBizImpl必须实现UserDetailsService接口
     - UserDetailsService接口是spring security的用户认证接口
     - 用于从数据库中读取用户信息
    */
    private ResUserBizImpl resUserBiz;

    // 提前定义白名单路径集合
    List<RequestMatcher> permitAllMatchers = Arrays.asList(
            new AntPathRequestMatcher("/user/login", "POST"),
            new AntPathRequestMatcher("/user/logout", "POST")
    );

    @Bean //配置加密器
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();// 加密方式
    }

    @Autowired //这里使用set方法注入，可以避免循环依赖
    public void setResUserBiz(ResUserBizImpl resUserBiz) {
        this.resUserBiz = resUserBiz;
    }
    @Bean //认证服务 组装组件
    public AuthenticationProvider authenticatorProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //设置密码加密器
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        //设置用户信息获取服务 获取用户信息
        provider.setUserDetailsService(resUserBiz);
        return provider;
    }
    @Bean //认证管理器
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        //获取认证管理器
        return configuration.getAuthenticationManager();
    }
    @Bean //配置安全过滤器链
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        //取消默认的登录页面
        http.cors().and()
                .formLogin(AbstractHttpConfigurer::disable)
                //取消默认的登出页面
                .logout(AbstractHttpConfigurer::disable)
                //将自己的认证服务加入
                .authenticationProvider(authenticatorProvider())
                //禁用csrf保护
                .csrf(AbstractHttpConfigurer::disable)
                //禁用session，因为使用token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //禁用http基本认证，因为传输数据用的post，且请求体为JSON
                .httpBasic(AbstractHttpConfigurer::disable)
                //添加自定义的JWT过滤器
                .addFilterBefore(new JwtRequestFilter(permitAllMatchers),  FilterSecurityInterceptor.class)
                //Security异常拦截
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                // 权限配置
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permitAllMatchers.toArray(new RequestMatcher[0])).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean // 配置跨域请求
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationHandler();
    }

}
