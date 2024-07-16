package com.rhkr8521.iccas_question.api.survey.service;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.member.repository.MemberRepository;
import com.rhkr8521.iccas_question.api.survey.domain.Survey;
import com.rhkr8521.iccas_question.api.survey.dto.SurveyResponseDTO;
import com.rhkr8521.iccas_question.api.survey.repository.SurveyRepository;
import com.rhkr8521.iccas_question.common.exception.NotFoundException;
import com.rhkr8521.iccas_question.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SurveyResponseDTO addSurvey(String userId, int scoreResult) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER.getMessage()));
        String result, detailResult;

        if (scoreResult < 16) {
            result = "Minimal Risk";
            detailResult = "Your score indicates that there is little in your child's developmental history to indicate that he/she is at risk for a reading disability (dyslexia).";
        } else if (scoreResult < 21) {
            result = "Moderate Risk";
            detailResult = "Your score indicates that there are features of your child's developmental history (e.g. difficulty learning letters, required extra reading help) that may be consistent with a reading disability (dyslexia). Reading disability constitutes a very common learning disability, affecting approximately 5% of the United States population. Reading disability is characterized by slow or effortful reading, difficulty sounding out new words, and problems with spelling";
        } else {
            result = "Significant Risk";
            detailResult = "Your score indicates that there are several features of your child's developmental history (e.g. difficulty learning letters, required extra reading help) that are consistent with a reading disability (dyslexia). Reading disability constitutes a very common learning disability, affecting approximately 5% of the United States population. Reading disability is characterized by slow or effortful reading, difficulty sounding out new words, and problems with spelling. The results of this questionnaire indicate that your child may be experiencing some or all of those symptoms.";
        }

        Survey survey = Survey.builder()
                .member(member)
                .scoreResult(scoreResult)
                .result(result)
                .detailResult(detailResult)
                .build();

        surveyRepository.save(survey);

        return new SurveyResponseDTO(survey.getResult(), survey.getDetailResult());
    }
}
