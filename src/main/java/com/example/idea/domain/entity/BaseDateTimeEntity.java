package com.example.idea.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
public class BaseDateTimeEntity {

	@CreatedDate
	@Column(name = "reg_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime regDate; // 등록일

	@LastModifiedDate
	@Column(name ="mod_at")
	private LocalDateTime modDate; // 정보 수정일
}
