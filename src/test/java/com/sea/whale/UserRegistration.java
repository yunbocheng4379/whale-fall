package com.sea.whale;

import com.sea.whale.entity.dto.UserDTO;
import com.sea.whale.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-26 21:43
 */
@SpringBootTest
public class UserRegistration {

    @Autowired
    private UserService userService;

    @Test
    public void insertUser() {
        UserDTO userDTO = UserDTO.builder()
                .userName("王蓉")
                .password(new BCryptPasswordEncoder().encode("88888888"))
                .userRole(2)
                .flag(1)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        userService.insertUser(userDTO);
    }

}
