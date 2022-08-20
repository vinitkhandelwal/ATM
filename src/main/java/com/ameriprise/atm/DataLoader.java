package com.ameriprise.atm;

import com.ameriprise.atm.dao.AccountRepository;
import com.ameriprise.atm.dao.CardRepository;
import com.ameriprise.atm.dao.CustomerRepository;
import com.ameriprise.atm.model.Account;
import com.ameriprise.atm.model.Card;
import com.ameriprise.atm.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Override
    public void run(String... args) throws Exception {

        Customer customer = new Customer();
        customer.setFirstName("Vin");
        Account account1 = new Account();
        account1.setAccountNumber(100L);
        account1.setBalance(1000.0);
        account1.setCustomer(customer);

        Card card1 = new Card();
        card1.setCardNumber("10101010");
        card1.setCvv("123");
        account1.setCard(card1);

        customerRepository.save(customer);
        account1.setCustomer(customer);
        accountRepository.save(account1);
        cardRepository.save(card1);


        Customer customer2 = new Customer();
        customer2.setFirstName("Nik");
        Account account2 = new Account();
        account2.setAccountNumber(200L);
        account2.setBalance(1000.0);
        account2.setCustomer(customer);

        Card card2 = new Card();
        card2.setCardNumber("20202020");
        card2.setCvv("123");
        account2.setCard(card2);

        customerRepository.save(customer2);
        account2.setCustomer(customer2);
        accountRepository.save(account2);
        cardRepository.save(card2);

    }
}
