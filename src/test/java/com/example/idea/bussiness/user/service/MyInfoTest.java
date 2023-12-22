package com.example.idea.bussiness.user.service;

import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class MyInfoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository; // UserRepository 주입

    @Test
    @Transactional // 트랜잭션 설정
    public void testViewProfile() throws Exception {
        // 테스트를 위한 사용자 데이터 추가
        User user = new User();
        user.setUserId("testUser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        userRepository.save(user);

        // /my-info 엔드포인트를 호출하고 응답을 검증합니다.
        mockMvc.perform(MockMvcRequestBuilders.get("/my-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> "testUser")) // 사용자 아이디를 principal로 설정
                .andExpect(MockMvcResultMatchers.status().isOk()) // HTTP 상태 코드 200 확인
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // JSON 응답 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("testUser")) // 사용자 아이디 필드 검증
        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }
}
