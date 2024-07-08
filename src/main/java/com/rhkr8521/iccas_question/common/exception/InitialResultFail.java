package com.rhkr8521.iccas_question.common.exception;

import org.springframework.http.HttpStatus;

public class InitialResultFail extends BaseException{
    public InitialResultFail() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InitialResultFail(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
