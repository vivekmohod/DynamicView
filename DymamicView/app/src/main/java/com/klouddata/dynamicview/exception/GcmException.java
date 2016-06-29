package com.klouddata.dynamicview.exception;

public class GcmException extends Exception {
    private static final long serialVersionUID = 1L;
    public GcmException(String errorMessage) {
        super(errorMessage);
    }
    public GcmException(Throwable throwable) {
        super(throwable);
    }
}
