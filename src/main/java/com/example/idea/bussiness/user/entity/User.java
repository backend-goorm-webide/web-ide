package com.example.idea.bussiness.user.entity;

import com.example.idea.bussiness.user.dto.UserDto;
import com.example.idea.common.entity.BaseDateTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@JsonIgnoreProperties({"pwd"})
@Getter
@Setter
@ToString
public class User extends BaseDateTimeEntity implements Serializable {

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

    @Column(length = 16)
    private String phone; // 휴대폰 번호

    @Column(name = "last_login_at", nullable = true, columnDefinition = "TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime lastLoginDate; // 마지막 로그인 일시

    @Column
    private boolean isWithdrawn; // 탈퇴여부

    public static User toUserEntity(UserDto userDto) { //dto -> entity
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setPwd(userDto.getPwd());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        return user;
    }
}
