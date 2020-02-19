package com.devexperts.exception;

import java.text.MessageFormat;

public interface BaseExceptionError {

    default String message() {
        return MessageFormat.format(getMessage(), null);
    }

    default String message(String detail) {
        return MessageFormat.format(getMessage(), detail);
    }

    default String message(Long id) {
        return MessageFormat.format(getMessage(), id);
    }

    String getMessage();

    String code();

}
