package com.example.idea.bussiness.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id; // 시퀀스

    @NotNull(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "아이디는 특수문자를 제외한 4~10자리여야 합니다.")
    private String userId; // 사용자ID

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pwd; // 패스워드

    @NotNull(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣_]{1,100}$", message = "이름 형식이 올바르지 않습니다.")
    private String name; // 이름

    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String email; // 이메일

    @Pattern(regexp = "^01\\d{8,9}$", message = "휴대전화번호 형식이 올바르지 않습니다.")
    private String phone; // 휴대폰 번호

    private LocalDateTime lastLoginDate; // 마지막 로그인 일시

    private boolean isWithdrawn; // 탈퇴여부
}

