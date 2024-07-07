package com.rhkr8521.iccas_question.api.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class AnswerRequestDTO {
    private Long questionId;
    private String userId;
    private String answer;
}
