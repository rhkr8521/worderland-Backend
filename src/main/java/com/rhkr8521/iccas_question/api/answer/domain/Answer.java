package com.rhkr8521.iccas_question.api.answer.domain;

import com.rhkr8521.iccas_question.api.question.domain.Question;
import com.rhkr8521.iccas_question.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question question;

    private String userId;

    @Enumerated(EnumType.STRING)
    private Result result;

    public enum Result {
        OK,
        FAIL
    }
}