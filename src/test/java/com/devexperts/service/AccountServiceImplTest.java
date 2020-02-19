package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.exception.account.AccountBadRequestException;
import com.devexperts.exception.account.AccountInsufficientBalanceException;
import com.devexperts.exception.account.AccountNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {


    @InjectMocks
    private AccountServiceImpl service;

    private Account account1;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        account1 = new Account(AccountKey.valueOf(1), "Joao", "Silva", 2000D);
        service.createAccount(account1);

        account2 = new Account(AccountKey.valueOf(2), "Rafael", "Oliveira", 3400D);
        service.createAccount(account2);
    }


    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowAccountNotFoundExceptionWhenAccountTransfer() {
        service.transfer(-1, 2, 200D);
    }


    @Test(expected = AccountBadRequestException.class)
    public void shouldThrowAccountBadRequestExceptionWhenAccountTransfer() {
        service.transfer(1, 2, -200D);
    }

    @Test(expected = AccountInsufficientBalanceException.class)
    public void shouldThrowAccountInsufficientBalanceExceptionWhenAccountTransfer() {
        service.transfer(1, 2, 200000000000D);
    }


    public void shouldAccountTransferVerifyNewbalance() {
        service.transfer(1, 2, 100D);
        assertEquals(account1.getBalance(), 1900D, 0D);
        assertEquals(account2.getBalance(), 3500D, 0D);

    }


}