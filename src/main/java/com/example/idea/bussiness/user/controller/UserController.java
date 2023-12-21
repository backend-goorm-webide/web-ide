package com.example.idea.bussiness.user.controller;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.bussiness.user.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 및 유효성 검증
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 있을 때의 처리
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);
            return ResponseEntity.badRequest().body(validatorResult);
        }
        userService.join(userDto);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }


    // 아이디 중복 확인
    @GetMapping("/check-Id")
    public ResponseEntity<?> checkDuplicateUserId(@RequestParam String userId) {
        boolean isDuplicate = userService.isUserIdDuplicate(userId);
        return ResponseEntity.ok().body(Map.of("isDuplicate", isDuplicate));
    }

    // 내 정보 조회
    @GetMapping("/my-info")
    public ResponseEntity<?> viewProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String userId = principal.getName(); // 현재 로그인한 사용자의 아이디

        UserDto userDto = userService.findByUserId(userId);
        return ResponseEntity.ok().body(userDto);
    }

    // 회원 탈퇴
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId, Principal principal) {
        if (principal == null || !principal.getName().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
    }
}
