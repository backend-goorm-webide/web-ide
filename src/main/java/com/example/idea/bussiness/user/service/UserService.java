package com.example.idea.bussiness.user.service;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.entity.User;
import com.example.idea.bussiness.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원 가입
    public void join(UserDto userDto) {
        User user = User.toUserEntity(userDto);

        if (isUserIdDuplicate(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다.");        }
        userRepository.save(user);
    }

    // 아이디 중복 확인
    public boolean isUserIdDuplicate(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.isPresent(); // 사용자가 존재하면 true, 그렇지 않으면 false 반환
    }

    // 유효성 체크 (실패한 필드들은 키값과 에러 메시지를 응답)
    @Transactional(readOnly = true)
    public Map<String, String> validationErrors(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        // 유효성 검사에 실패한 필드 목록을 받음
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
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
        Optional<User> userOptional = userRepository.findByIdAndEmail(name, email);

        return userOptional.map(user -> {
            return UserDto.builder()
                    .userId(user.getUserId())
                    .build();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));
    }
}


