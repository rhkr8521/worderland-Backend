package com.rhkr8521.iccas_question.api.result.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReturnRecordResponseDTO {
    private String date;
    private List<ThemeRecordDTO> result;

    @Getter
    @Builder
    public static class ThemeRecordDTO {
        private String theme;
        private double firstStageBestRecord;
        private double firstStageAverageRecord;
        private double secondStageBestRecord;
        private double secondStageAverageRecord;
        private double thirdStageBestRecord;
        private double thirdStageAverageRecord;
        private double totalBestRecord;
        private double totalAverageRecord;
        private double totalPlayRecord;
    }
}
