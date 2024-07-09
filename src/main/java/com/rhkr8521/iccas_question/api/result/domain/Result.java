package com.rhkr8521.iccas_question.api.result.domain;

import com.rhkr8521.iccas_question.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Result extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String theme;
    private Long stage;

    private int totalQuestions;
    private int totalCorrectAnswers;

    private int recentCorrectAnswers;
    private int highCorrectAnswers;

    public void incrementTotalQuestions() {
        this.totalQuestions++;
    }

    public void incrementTotalCorrectAnswers() {
        this.totalCorrectAnswers++;
    }

    public void updateRecentCorrectAnswers(int correctAnswers) {
        this.recentCorrectAnswers = correctAnswers;
    }

    public void updateHighCorrectAnswers(int correctAnswers) {
        if (correctAnswers > this.highCorrectAnswers) {
            this.highCorrectAnswers = correctAnswers;
        }
    }
}
