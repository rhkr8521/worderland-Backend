package com.rhkr8521.iccas_question.api.answer.controller;

import com.rhkr8521.iccas_question.api.answer.dto.AnswerRequestDTO;
import com.rhkr8521.iccas_question.api.answer.service.AnswerService;
import com.rhkr8521.iccas_question.common.response.ApiResponse;
import com.rhkr8521.iccas_question.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/api/answer")
    public ResponseEntity<?> checkAnswer(@RequestBody AnswerRequestDTO answerRequestDto) {
        boolean isCorrect = answerService.checkAnswer(answerRequestDto.getQuestionId(), answerRequestDto.getUserId(), answerRequestDto.getAnswer());

        SuccessStatus status = isCorrect ? SuccessStatus.SEND_QUESTION_ANSWER_YES : SuccessStatus.SEND_QUESTION_ANSWER_NO;

        return ResponseEntity.ok(ApiResponse.builder()
                .status(status.getStatusCode())
                .success(true)
                .message(status.getMessage())
                .build());
    }
}
