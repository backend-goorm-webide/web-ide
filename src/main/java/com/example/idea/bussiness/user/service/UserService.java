package com.example.idea.bussiness.user.service;

import com.example.idea.bussiness.user.dto.UserAuthDto;
import com.example.idea.bussiness.user.entity.Authority;
import com.example.idea.bussiness.user.entity.Users;
import com.example.idea.bussiness.user.repository.UserRepository;
import com.example.idea.config.security.SecurityUtil;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Users join(UserAuthDto userAuthDto) {
        if (userRepository.findOneWithAuthoritiesByUserId(userAuthDto.getUserId()).orElse(null) != null) {
            throw new RuntimeException("ERR_DUPLICATED_USER_ID"); // 이미 사용중인 ‘아이디’ 입니다.
        }

        // 신규 회원이라면,
        // 1. 권한 정보 생성
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 2. User 정보 Build
        Users users = Users.builder()
                .userId(userAuthDto.getUserId())
                .pwd(passwordEncoder.encode(userAuthDto.getPwd()))
                .name(userAuthDto.getName())
                .email(userAuthDto.getEmail())
                .phone(userAuthDto.getPhone())
                .authorities(Collections.singleton(authority))
                .isWithdrawn(false)
                .build();

        // 3. 저장
        return userRepository.save(users);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Users> getUserWithAuthorities(String userId) {
        return userRepository.findOneWithAuthoritiesByUserId(userId);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Users> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUserId);
    }
}
