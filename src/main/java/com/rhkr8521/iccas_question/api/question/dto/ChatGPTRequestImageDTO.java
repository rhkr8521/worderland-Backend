package com.rhkr8521.iccas_question.api.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatGPTRequestImageDTO{

    private String prompt;
    private int n;
    private String size;

    @Builder
    public ChatGPTRequestImageDTO(String prompt, int n, String size) {
        this.prompt = prompt;
        this.n = n;
        this.size = size;
    }
}
