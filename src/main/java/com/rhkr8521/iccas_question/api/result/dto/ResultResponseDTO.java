package com.rhkr8521.iccas_question.api.result.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponseDTO {
    private String userId;
    private String theme;
    private Long stage;
    private int totalQuestions;
    private int totalCorrectAnswers;
    private int recentCorrectAnswers;
}
