package com.ameriprise.atm.controller;

import com.ameriprise.atm.dao.AccountRepository;
import com.ameriprise.atm.dto.AccountDto;
import com.ameriprise.atm.dto.request.DepositRequest;
import com.ameriprise.atm.dto.request.WithdrawRequest;
import com.ameriprise.atm.model.Account;
import com.ameriprise.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity accounDetail(@PathVariable(value = "id") long id) {
        Optional<AccountDto> account = accountService.getAccountDetail(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("INVALID_ACCOUNT", "Account number doesn't exist"));
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity depositCash(@PathVariable(value = "id") long id, @Valid @RequestBody DepositRequest depositRequest) {
        Optional<AccountDto> account = accountService.deposit(depositRequest);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("INVALID_ACCOUNT", "Account number doesn't exist"));
    }

    @PostMapping("/withdraw/{id}")
    public ResponseEntity withdrawCash(@PathVariable(value = "id") long id,@Valid @RequestBody WithdrawRequest withdrawRequest) {
        String message = accountService.withdraw(withdrawRequest);
        return ResponseEntity.ok(message);

    }
}
