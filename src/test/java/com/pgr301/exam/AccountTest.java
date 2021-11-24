package com.pgr301.exam;

import com.pgr301.exam.model.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    @Test
    void shouldSetBalance() {
        BigDecimal balance = BigDecimal.valueOf(50);

        Account a = new Account();
        a.setBalance(balance);

        assertEquals(a.getBalance(), balance + 1);
    }
}
