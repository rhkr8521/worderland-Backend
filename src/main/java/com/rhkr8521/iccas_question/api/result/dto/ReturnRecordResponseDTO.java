package com.rhkr8521.iccas_question.api.result.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class ReturnRecordResponseDTO {
    private String userId;
    private String theme;
    private String date;
    private Long stage;
    private int correctAnswers;
    private float first_stage_best_record;
    private float first_stage_average_record;
    private float second_stage_best_record;
    private float second_stage_average_record;
    private float third_stage_best_record;
    private float third_stage_average_record;
    private float total_best_record;
    private float total_average_record;
}
