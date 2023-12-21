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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 페이지 출력
    @GetMapping("/users/join")
    public String showJoinForm() {
        return "users/joinForm";
    }

    // 회원가입 및 유효성 검증
    @PostMapping("/users/join")
    public String join(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 있을 때의 처리 로직
            model.addAttribute("userDto", userDto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            // 회원가입 페이지로 리턴
            return "users/joinForm";
        }
        userService.join(userDto);
        return "redirect:/";
    }

    // 아이디 중복 확인
    @GetMapping("/users/check-Id")
    public ResponseEntity<?> checkDuplicateUserId(@RequestParam String userId) {
        boolean isDuplicate = userService.isUserIdDuplicate(userId);
        return ResponseEntity.ok().body(Map.of("isDuplicate", isDuplicate));
    }

    // 내 정보 조회
    @GetMapping("/users/my-info")
    public String viewProfile(Model model, Principal principal) {
        String userId = principal.getName(); // 현재 로그인한 사용자의 아이디

        UserDto userDto = userService.findByUserId(userId);
        model.addAttribute("userDto", userDto);
        return "users/my-info";
    }

    // 회원 탈퇴

    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<String> deleteUser(Principal principal) {
        String userId = principal.getName();
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
