package com.ruoyi.weibank.exception;

public class ReportHandleException extends Exception{
    public ReportHandleException() {
        super();
    }

    public ReportHandleException(String message) {
        super(message);
    }

    public ReportHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportHandleException(Throwable cause) {
        super(cause);
    }

    protected ReportHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
