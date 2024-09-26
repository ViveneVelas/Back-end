package com.velas.vivene.inventory.manager.commons;

import com.velas.vivene.inventory.manager.commons.exceptions.ErrorMensagesResponse;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ErrorMensagesResponse errorResponse(ResourceNotFoundException ex) {
        return new ErrorMensagesResponse(HttpStatus.NO_CONTENT, ex.getMessage());
    }

    @ExceptionHandler(value = InternalError.class)
    public ErrorMensagesResponse erroInternal(InternalError ex) {
        return new ErrorMensagesResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ErrorMensagesResponse errorRequest(BadRequestException ex) {
        return new ErrorMensagesResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

}

