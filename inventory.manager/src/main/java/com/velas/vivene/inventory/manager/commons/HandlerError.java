package com.velas.vivene.inventory.manager.commons;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HandlerError {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> notFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>("Ocorreu um erro interno no servidor.", HttpStatus.NOT_FOUND);
    }

}
