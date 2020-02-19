package com.devexperts.exception.account;

import com.devexperts.exception.BaseExceptionError;

import static com.devexperts.exception.InterviewBaseExceptionError.ACCOUNT_ERROR_CODE;

public enum AccountExceptionError implements BaseExceptionError {

    ACCOUNT_NOT_FOUND(ACCOUNT_ERROR_CODE.code() + 101, "Account not found", null),
    ACCOUNT_BAD_REQUEST(ACCOUNT_ERROR_CODE.code() + 102, "One of the parameters in not present " +
            "or amount is invalid", null),
    INSUFFICIENT_BALANCE(ACCOUNT_ERROR_CODE.code() + 103, "insufficient account balance", null);


    String code;
    String message;
    String detail;

    AccountExceptionError(String code, String message, String detail) {
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
