package com.sea.whale.security.oauth2;

import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import com.sea.whale.service.UserAuthRepository;
import com.sea.whale.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * OAuth2登录服务
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-26 15:55
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

    private final UserService userService;

    private final UserAuthRepository userAuthRepository;

    @Transactional(readOnly = true)
    public UserDTO handleOAuthLogin(OAuth2User oauthUser, String authType) {
        // 1. 参数校验前置
        validateOAuthUser(oauthUser, authType);

        // 2. 获取平台唯一标识（使用枚举更安全）
        final Integer authId = extractPlatformId(oauthUser, authType);

        // 3. 查询绑定关系（使用Optional处理空值）
        return userAuthRepository.findByAuthTypeAndAuthId(authType, authId)
                .map(UserAuth::getUserId)
                .map(userId -> getUserWithValidation(userId, authType))
                .orElseThrow(() -> new AppException(ResultEnum.ACCOUNT_DATA_INCONSISTENT)); // 明确未绑定异常类型
    }

    private void validateOAuthUser(OAuth2User oauthUser, String  authType) {
        if (oauthUser == null || authType == null) {
            log.warn("OAuth登录参数异常: oauthUser={}, authType={}", oauthUser, authType);
            throw new AppException(ResultEnum.OAUTH_PARAMETER_EXCEPTION);
        }

        if (oauthUser.getAttributes().isEmpty()) {
            log.error("第三方用户数据为空: {}", oauthUser.getName());
            throw new AppException(ResultEnum.OAUTH_TRIPARTITE_EXCEPTION);
        }
    }

    private Integer extractPlatformId(OAuth2User oauthUser, String authType) {
        try {
            switch (authType) {
                case "github":
                case "gitee": return oauthUser.getAttribute("id");
                default : {
                    throw new AppException(ResultEnum.UNSUPPORTED_AUTH_TYPE);
                }
            }
        } catch (NullPointerException e) {
            log.error("平台唯一标识提取失败: {} - {}", authType, oauthUser.getAttributes());
            throw new AppException(ResultEnum.OAUTH_DATA_ERROR);
        }
    }

    private UserDTO getUserWithValidation(Integer userId, String authType) {
        UserDTO user = userService.getUserById(userId);
        if (user == null) {
            log.error("账户数据不一致: {} 绑定的用户ID {} 不存在", authType, userId);
            throw new AppException(ResultEnum.ACCOUNT_DATA_INCONSISTENT);
        }
        return user;
    }

}
