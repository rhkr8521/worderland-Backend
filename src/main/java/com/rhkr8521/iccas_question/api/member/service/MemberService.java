package com.rhkr8521.iccas_question.api.member.service;

import com.rhkr8521.iccas_question.api.member.domain.Member;
import com.rhkr8521.iccas_question.api.member.repository.MemberRepository;
import com.rhkr8521.iccas_question.api.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
}
