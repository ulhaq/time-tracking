package com.timetrack.mvp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExists extends RuntimeException {
    private static final long serialVersionUID = 2318572506517526285L;

    public UserAlreadyExists(String msg) {
        super(msg);
    }
}
