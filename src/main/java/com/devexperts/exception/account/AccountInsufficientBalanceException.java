package com.devexperts.exception.account;

import com.devexperts.exception.RestResponseException;
import org.springframework.http.HttpStatus;

import static com.devexperts.exception.account.AccountExceptionError.INSUFFICIENT_BALANCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class AccountInsufficientBalanceException extends RestResponseException {

    public AccountInsufficientBalanceException() {
        super(INSUFFICIENT_BALANCE.getMessage());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getErrorCode() {
        return INSUFFICIENT_BALANCE.code();
    }

}