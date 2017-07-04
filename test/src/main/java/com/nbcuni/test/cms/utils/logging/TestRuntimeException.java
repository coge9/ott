package com.nbcuni.test.cms.utils.logging;

public class TestRuntimeException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TestRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TestRuntimeException(final Throwable cause) {
        super(cause);
    }

    public TestRuntimeException(final String message) {
        super(message);
    }
}