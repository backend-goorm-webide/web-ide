package com.example.idea.bussiness.user.service;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class JoinTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private BindingResult bindingResult;

    private UserDto validUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화

        validUser = new UserDto();
        validUser.setUserId("newUser");
        validUser.setPwd("Password@123");
        validUser.setName("홍길동");
        validUser.setEmail("user@example.com");
        validUser.setPhone("01012345678");

        // 유효성 검사에서 에러가 없음을 가정
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void testValidUserRegistration() {
        userService.join(validUser, bindingResult);
        User registeredUser = userRepository.findByUserId(validUser.getUserId()).orElse(null);
        assertNotNull(registeredUser);
    }

    @Test
    public void testInvalidUserRegistration() {
        UserDto invalidUser = new UserDto();
        invalidUser.setEmail("invalidEmail"); // 유효하지 않은 이메일 설정

        // 유효성 검사에서 에러가 있음을 가정
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            userService.join(invalidUser, bindingResult);
        });
    }

    @Test
    public void testDuplicateUserIdRegistration() {
        userService.join(validUser, bindingResult);

        UserDto duplicateUser = new UserDto();
        duplicateUser.setUserId(validUser.getUserId()); // 동일한 userId로 새 사용자 생성
        duplicateUser.setPwd("NewPassword@123");
        duplicateUser.setName("이순신");
        duplicateUser.setEmail("newuser@example.com");
        duplicateUser.setPhone("01098765432");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.join(duplicateUser, bindingResult);
        });
    }
}
