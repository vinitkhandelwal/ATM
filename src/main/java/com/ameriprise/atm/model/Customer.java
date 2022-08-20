package com.ameriprise.atm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String firstName;
//    private String lastName;
//    private String address;
//    private String phoneNumber;
    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL )
    private List<Account> accounts;




}
