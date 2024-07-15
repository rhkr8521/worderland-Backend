package com.rhkr8521.iccas_question.api.result.repository;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.result.domain.GameSet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameSetRepository extends JpaRepository<GameSet, Long> {
    List<GameSet> findByMemberAndTheme(Member member, String theme);
}
