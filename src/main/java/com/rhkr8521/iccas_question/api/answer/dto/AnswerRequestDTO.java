package com.rhkr8521.iccas_question.api.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequestDTO {
    private Long questionId;
    private String userId;
    private String answer;
}
