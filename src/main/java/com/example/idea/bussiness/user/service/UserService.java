package com.example.idea.bussiness.user.service;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원 가입
    public void join(UserDto userDto, BindingResult bindingResult) {
        // 유효성 검사 실패 처리
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = validationErrors(bindingResult);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
        }
        // 아이디 중복 체크
        if (isUserIdDuplicate(userDto.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        try {
            User user = User.toUserEntity(userDto);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 예외 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "데이터 무결성 위반: " + e.getMessage());
        }
    }

    // 아이디 중복 확인
    public boolean isUserIdDuplicate(String userId) {
        return userRepository.existsByUserId(userId);
    }

    // 유효성 체크
    public Map<String, String> validationErrors(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            validatorResult.put(error.getField(), error.getDefaultMessage());
        }
        return validatorResult;
    }

    // 내 정보 조회
    public UserDto findByUserId(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDto.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .build();
        } else {
            throw new RuntimeException("회원을 찾을 수 없습니다: " + userId);
        }
    }

    // 회원 탈퇴
    public void deleteUser(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setWithdrawn(true); // 회원 탈퇴 여부를 true로 설정
            userRepository.save(user);
        } else {
            throw new RuntimeException("회원을 찾을 수 없습니다: " + userId);
        }
    }

    // 1년동안 로그인 하지 않으면 회원 삭제
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행되도록 스케줄링 설정
    public void deleteExpiredMembers() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        userRepository.deleteByLastLoginDate(oneYearAgo);
    }

    // 아이디 찾기
    public UserDto findUserId(String name, String email) {
        Optional<User> userOptional = userRepository.findByNameAndEmail(name, email);

        return userOptional.map(user -> {
            return UserDto.builder()
                    .userId(user.getUserId())
                    .build();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));
    }
}


