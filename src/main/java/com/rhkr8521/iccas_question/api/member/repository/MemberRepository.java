package com.rhkr8521.iccas_question.api.member.repository;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
}
