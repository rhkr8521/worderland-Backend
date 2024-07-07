package com.rhkr8521.iccas_question.api.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionDTO {
    private Long questionId;
    private String content;
}
