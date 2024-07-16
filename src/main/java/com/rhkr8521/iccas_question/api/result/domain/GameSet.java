package com.rhkr8521.iccas_question.api.result.domain;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder(toBuilder = true)
public class GameSet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String theme;
    private int firstStageRecord;
    private int firstStageTotalCount;
    private int secondStageRecord;
    private int secondStageTotalCount;
    private int thirdStageRecord;
    private int thirdStageTotalCount;

    @Builder
    public GameSet(Member member, String theme, int firstStageRecord, int firstStageTotalCount,
                   int secondStageRecord, int secondStageTotalCount, int thirdStageRecord, int thirdStageTotalCount) {
        this.member = member;
        this.theme = theme;
        this.firstStageRecord = firstStageRecord;
        this.firstStageTotalCount = firstStageTotalCount;
        this.secondStageRecord = secondStageRecord;
        this.secondStageTotalCount = secondStageTotalCount;
        this.thirdStageRecord = thirdStageRecord;
        this.thirdStageTotalCount = thirdStageTotalCount;
    }

    public int getTotalCorrectAnswers() {
        return firstStageRecord + secondStageRecord + thirdStageRecord;
    }

    public double getTotalAccuracy() {
        return (firstStageRecord / 5.0) + (secondStageRecord / 5.0) + (thirdStageRecord / 1.0);
    }

    public GameSet updateFirstStageRecord(int increment) {
        return this.toBuilder()
                .firstStageRecord(this.firstStageRecord + increment)
                .firstStageTotalCount(this.firstStageTotalCount + 1)
                .build();
    }

    public GameSet updateSecondStageRecord(int increment) {
        return this.toBuilder()
                .secondStageRecord(this.secondStageRecord + increment)
                .secondStageTotalCount(this.secondStageTotalCount + 1)
                .build();
    }

    public GameSet updateThirdStageRecord(int increment) {
        return this.toBuilder()
                .thirdStageRecord(this.thirdStageRecord + increment)
                .thirdStageTotalCount(this.thirdStageTotalCount + 1)
                .build();
    }

    public GameSet resetStageRecords() {
        return this.toBuilder()
                .firstStageRecord(0)
                .firstStageTotalCount(0)
                .secondStageRecord(0)
                .secondStageTotalCount(0)
                .thirdStageRecord(0)
                .thirdStageTotalCount(0)
                .build();
    }
}
