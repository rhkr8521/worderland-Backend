package com.rhkr8521.iccas_question.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerError extends BaseException{
    public InternalServerError() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
