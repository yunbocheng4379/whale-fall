package com.sea.whale.security;

import com.sea.whale.security.mail.MailCodeAuthenticationProvider;
import com.sea.whale.security.oauth2.CustomOAuth2UserService;
import com.sea.whale.security.oauth2.OAuthFailureHandler;
import com.sea.whale.security.oauth2.OAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 白名单路径集合
    private static final List<RequestMatcher> PERMIT_ALL_MATCHERS = Arrays.asList(
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/user/**"),
            new AntPathRequestMatcher("/login/oauth2/code/**"),
            new AntPathRequestMatcher("/oauth2/authorization/**")
    );

    @Qualifier("usernameUserDetailsService") private final UserDetailsService usernameUserService;
    @Qualifier("mailUserDetailsService") private final UserDetailsService mailUserService;
    private final RedisTemplate<String, String> redisTemplate;

    // 通过构造器注入并指定Bean名称
    public SecurityConfig(
            @Qualifier("usernameUserDetailsService") UserDetailsService usernameUserService,
            @Qualifier("mailUserDetailsService") UserDetailsService mailUserService,
            RedisTemplate<String, String> redisTemplate) {
        this.usernameUserService = usernameUserService;
        this.mailUserService = mailUserService;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 邮箱验证码认证提供者
    @Bean
    public MailCodeAuthenticationProvider customMailAuthProvider() {
        return new MailCodeAuthenticationProvider(mailUserService, redisTemplate);
    }

    // 密码验证认证提供者
    @Bean
    public DaoAuthenticationProvider daoAuthProvider(BCryptPasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usernameUserService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthProvider,
            MailCodeAuthenticationProvider mailProvider) {
        // 组合多个认证提供者
        return new ProviderManager(
                daoAuthProvider,   // 账号密码登录方式
                mailProvider // 验证码登录方式
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtRequestFilter jwtRequestFilter) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 会话管理
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 权限配置
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_ALL_MATCHERS.toArray(new RequestMatcher[0]))
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // OAuth2配置
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService())
                        )
                        .successHandler(oauthSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                )
                // 添加 JWT 过滤器
                .addFilterBefore(jwtRequestFilter, AuthorizationFilter.class)
                // 异常处理
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public AuthenticationSuccessHandler oauthSuccessHandler() {
        return new OAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new OAuthFailureHandler();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(PERMIT_ALL_MATCHERS);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationHandler();
    }
}
