package com.ameriprise.atm.exception;

import com.ameriprise.atm.controller.ErrorMessage;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private ErrorMessage errorMessage;

    public ApplicationException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
