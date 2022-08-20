package com.ameriprise.atm.dao;

import com.ameriprise.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
