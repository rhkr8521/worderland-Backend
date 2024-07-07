package com.rhkr8521.iccas_question.api.answer.repository;

import com.rhkr8521.iccas_question.api.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
