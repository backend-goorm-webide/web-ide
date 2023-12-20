package com.example.idea.bussiness.user.repository;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    // 아이디 중복 여부 확인
    Optional<User> findByUserId(String userId);
}
