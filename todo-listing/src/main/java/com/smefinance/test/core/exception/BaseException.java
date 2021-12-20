package com.smefinance.test.core.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    //region variables
    protected final int lineNumber;
    //endregion

    //region public
    public BaseException(String message, int lineNumber) {

        super(message);
        this.lineNumber = lineNumber;
    }
    //endregion
}
