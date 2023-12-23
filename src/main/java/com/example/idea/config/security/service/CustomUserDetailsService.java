package com.example.idea.config.security.service;

import com.example.idea.bussiness.user.entity.Users;
import com.example.idea.bussiness.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인 시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String userId) {

        return userRepository.findOneWithAuthoritiesByUserId(userId)
                .map(user -> createUser(userId, user))
                .orElseThrow(() -> new UsernameNotFoundException("ERR_NOT_VAILD_USER")); // 데이터베이스에서 찾을 수 없습니다.
    }

    private org.springframework.security.core.userdetails.User createUser(String username, Users users) {
        if (users.isWithdrawn()) {
            throw new RuntimeException(username + " 회원님은 탈퇴 처리 중입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = users.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(users.getUserId(),
                users.getPwd(),
                grantedAuthorities);
    }
}
