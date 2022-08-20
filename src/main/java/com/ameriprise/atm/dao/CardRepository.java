package com.ameriprise.atm.dao;

import com.ameriprise.atm.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,String> {
}
