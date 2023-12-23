package com.example.idea.bussiness.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// h2-db authority 테이블 entity
@Entity
//@Table(name = "AUTHORITY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
//    @Column(name = "AUTHORITY_NAME", length = 50)
    @Column(length = 50)
    private String authorityName;
}
