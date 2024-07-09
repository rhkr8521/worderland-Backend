package com.rhkr8521.iccas_question.api.result.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponseDTO {
    private String userId;
    private String theme;
    private Long stage;
    private int totalQuestions;
    private int totalCorrectAnswers;
    private int recentCorrectAnswers;
    private Integer highCorrectAnswers;

    @Builder
    public ResultResponseDTO(String userId, String theme, Long stage, int totalQuestions, int totalCorrectAnswers, int recentCorrectAnswers, Integer highCorrectAnswers) {
        this.userId = userId;
        this.theme = theme;
        this.stage = stage;
        this.totalQuestions = totalQuestions;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.recentCorrectAnswers = recentCorrectAnswers;
        this.highCorrectAnswers = highCorrectAnswers;
    }
}
