package com.example.idea.bussiness.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

public class FindIdTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserId_UserExists() {
        // 가정: 사용자가 존재하는 경우
        String name = "홍길동";
        String email = "hong@example.com";
        User mockUser = new User();
        mockUser.setUserId("user123");
        when(userRepository.findId(name, email)).thenReturn(Optional.of(mockUser));

        // 실행
        UserDto result = userService.findUserId(name, email);

        // 검증
        assertNotNull(result);
        assertEquals("user123", result.getUserId());
    }

    @Test
    public void testFindUserId_UserDoesNotExist() {
        // 가정: 사용자가 존재하지 않는 경우
        String name = "이순신";
        String email = "lee@example.com";
        when(userRepository.findId(name, email)).thenReturn(Optional.empty());

        // 실행 및 검증
        assertThrows(ResponseStatusException.class, () -> {
            userService.findUserId(name, email);
        });
    }
}
