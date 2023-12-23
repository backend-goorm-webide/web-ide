package com.example.idea.bussiness.user.repository;

import com.example.idea.bussiness.user.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Users> findOneWithAuthoritiesByUserId(String userId);
}