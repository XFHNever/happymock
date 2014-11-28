package com.ebay.happymock.core.compiling.v1.exception;

import java.text.MessageFormat;

/**
 * User: jicui
 * Date: 14-8-21
 */
public class CompileException extends Exception {

    private CompileErrorCode errorCode;
    private String errorMsg;

    public CompileException(CompileErrorCode errorCode,Throwable cause,String... args) {
        super(cause);
        this.errorCode=errorCode;
        errorMsg=MessageFormat.format(errorCode.getMessage(),args);
    }

    public CompileException(CompileErrorCode errorCode,String... args) {
        super();
        this.errorCode=errorCode;
        errorMsg=MessageFormat.format(errorCode.getMessage(),args);
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    public CompileErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(CompileErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
