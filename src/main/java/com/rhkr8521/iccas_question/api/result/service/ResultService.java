package com.rhkr8521.iccas_question.api.result.service;

import com.rhkr8521.iccas_question.api.result.domain.GameSet;
import com.rhkr8521.iccas_question.api.result.domain.Result;
import com.rhkr8521.iccas_question.api.result.dto.ResultResponseDTO;
import com.rhkr8521.iccas_question.api.result.repository.GameSetRepository;
import com.rhkr8521.iccas_question.api.result.repository.ResultRepository;
import com.rhkr8521.iccas_question.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final GameSetRepository gameSetRepository;
    private final ResultRepository resultRepository;

    @Transactional
    public void updateGameSet(String userId, String theme, Long stage, boolean isCorrect) {
        GameSet currentGameSet = gameSetRepository.findByUserIdAndTheme(userId, theme).stream()
                .filter(gs -> gs.getFirstStageTotalCount() < 5 || gs.getSecondStageTotalCount() < 5 || gs.getThirdStageTotalCount() < 1)
                .findFirst()
                .orElseGet(() -> {
                    GameSet newGameSet = GameSet.builder()
                            .userId(userId)
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
            updateResultsWithBestGameSet(userId, theme);
            GameSet newGameSet = GameSet.builder()
                    .userId(userId)
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

    private void updateResultsWithBestGameSet(String userId, String theme) {
        List<GameSet> gameSets = gameSetRepository.findByUserIdAndTheme(userId, theme);

        if (gameSets.isEmpty()) {
            throw new NotFoundException("해당 사용자는 게임 플레이 기록이 없습니다.");
        }

        GameSet bestGameSet = gameSets.stream()
                .max(Comparator.comparingDouble(GameSet::getTotalAccuracy))
                .orElseThrow(() -> new NotFoundException("해당 사용자는 게임 플레이 기록이 없습니다."));

        updateOrSaveResult(userId, theme, 1L, bestGameSet.getFirstStageRecord());
        updateOrSaveResult(userId, theme, 2L, bestGameSet.getSecondStageRecord());
        updateOrSaveResult(userId, theme, 3L, bestGameSet.getThirdStageRecord());
    }

    private void updateOrSaveResult(String userId, String theme, Long stage, int correctAnswers) {
        Result result = resultRepository.findByUserIdAndThemeAndStage(userId, theme, stage)
                .orElse(Result.builder()
                        .userId(userId)
                        .theme(theme)
                        .stage(stage)
                        .correctAnswers(correctAnswers)
                        .build());

        result = Result.builder()
                .id(result.getId())
                .userId(result.getUserId())
                .theme(result.getTheme())
                .stage(result.getStage())
                .correctAnswers(correctAnswers)
                .build();

        resultRepository.save(result);
    }

    @Transactional(readOnly = true)
    public List<ResultResponseDTO> getBestResults(String userId, String theme) {
        List<GameSet> gameSets = gameSetRepository.findByUserIdAndTheme(userId, theme);

        if (gameSets.isEmpty()) {
            throw new NotFoundException("해당 사용자는 게임 플레이 기록이 없습니다.");
        }

        GameSet bestGameSet = gameSets.stream()
                .max(Comparator.comparingDouble(GameSet::getTotalAccuracy))
                .orElseThrow(() -> new NotFoundException("해당 사용자는 게임 플레이 기록이 없습니다."));

        return List.of(
                ResultResponseDTO.builder()
                        .userId(bestGameSet.getUserId())
                        .theme(bestGameSet.getTheme())
                        .stage(1L)
                        .correctAnswers(bestGameSet.getFirstStageRecord())
                        .build(),
                ResultResponseDTO.builder()
                        .userId(bestGameSet.getUserId())
                        .theme(bestGameSet.getTheme())
                        .stage(2L)
                        .correctAnswers(bestGameSet.getSecondStageRecord())
                        .build(),
                ResultResponseDTO.builder()
                        .userId(bestGameSet.getUserId())
                        .theme(bestGameSet.getTheme())
                        .stage(3L)
                        .correctAnswers(bestGameSet.getThirdStageRecord())
                        .build()
        );
    }
}
