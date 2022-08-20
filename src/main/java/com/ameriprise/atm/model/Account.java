package com.ameriprise.atm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Account {

    @Id
    @Column(name = "id")
    private Long accountNumber;
    private Double balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "account_card",
            joinColumns =
                    { @JoinColumn(name = "account_number", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "card_number", referencedColumnName = "id") })
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutorial_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;
}
