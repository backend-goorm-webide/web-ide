package com.example.idea.member.entity;

import com.example.idea.domain.entity.BaseDateTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
//@Table(name = "IDE_MEMBER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@JsonIgnoreProperties({ "pwd" })
@Getter
@ToString
public class Member extends BaseDateTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id; // 시퀀스

    @Column(nullable = false, unique = true)
    private String userId; // 사용자ID

//    @Column(nullable = false)
    private String pwd; // 패스워드

    @Column(nullable = false, length = 100)
    private String name; // 이름

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false, length = 16)
    private String phone; // 휴대폰 번호

    @Column(name = "last_login_at", nullable = true, columnDefinition = "TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime lastLoginDate; // 마지막 로그인 일시

    @Column
    private boolean isWithdrawn; // 탈퇴여부
}