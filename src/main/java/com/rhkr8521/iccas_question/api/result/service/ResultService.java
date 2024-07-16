package com.rhkr8521.iccas_question.api.result.service;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.member.repository.MemberRepository;
import com.rhkr8521.iccas_question.api.result.domain.GameSet;
import com.rhkr8521.iccas_question.api.result.domain.Result;
import com.rhkr8521.iccas_question.api.result.dto.ResultResponseDTO;
import com.rhkr8521.iccas_question.api.result.repository.GameSetRepository;
import com.rhkr8521.iccas_question.api.result.repository.ResultRepository;
import com.rhkr8521.iccas_question.common.exception.NotFoundException;
import com.rhkr8521.iccas_question.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final GameSetRepository gameSetRepository;
    private final ResultRepository resultRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void updateGameSet(String userId, String theme, Long stage, boolean isCorrect) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER.getMessage()));

        GameSet currentGameSet = gameSetRepository.findByMemberAndTheme(member, theme).stream()
                .filter(gs -> gs.getFirstStageTotalCount() < 5 || gs.getSecondStageTotalCount() < 5 || gs.getThirdStageTotalCount() < 1)
                .findFirst()
                .orElseGet(() -> {
                    GameSet newGameSet = GameSet.builder()
                            .member(member)
                            .theme(theme)
                            .firstStageRecord(0)
                            .firstStageTotalCount(0)
                            .secondStageRecord(0)
                            .secondStageTotalCount(0)
                            .thirdStageRecord(0)
                            .thirdStageTotalCount(0)
                            .build();
                    return gameSetRepository.save(newGameSet);
                });

        if (stage == 1L) {
            currentGameSet = currentGameSet.updateFirstStageRecord(isCorrect ? 1 : 0);
        } else if (stage == 2L) {
            currentGameSet = currentGameSet.updateSecondStageRecord(isCorrect ? 1 : 0);
        } else if (stage == 3L) {
            currentGameSet = currentGameSet.updateThirdStageRecord(isCorrect ? 1 : 0);
        }

        gameSetRepository.save(currentGameSet);

        if (currentGameSet.getFirstStageTotalCount() == 5 && currentGameSet.getSecondStageTotalCount() == 5 && currentGameSet.getThirdStageTotalCount() == 1) {
            updateResultsWithBestGameSet(member, theme);
            GameSet newGameSet = GameSet.builder()
                    .member(member)
                    .theme(theme)
                    .firstStageRecord(0)
                    .firstStageTotalCount(0)
                    .secondStageRecord(0)
                    .secondStageTotalCount(0)
                    .thirdStageRecord(0)
                    .thirdStageTotalCount(0)
                    .build();
            gameSetRepository.save(newGameSet);
        }
    }

    private void updateResultsWithBestGameSet(Member member, String theme) {
        List<GameSet> gameSets = gameSetRepository.findByMemberAndTheme(member, theme);

        if (gameSets.isEmpty()) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_RESULT.getMessage());
        }

        GameSet bestGameSet = gameSets.stream()
                .max(Comparator.comparingDouble(GameSet::getTotalAccuracy))
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_RESULT.getMessage()));

        updateOrSaveResult(member, theme, 1L, bestGameSet.getFirstStageRecord());
        updateOrSaveResult(member, theme, 2L, bestGameSet.getSecondStageRecord());
        updateOrSaveResult(member, theme, 3L, bestGameSet.getThirdStageRecord());
    }

    private void updateOrSaveResult(Member member, String theme, Long stage, int correctAnswers) {
        Result result = resultRepository.findByMemberAndThemeAndStage(member, theme, stage)
                .orElse(Result.builder()
                        .member(member)
                        .theme(theme)
                        .stage(stage)
                        .correctAnswers(correctAnswers)
                        .build());

        result = result.toBuilder()
                .member(result.getMember())
                .theme(result.getTheme())
                .stage(result.getStage())
                .correctAnswers(correctAnswers)
                .build();

        resultRepository.save(result);
    }

    @Transactional(readOnly = true)
    public List<ResultResponseDTO> getBestResults(String userId, String theme) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER.getMessage()));

        List<Result> results = resultRepository.findByMemberAndTheme(member, theme);

        if (results.isEmpty()) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_RESULT.getMessage());
        }

        return results.stream()
                .map(result -> ResultResponseDTO.builder()
                        .userId(result.getMember().getUserId())
                        .theme(result.getTheme())
                        .stage(result.getStage())
                        .correctAnswers(result.getCorrectAnswers())
                        .build())
                .collect(Collectors.toList());
    }
}
