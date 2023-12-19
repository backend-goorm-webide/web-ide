package com.example.idea.bussiness.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.idea.IdeaApplication;
import com.example.idea.bussiness.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

//import org.springframework.security.crypto.password.PasswordEncoder;

//@RunWith(SpringRunner.class) // JUnit 4 일경우,
@DataJpaTest
@ContextConfiguration(classes = IdeaApplication.class) // @SEE: https://velog.io/@gillog/VSCode-jUnit-Test-실행-안될-때-Could-not-detect-default-configuration-classes-for-test-class
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // @SEE: https://charliezip.tistory.com/21
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserEntity() {
        // given
        User user = User.builder()
                .userId("testUser")
                .pwd("password")
                .name("Test Name")
                .email("test@example.com")
                .phone("01012345678")
                .isWithdrawn(false)
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo("testUser");
    }
}