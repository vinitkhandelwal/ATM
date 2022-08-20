package com.ameriprise.atm.service;

import com.ameriprise.atm.controller.ErrorMessage;
import com.ameriprise.atm.dao.AccountRepository;
import com.ameriprise.atm.dto.AccountDto;
import com.ameriprise.atm.dto.request.DepositRequest;
import com.ameriprise.atm.dto.request.WithdrawRequest;
import com.ameriprise.atm.exception.ApplicationException;
import com.ameriprise.atm.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<AccountDto> getAccountDetail(Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()) {
            AccountDto accountDto = AccountDto.builder()
                    .accountNumber(account.get().getAccountNumber())
                    .balance(account.get().getBalance())
                    .customerId(account.get().getCustomer().getCustomerId())
                    .customerName(account.get().getCustomer().getFirstName())
                    .build();
            return Optional.of(accountDto);
        }
        return Optional.empty();
    }

    public Optional<AccountDto> deposit(DepositRequest depositRequest){
        Optional<Account> account = accountRepository.findById(depositRequest.getAccountId());
        if(account.isPresent()) {
           account.get().setBalance(account.get().getBalance()+depositRequest.getAmount());
           accountRepository.save(account.get());
            AccountDto accountDto = AccountDto.builder()
                    .accountNumber(account.get().getAccountNumber())
                    .balance(account.get().getBalance()+depositRequest.getAmount())
                    .customerId(account.get().getCustomer().getCustomerId())
                    .customerName(account.get().getCustomer().getFirstName())
                    .build();
            return Optional.of(accountDto);
        }
        return Optional.empty();
    }


    @Transactional
    public String withdraw(WithdrawRequest request){
        Optional<Account> fromAccount = accountRepository.findById(request.getFromAccount());
        Optional<Account> toAccount = accountRepository.findById(request.getToAccount());

        if(!fromAccount.isPresent()){
            throw new ApplicationException(new ErrorMessage("INVALID_ACCOUNT","Invalid Account number " + request.getFromAccount() ));
        }

        if(!toAccount.isPresent()){
            throw new ApplicationException(new ErrorMessage("INVALID_ACCOUNT","Invalid Account number " + request.getToAccount() ));
        }

        if(fromAccount.get().getBalance() < request.getAmount()) {
            throw new ApplicationException(new ErrorMessage("INVALID_AMOUNT","Insufficient Balance  " + fromAccount.get().getBalance() ));
        }

        fromAccount.get().setBalance(fromAccount.get().getBalance()- request.getAmount());
        toAccount.get().setBalance(toAccount.get().getBalance() + request.getAmount());
        return "Success";
    }



}
