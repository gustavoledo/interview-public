package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.exception.account.AccountBadRequestException;
import com.devexperts.exception.account.AccountInsufficientBalanceException;
import com.devexperts.exception.account.AccountNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountServiceImpl implements AccountService {

    private Map<Long, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public void clear() {
        accounts.clear();
    }

    @Override
    public void createAccount(Account account) {
        accounts.put(account.getAccountKey().getAccountId(), account);
    }

    @Override
    public Account getAccount(long id) {
        if (accounts.containsKey(id)) {
            return accounts.get(id);
        }
        throw new AccountNotFoundException();

    }

    @Override
    public void transfer(Account source, Account target, double amount) {
        validateTransferValues(source, amount);

        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
    }

    @Async
    public void transfer(long sourceId, long targetId, double amount) {
        transfer(getAccount(sourceId), getAccount(targetId), amount);
    }

    private void validateTransferValues(Account source, double amount) {
        if (amount < 0) {
            throw new AccountBadRequestException();
        }
        if (source.getBalance() < amount) {
            throw new AccountInsufficientBalanceException();
        }
    }
}
