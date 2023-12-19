package com.example.idea.bussiness.user.repository;

import com.example.idea.bussiness.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}