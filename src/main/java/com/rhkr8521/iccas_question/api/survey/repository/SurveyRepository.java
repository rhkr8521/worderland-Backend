package com.rhkr8521.iccas_question.api.survey.repository;

import com.rhkr8521.iccas_question.api.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}