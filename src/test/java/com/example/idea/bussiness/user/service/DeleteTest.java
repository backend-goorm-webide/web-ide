package com.example.idea.bussiness.user.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        // 테스트 사용자 생성 및 저장
        User testUser = new User(null, "testUser", "password", "test", "test@example.com", "01012345678", null, false);
        userRepository.save(testUser);
    }

    @Test
    public void deleteUser_Success() throws Exception {
        // 테스트 사용자의 ID
        String userId = "testUser";

        // Principal 객체를 모의
        Principal mockPrincipal = () -> userId;

        // DELETE 요청 수행
        mockMvc.perform(delete("/delete/{userId}", userId).principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("회원 탈퇴가 성공적으로 처리되었습니다.")));
    }

    @Test
    public void deleteUser_NotFound() throws Exception {
        // 존재하지 않는 사용자 ID
        String nonExistingUserId = "nonExistingUser";

        // DELETE 요청 수행
        mockMvc.perform(delete("/delete/{userId}", nonExistingUserId))
                .andExpect(status().isNotFound());
    }
}
