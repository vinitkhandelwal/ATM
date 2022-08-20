package com.ameriprise.atm.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AccountDto {

    private Long customerId;
    private String customerName;
    private Long accountNumber;
    private Double balance;
}
