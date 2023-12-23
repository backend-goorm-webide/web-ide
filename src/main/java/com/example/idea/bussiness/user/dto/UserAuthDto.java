package com.example.idea.bussiness.user.dto;

//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.validation.constraints.Size;
//import java.time.LocalDateTime;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.antlr.v4.runtime.misc.NotNull;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserAuthDto {
//
//    @NotNull
//    @Size(min = 3, max = 50)
//    private String username;
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull
//    @Size(min = 3, max = 100)
//    private String password;
//
//    @NotNull
//    @Size(min = 3, max = 50)
//    private String nickname;
//}

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@NoArgsConstructor
@ToString
public class UserAuthDto {

    private Long id; // 시퀀스

    @NotNull(message = "아이디는 필수 입력 값입니다.")
//    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "아이디는 특수문자를 제외한 4~10자리여야 합니다.")
    private String userId; // 사용자ID

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 8, max = 255)
    private String pwd; // 패스워드

    @NotNull
    @Size(min = 1, max = 100)
    private String name; // 이름

    @NotNull
    @Email
    private String email; // 이메일

    // 전화번호는 10자리 또는 11자리의 숫자
    @Pattern(regexp = "^\\d{10,11}$", message = "전화번호 형식을 확인해주세요")
    private String phone; // 휴대폰 번호

    private LocalDateTime lastLoginDate; // 마지막 로그인 일시

    private boolean isWithdrawn; // 탈퇴여부

    // default value;
    public UserAuthDto() {
        this.lastLoginDate = LocalDateTime.now();
        this.isWithdrawn = false;
    }
}