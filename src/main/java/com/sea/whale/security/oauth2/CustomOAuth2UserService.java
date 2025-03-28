package com.sea.whale.security.oauth2;

import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.security.ResUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * <p>
 * OAuth2拦截器
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-26 16:48
 */
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    @Autowired
    private OAuthService oauthService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);
        // OAuth2认证器
        String provider = userRequest.getClientRegistration().getRegistrationId();
        UserDTO userDTO = oauthService.handleOAuthLogin(oauthUser, provider);
        // 转换为Spring Security需要的用户对象
        return ResUser.builder().
                id(userDTO.getId())
                .username(userDTO.getUserName())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .role(userDTO.getUserRole())
                .build();
    }

}
