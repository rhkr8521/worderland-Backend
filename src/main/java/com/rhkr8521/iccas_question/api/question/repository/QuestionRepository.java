package com.rhkr8521.iccas_question.api.question.repository;

import com.rhkr8521.iccas_question.api.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByThemeAndStage(String theme, Long stage);
}
