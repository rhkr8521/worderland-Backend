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
        String result;

        if (scoreResult < 16) {
            result = "Minimal Risk";
        } else if (scoreResult < 21) {
            result = "Moderate Risk";
        } else {
            result = "Significant Risk";
        }

        Survey survey = Survey.builder()
                .member(member)
                .scoreResult(scoreResult)
                .result(result)
                .build();

        surveyRepository.save(survey);

        return new SurveyResponseDTO(survey.getResult());
    }
}
