package com.rhkr8521.iccas_question.api.question.controller;

import com.rhkr8521.iccas_question.api.question.dto.QuestionDTO;
import com.rhkr8521.iccas_question.api.question.service.QuestionService;
import com.rhkr8521.iccas_question.common.response.ApiResponse;
import com.rhkr8521.iccas_question.common.response.ErrorStatus;
import com.rhkr8521.iccas_question.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/api/question/{theme}")
    public ApiResponse<QuestionDTO> getQuestionByThemeAndStage(@PathVariable String theme, @RequestParam Long stage) {
        Optional<QuestionDTO> question = questionService.getRandomQuestionByThemeAndStage(theme, stage);
        if (question.isPresent()) {
            return ApiResponse.success(SuccessStatus.SEND_QUESTION_SUCCESS, question.get());
        } else {
            return ApiResponse.fail(ErrorStatus.NOT_FOUND_QUESTION.getStatusCode(), ErrorStatus.NOT_FOUND_QUESTION.getMessage());
        }
    }
}
