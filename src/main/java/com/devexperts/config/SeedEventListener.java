package com.devexperts.config;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class SeedEventListener implements ApplicationListener<ServletWebServerInitializedEvent> {

    @Autowired
    AccountServiceImpl accountService;

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        seedAccounts();
    }


    private void seedAccounts() {

        Account account1 = new Account(AccountKey.valueOf(1), "Joao", "Silva", 2000D);
        accountService.createAccount(account1);

        Account account2 = new Account(AccountKey.valueOf(2), "Rafael", "Oliveira", 3400D);
        accountService.createAccount(account2);
    }


}

