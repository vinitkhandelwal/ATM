package com.ameriprise.atm.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class WithdrawRequest {

    @NotNull
    private Long fromAccount;
    @NotNull
    private Long toAccount;
    @NotNull
    @Min(value = 1, message = "Amount can not be negative")
    private Double amount;
}
