package com.rhkr8521.iccas_question.api.question.domain;

import com.rhkr8521.iccas_question.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String content;
    private String answer;
    private String theme;
    private Long stage;
}
