package com.cm.exception;

/**
 * Created by Çelebi Murat on 18/11/15.
 */
public class MultipleKeyspaceException extends MermaidCoreException {

    public MultipleKeyspaceException() {
        super();
    }

    public MultipleKeyspaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MultipleKeyspaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleKeyspaceException(String message) {
        super(message);
    }

    public MultipleKeyspaceException(Throwable cause) {
        super(cause);
    }

}
