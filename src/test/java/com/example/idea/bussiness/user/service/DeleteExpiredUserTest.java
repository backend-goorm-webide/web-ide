package com.example.idea.bussiness.user.service;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteExpiredUserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    void deleteExpiredOldMembers() {
        // 로그인 날짜가 오래된 사용자 생성
        User oldUser = new User(null, "oldtestUser", "password", "test", "test@example.com", "01012345678", null,
                false);
        oldUser.setLastLoginDate(LocalDateTime.now().minusYears(2));
        userRepository.save(oldUser);

        // 스케줄된 작업 실행
        userService.deleteExpiredUser();

        // 오래된 사용자가 삭제되었는지 확인
        assertFalse(userRepository.findByUserId(oldUser.getUserId()).isPresent());
    }

    @Test
    void deleteExpiredNewMembers() {
        // 최근 로그인한 사용자 생성
        User newUser = new User(null, "newtestUser", "password", "test", "test@example.com", "01012345678", null,
                false);
        newUser.setLastLoginDate(LocalDateTime.now());
        userRepository.save(newUser);

        // 스케줄된 작업 실행
        userService.deleteExpiredUser();

        // 최근 로그인한 사용자가 그대로 존재하는지 확인
        assertTrue(userRepository.findByUserId(newUser.getUserId()).isPresent());
    }
}
