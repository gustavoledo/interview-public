package com.devexperts.exception;


public enum BaseMessagesError implements BaseExceptionError {
    UNKNOWN("UNKNOWN_101", "Unknown error", null),
    MISSING_PARAMETER("BAD_REQUEST_101", "Missing request parameter: {0}", "{0}"),
    INVALID_ARGUMENT("INVALID_ARGUMENT_101", "Invalid argument: {0}", "{0}");

    String code;
    String message;
    String detail;

    BaseMessagesError(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String code() {
        return this.code;
    }
}
