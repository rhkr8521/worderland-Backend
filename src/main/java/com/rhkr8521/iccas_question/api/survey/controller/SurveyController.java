package com.rhkr8521.iccas_question.api.survey.controller;

import com.rhkr8521.iccas_question.api.survey.dto.SurveyRequestDTO;
import com.rhkr8521.iccas_question.api.survey.dto.SurveyResponseDTO;
import com.rhkr8521.iccas_question.api.survey.service.SurveyService;
import com.rhkr8521.iccas_question.common.response.ApiResponse;
import com.rhkr8521.iccas_question.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("api/survey/add")
    public ResponseEntity<ApiResponse<SurveyResponseDTO>> addSurvey(@RequestBody SurveyRequestDTO surveyRequest) {
        SurveyResponseDTO response = surveyService.addSurvey(surveyRequest.getUserId(), surveyRequest.getScoreResult());

        ApiResponse<SurveyResponseDTO> apiResponse = ApiResponse.<SurveyResponseDTO>builder()
                .status(SuccessStatus.ADD_SURVEY_SCORE.getStatusCode())
                .success(true)
                .message(SuccessStatus.ADD_SURVEY_SCORE.getMessage())
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
