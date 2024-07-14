package com.rhkr8521.iccas_question.api.member.controller;

import com.rhkr8521.iccas_question.api.member.dto.MemberResponseDTO;
import com.rhkr8521.iccas_question.api.member.service.MemberService;
import com.rhkr8521.iccas_question.common.response.ApiResponse;
import com.rhkr8521.iccas_question.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/member_check/{userId}")
    public ResponseEntity<?> checkFirstTimeUser(@PathVariable String userId, @RequestParam("theme") String theme) {
        MemberResponseDTO response = memberService.checkFirstTimeUser(userId);
        memberService.checkCorrectResult(userId, theme);

        if (response.isResult()) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .status(SuccessStatus.FIRST_USER.getStatusCode())
                    .success(true)
                    .message(SuccessStatus.FIRST_USER.getMessage())
                    .data(response)
                    .build());
        } else {
            return ResponseEntity.ok(ApiResponse.builder()
                    .status(SuccessStatus.EXIST_USER.getStatusCode())
                    .success(true)
                    .message(SuccessStatus.EXIST_USER.getMessage())
                    .data(response)
                    .build());
        }
    }
}
