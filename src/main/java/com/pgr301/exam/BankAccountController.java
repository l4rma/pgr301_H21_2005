package com.pgr301.exam;

import com.pgr301.exam.model.Account;
import com.pgr301.exam.model.Transaction;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.ofNullable;

@RestController
public class
BankAccountController implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private BankingCoreSystmeService bankService;

    @Autowired
    private MeterRegistry meterRegistry;


    @Timed("transfer_money")
    @PostMapping(path = "/account/{fromAccount}/transfer/{toAccount}", consumes = "application/json", produces = "application/json")
    public void transfer(@RequestBody Transaction tx, @PathVariable String fromAccount, @PathVariable String toAccount) {
        meterRegistry.counter("transfer_money_before_backend", "amount", String.valueOf(tx.getAmount()) ).increment();
        bankService.transfer(tx, fromAccount, toAccount);
        meterRegistry.counter("transfer_money_after_backend", "amount", String.valueOf(tx.getAmount()) ).increment();
    }

    @Timed("update_account")
    @PostMapping(path = "/account", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> updateAccount(@RequestBody Account a) {
        meterRegistry.counter("update_account_before_backend", "id", "balance", a.getId(), a.getBalance().toString() ).increment();
        bankService.updateAccount(a);
        meterRegistry.counter("update_account_after_backend", "id", "balance", a.getId(), a.getBalance().toString() ).increment();
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @Timed("get_account")
    @GetMapping(path = "/account/{accountId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> balance(@PathVariable String accountId) {
        meterRegistry.counter("get_account_before_backend", "id", accountId ).increment();
        Account account = ofNullable(bankService.getAccount(accountId)).orElseThrow(AccountNotFoundException::new);
        meterRegistry.counter("get_account_after_backend", "id", accountId ).increment();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "video not found")
    public static class AccountNotFoundException extends RuntimeException {
    }
}

