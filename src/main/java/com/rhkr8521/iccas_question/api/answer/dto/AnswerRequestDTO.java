package com.rhkr8521.iccas_question.api.answer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerRequestDTO {
    private Long questionId;
    private String userId;
    private String answer;
}
