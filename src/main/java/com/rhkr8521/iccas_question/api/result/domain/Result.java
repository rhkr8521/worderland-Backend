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
    private int correctAnswers;
}
