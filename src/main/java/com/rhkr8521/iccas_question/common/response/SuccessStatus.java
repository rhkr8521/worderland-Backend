package com.rhkr8521.iccas_question.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {

    /**
     * 200
     */
    SEND_QUESTION_SUCCESS(HttpStatus.OK, "문제 발송 성공"),
    SEND_QUESTION_ANSWER_YES(HttpStatus.OK, "정답 입니다"),
    SEND_QUESTION_ANSWER_NO(HttpStatus.OK, "오답 입니다"),
    SEND_RESULT(HttpStatus.OK, "결과 발송 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}