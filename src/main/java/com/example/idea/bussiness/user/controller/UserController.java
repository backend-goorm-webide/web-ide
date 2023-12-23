package com.example.idea.bussiness.user.controller;

// 회원 가입 및 사용자 정보 조회 controller

import com.example.idea.bussiness.user.dto.UserAuthDto;
import com.example.idea.bussiness.user.entity.Users;
import com.example.idea.bussiness.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/users/join")
    public ResponseEntity<Users> signup(
            @Valid @RequestBody UserAuthDto userAuthDto
    ) {
        return ResponseEntity.ok(userService.join(userAuthDto));
    }

    @GetMapping("/users/my-info")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Users> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

//    @GetMapping("/user/{username}")
    @GetMapping("/user/info/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }

    @GetMapping("/adm/test")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("{test : user}");
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("{test : user}");
    }
}
