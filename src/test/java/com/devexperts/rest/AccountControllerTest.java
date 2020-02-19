package com.devexperts.rest;

import com.devexperts.exception.account.AccountInsufficientBalanceException;
import com.devexperts.exception.account.AccountNotFoundException;
import com.devexperts.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountService service;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void transferOk() throws Exception {
        doNothing().when(service).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/api/operations/transfer")
                .param("sourceId", "1")
                .param("targetId", "2")
                .param("amount", "110D")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void transferBadRequest() throws Exception {
        doNothing().when(service).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/api/operations/transfer")
                .param("sourceId", "1")
                .param("targetId", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void transferAccountNotFound() throws Exception {
        doThrow(new AccountNotFoundException()).when(service).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/api/operations/transfer")
                .param("sourceId", "1")
                .param("targetId", "5")
                .param("amount", "110D")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void transferInsufficientAccountBalance() throws Exception {
        doThrow(new AccountInsufficientBalanceException()).when(service).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/api/operations/transfer")
                .param("sourceId", "1")
                .param("targetId", "5")
                .param("amount", "110D")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}