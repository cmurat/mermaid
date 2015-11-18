package com.cm.exception;

/**
 * Created by Çelebi Murat on 18/11/15.
 */
public class MermaidCoreException extends Exception {
    public MermaidCoreException() {
        super();
    }

    public MermaidCoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MermaidCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public MermaidCoreException(String message) {
        super(message);
    }

    public MermaidCoreException(Throwable cause) {
        super(cause);
    }
}