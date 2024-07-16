package com.rhkr8521.iccas_question.api.result.domain;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder(toBuilder = true)
public class Result extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String theme;
    private Long stage;
    private int correctAnswers;

    @Builder
    public Result(Long id, Member member, String theme, Long stage, int correctAnswers) {
        this.id = id;
        this.member = member;
        this.theme = theme;
        this.stage = stage;
        this.correctAnswers = correctAnswers;
    }
}
