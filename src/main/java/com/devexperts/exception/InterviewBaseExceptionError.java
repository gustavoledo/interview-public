package com.devexperts.exception;

public enum InterviewBaseExceptionError {

    ACCOUNT_ERROR_CODE("ACCOUNT");

    public static final String DELIMITER = "_";
    String code;

    InterviewBaseExceptionError(String code) {
        this.code = code;
    }

    public String code() {
        return code + DELIMITER;
    }

}