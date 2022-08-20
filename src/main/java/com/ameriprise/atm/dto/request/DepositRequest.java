package com.ameriprise.atm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DepositRequest {
    @NotNull
    private Long accountId;
    @Valid
    @NotNull
    @Min(value = 1, message = "Amount can not be negative")
    private Double amount;
}
