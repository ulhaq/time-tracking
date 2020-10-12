package com.timetrack.mvp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoRecordFoundException extends RuntimeException {
    private static final long serialVersionUID = 1199844169066977958L;

    public NoRecordFoundException(String msg) {
        super(msg);
    }
}
