package com.ameriprise.atm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@Entity
public class Card {
    @Id
    @Column(name="id")
    private String cardNumber;
    private String cvv;

    @OneToOne(mappedBy = "card")
    private Account account;
}
