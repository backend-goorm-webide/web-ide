package com.example.idea.config.security.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IDEUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // @TODO: 여기에서 데이터베이스로부터 사용자 정보를 조회합니다.
        // 여기서는 예시를 위해 하드코딩된 사용자 정보를 반환합니다.
        return new User("userId", "pwd", new ArrayList<>());
    }
}
