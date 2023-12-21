package com.example.idea.bussiness.user.service;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class JoinTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserDto validUser;

    @BeforeEach
    public void setup() {
        validUser = new UserDto();
        validUser.setUserId("newUser");
        validUser.setPwd("Password@123");
        validUser.setName("홍길동");
        validUser.setEmail("user@example.com");
        validUser.setPhone("01012345678");
    }

    @Test
    public void testValidUserRegistration() {
        userService.join(validUser);
        User registeredUser = userRepository.findByUserId(validUser.getUserId()).orElse(null);
        assertNotNull(registeredUser);
    }

    @Test
    public void testInvalidUserRegistration() {
        validUser.setEmail("invalidEmail"); // 유효하지 않은 이메일 형식

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            userService.join(validUser);
        });

        String expectedMessage = "이메일 형식이 올바르지 않습니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDuplicateUserIdRegistration() {
        userService.join(validUser);

        UserDto duplicateUser = new UserDto();
        duplicateUser.setUserId(validUser.getUserId()); // 동일한 userId로 새 사용자 생성
        duplicateUser.setPwd("NewPassword@123");
        duplicateUser.setName("이순신");
        duplicateUser.setEmail("newuser@example.com");
        duplicateUser.setPhone("01098765432");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            userService.join(duplicateUser);
        });

        String expectedMessage = "이미 사용중인 아이디입니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}


