package com.ameriprise.atm.controller;

import com.ameriprise.atm.exception.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { ApplicationException.class})
    protected ResponseEntity<Object> handleConflict(
            ApplicationException ex, WebRequest request) {
        ErrorMessage errorMessage = ex.getErrorMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorMessage errorMessageObj = new ErrorMessage();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessageObj.setCode("INVALID_INPUT");
            errorMessageObj.setMessage(errorMessage);

        });

            return ResponseEntity.badRequest().body(errorMessageObj);
    }


}
