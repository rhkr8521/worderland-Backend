package com.rhkr8521.iccas_question.api.result.controller;

import com.rhkr8521.iccas_question.api.result.dto.ResultResponseDTO;
import com.rhkr8521.iccas_question.api.result.dto.ReturnRecordResponseDTO;
import com.rhkr8521.iccas_question.api.result.service.ResultService;
import com.rhkr8521.iccas_question.common.response.ApiResponse;
import com.rhkr8521.iccas_question.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/api/result/{userId}")
    public ResponseEntity<?> getBestResultsByTheme(@PathVariable String userId, @RequestParam String theme) {
        List<ResultResponseDTO> bestResults = resultService.getBestResults(userId, theme);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(SuccessStatus.SEND_RESULT.getStatusCode())
                .success(true)
                .message(SuccessStatus.SEND_RESULT.getMessage())
                .data(bestResults)
                .build());
    }

    @GetMapping("/api/result/return")
    public ResponseEntity<?> getRecordsByUserId(@RequestParam String userId) {
        List<ReturnRecordResponseDTO> userResult = resultService.getUserResults(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(SuccessStatus.SEND_USER_GAME_RESULT.getStatusCode())
                .success(true)
                .message(SuccessStatus.SEND_USER_GAME_RESULT.getMessage())
                .data(userResult)
                .build());
    }

}
