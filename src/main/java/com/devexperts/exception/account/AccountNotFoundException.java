package com.devexperts.exception.account;

import com.devexperts.exception.RestResponseException;
import org.springframework.http.HttpStatus;

import static com.devexperts.exception.account.AccountExceptionError.ACCOUNT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AccountNotFoundException extends RestResponseException {

    public AccountNotFoundException() {
        super(ACCOUNT_NOT_FOUND.getMessage());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return ACCOUNT_NOT_FOUND.code();
    }

}