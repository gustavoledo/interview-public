package com.devexperts.exception.account;

import com.devexperts.exception.RestResponseException;
import org.springframework.http.HttpStatus;

import static com.devexperts.exception.account.AccountExceptionError.ACCOUNT_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AccountBadRequestException extends RestResponseException {

    public AccountBadRequestException() {
        super(ACCOUNT_BAD_REQUEST.getMessage());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return ACCOUNT_BAD_REQUEST.code();
    }

}