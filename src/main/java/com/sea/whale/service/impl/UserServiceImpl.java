package com.sea.whale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.entity.vo.MenuVO;
import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import com.sea.whale.mapper.UserMapper;
import com.sea.whale.security.JwtUtil;
import com.sea.whale.security.LoginTypeEnum;
import com.sea.whale.security.LoginUser;
import com.sea.whale.security.mail.MailCodeAuthenticationToken;
import com.sea.whale.security.ResUser;
import com.sea.whale.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDTO> implements UserService {

    private final UserMapper userMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private final JavaMailSender mailSender;

    private static final String EMAIL_KEY = "email::";

    private final AuthenticationManager authenticationManager;

    @Value("${spring.mail.username}")
    private String sender;

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

    @Override
    public void sendVerificationCode(String email) {
        ResUser loginUserByEmail = userMapper.getLoginUserByEmail(email);
        if (loginUserByEmail == null) {
            throw new AppException(ResultEnum.MAIL_NOT_EXIST);
        }
        try {
            String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
            redisTemplate.opsForValue().set(EMAIL_KEY + email, code, 1, TimeUnit.MINUTES);
            String subject = "登录验证码";
            String content = "您登录《鲸落》的验证码是：" + code + "，1分钟内有效";
            sendSecureHtmlEmail(email, subject, content);
        }catch (Exception e) {
            log.error("邮件发送失败：{}", e.getMessage());
            throw new AppException(ResultEnum.VERIFICATION_CODE_SEND_FAIL);
        }
    }

    @Override
    public LoginUser login(ResUser user) {
        Authentication authentication;
        if (Objects.equals(LoginTypeEnum.MAIL.getType(),user.getLoginType())) {
            // 使用邮箱验证码认证器
            authentication = authenticationManager.authenticate(
                    new MailCodeAuthenticationToken(user.getEmail(), user.getCode())
            );
        } else {
            // 使用用户名密码认证器
            //使用authenticate()方法接收UsernamePasswordAuthenticationToken对象，并返回一个包含用户详细信息的 Authentication 对象。
            authentication = authenticationManager.authenticate(
                    //将用户名和密码[和证书]封装到UsernamePasswordAuthenticationToken对象中，用来包装认证信息
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        }
        //getContext()获取当前线程的SecurityContext，并返回一个SecurityContext对象。
        //setAuthentication()设置当前线程的SecurityContext，并将Authentication对象作为参数传入。
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //使用getPrincipal()方法获取认证信息，并转换为UserDetails对象
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResUser resuser = (ResUser) userDetails;
        String token = generateJwtToken(resuser, userDetails);
        return LoginUser.builder().token(token).username(userDetails.getUsername()).build();
    }

    private String generateJwtToken(ResUser resuser, UserDetails userDetails) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", String.valueOf(resuser.getId()));
        payload.put("username", userDetails.getUsername());
        //后期加入系统角色时,在考虑该字段,目前先使用默认的
        payload.put("role", resuser.getRole());
        return JwtUtil.getToken(payload);
    }

    public void sendSecureHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            message.addHeader("X-JDK17-SSL", "TLSv1.3");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ResultEnum.VERIFICATION_CODE_SEND_FAIL);
        }
    }
}
