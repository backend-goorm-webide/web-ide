package com.example.idea.bussiness.member.repository;

import com.example.idea.bussiness.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}