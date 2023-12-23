package com.example.idea.bussiness.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.idea.IdeaApplication;
import com.example.idea.bussiness.user.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

//import org.springframework.security.crypto.password.PasswordEncoder;

//@RunWith(SpringRunner.class) // JUnit 4 일경우,
//@DataJpaTest
@SpringBootTest
@ContextConfiguration(classes = IdeaApplication.class) // @SEE: https://velog.io/@gillog/VSCode-jUnit-Test-실행-안될-때-Could-not-detect-default-configuration-classes-for-test-class
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // @SEE: https://charliezip.tistory.com/21
//@Import(SecurityConfig.class)
class UsersRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testUserEntity() {
        // given
//        String userPwd = passwordEncoder.encode("adminPwd");
        String userPwd = passwordEncoder.encode("Rnfma1234!");
//        String userPwd = passwordEncoder.encode("12351235");
        Users users = Users.builder()
                .id(2L)
                .userId("testUser")
                .pwd(userPwd)
                .name("Test Name")
                .email("test@example.com")
                .phone("01012345678")
                .isWithdrawn(false)
                .build();

        // when
        Users savedUsers = userRepository.save(users);

        // then
        assertThat(savedUsers).isNotNull();
        assertThat(savedUsers.getUserId()).isEqualTo("testUser");
        System.out.println(userPwd);
    }
}