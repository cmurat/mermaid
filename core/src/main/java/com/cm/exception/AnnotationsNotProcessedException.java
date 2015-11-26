package com.cm.exception;

/**
 * Created by Çelebi Murat on 18/11/15.
 */
public class AnnotationsNotProcessedException extends MermaidCoreException {

    public AnnotationsNotProcessedException() {
        super();
    }

    public AnnotationsNotProcessedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnnotationsNotProcessedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationsNotProcessedException(String message) {
        super(message);
    }

    public AnnotationsNotProcessedException(Throwable cause) {
        super(cause);
    }

}
