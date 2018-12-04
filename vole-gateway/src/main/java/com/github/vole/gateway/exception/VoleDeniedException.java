package com.github.vole.gateway.exception;

/**
 * 403 授权拒绝
 */
public class VoleDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VoleDeniedException() {
    }

    public VoleDeniedException(String message) {
        super(message);
    }

    public VoleDeniedException(Throwable cause) {
        super(cause);
    }

    public VoleDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoleDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
