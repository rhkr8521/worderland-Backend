package com.rhkr8521.iccas_question.api.result.repository;

import com.rhkr8521.iccas_question.api.result.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByUserIdAndThemeAndStage(String userId, String theme, Long stage);
}
