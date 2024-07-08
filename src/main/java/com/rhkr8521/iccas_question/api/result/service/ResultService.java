package com.rhkr8521.iccas_question.api.result.service;

import com.rhkr8521.iccas_question.api.result.domain.Result;
import com.rhkr8521.iccas_question.api.result.dto.ResultResponseDTO;
import com.rhkr8521.iccas_question.api.result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    @Transactional
    public List<ResultResponseDTO> getResultsByTheme(String userId, String theme) {
        return resultRepository.findByUserIdAndTheme(userId, theme).stream()
                .map(result -> ResultResponseDTO.builder()
                        .userId(result.getUserId())
                        .theme(result.getTheme())
                        .stage(result.getStage())
                        .totalQuestions(result.getTotalQuestions())
                        .totalCorrectAnswers(result.getTotalCorrectAnswers())
                        .recentCorrectAnswers(result.getRecentCorrectAnswers())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateResult(String userId, String theme, Long stage, boolean isCorrect) {
        if (!resultRepository.existsByUserId(userId)) {
            initializeResult(userId);
        }

        Result result = resultRepository.findByUserIdAndThemeAndStage(userId, theme, stage)
                .orElseThrow(() -> new IllegalStateException("Result should be initialized but was not found"));

        result.incrementTotalQuestions();
        if (isCorrect) {
            result.incrementTotalCorrectAnswers();
        }

        int recentCorrectAnswers = result.getRecentCorrectAnswers();

        if (stage == 1L || stage == 2L) {
            // 10문제 단위로 맞춘 갯수를 갱신
            if (result.getTotalQuestions() % 10 == 0 && isCorrect) {
                recentCorrectAnswers = 10;
            } else if (result.getTotalQuestions() % 10 == 1) {
                recentCorrectAnswers = isCorrect ? 1 : 0;
            } else if (isCorrect) {
                recentCorrectAnswers++;
            }
        } else if (stage == 3L) {
            // 1문제 단위로 맞춘 갯수를 갱신
            recentCorrectAnswers = isCorrect ? 1 : 0;
        }

        result.updateRecentCorrectAnswers(recentCorrectAnswers);
        resultRepository.save(result);
    }

    @Transactional
    public void initializeResult(String userId) {
        List<String> themes = List.of("carousel", "ferris_wheel", "roller_coaster");
        for (String theme : themes) {
            for (Long stage = 1L; stage <= 3L; stage++) {
                Result result = Result.builder()
                        .userId(userId)
                        .theme(theme)
                        .stage(stage)
                        .totalQuestions(0)
                        .totalCorrectAnswers(0)
                        .recentCorrectAnswers(0)
                        .build();
                resultRepository.save(result);
            }
        }
    }
}
