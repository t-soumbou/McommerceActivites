package com.mexpedition.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpeditionIntrouvableException extends RuntimeException {

    public ExpeditionIntrouvableException(String s) {
        super(s);
    }
}
