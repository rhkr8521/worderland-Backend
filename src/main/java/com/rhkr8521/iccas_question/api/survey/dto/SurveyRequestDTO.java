package com.rhkr8521.iccas_question.api.survey.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurveyRequestDTO {

    private String userId;
    private int scoreResult;
}
