package com.rhkr8521.iccas_question.api.member.service;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.member.repository.MemberRepository;
import com.rhkr8521.iccas_question.api.member.dto.MemberResponseDTO;
import com.rhkr8521.iccas_question.api.result.domain.GameSet;
import com.rhkr8521.iccas_question.api.result.repository.GameSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final GameSetRepository gameSetRepository;

    @Transactional
    public MemberResponseDTO checkFirstTimeUser(String userId) {
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);

        if (memberOptional.isPresent()) {
            return MemberResponseDTO.builder()
                    .result(false)
                    .build();
        } else {
            Member newMember = Member.builder()
                    .userId(userId)
                    .build();
            memberRepository.save(newMember);
            return MemberResponseDTO.builder()
                    .result(true)
                    .build();
        }
    }

    @Transactional
    public void checkCorrectResult(String userId) {
        List<GameSet> currentGameSetCarousel = gameSetRepository.findByUserIdAndTheme(userId, "carousel");
        List<GameSet> currentGameSetFerrisWheel = gameSetRepository.findByUserIdAndTheme(userId, "ferris_wheel");
        List<GameSet> currentGameSetRollerCoaster = gameSetRepository.findByUserIdAndTheme(userId, "roller_coaster");
        currentGameSetCarousel.forEach(gs -> {
            int totalStagesCount = gs.getFirstStageTotalCount() + gs.getSecondStageTotalCount() + gs.getThirdStageTotalCount();
            if (totalStagesCount < 11) {
                GameSet updatedGameSet = gs.resetStageRecords();
                gameSetRepository.save(updatedGameSet);
            }
        });
        currentGameSetFerrisWheel.forEach(gs -> {
            int totalStagesCount = gs.getFirstStageTotalCount() + gs.getSecondStageTotalCount() + gs.getThirdStageTotalCount();
            if (totalStagesCount < 11) {
                GameSet updatedGameSet = gs.resetStageRecords();
                gameSetRepository.save(updatedGameSet);
            }
        });
        currentGameSetRollerCoaster.forEach(gs -> {
            int totalStagesCount = gs.getFirstStageTotalCount() + gs.getSecondStageTotalCount() + gs.getThirdStageTotalCount();
            if (totalStagesCount < 11) {
                GameSet updatedGameSet = gs.resetStageRecords();
                gameSetRepository.save(updatedGameSet);
            }
        });
    }
}
