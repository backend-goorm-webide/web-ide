package com.example.idea.bussiness.user.repository;

import com.example.idea.bussiness.user.entity.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    Optional<User> findByUserId(String userId);

    Optional<User> findByNameAndEmail(String name, String email);

    void deleteByLastLoginDateBefore(LocalDateTime OneYearAgo); // 마지막 로그인 1년 지나면 회원 삭제
}
