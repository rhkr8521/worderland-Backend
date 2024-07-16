package com.rhkr8521.iccas_question.api.result.repository;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.result.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByMemberAndThemeAndStage(Member member, String theme, Long stage);
    List<Result> findByMemberAndTheme(Member member, String theme);

}
