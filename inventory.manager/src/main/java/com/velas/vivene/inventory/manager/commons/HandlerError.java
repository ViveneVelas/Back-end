package com.velas.vivene.inventory.manager.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomAccessDeniedException;
import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.InvalidArgumentException;
import com.velas.vivene.inventory.manager.commons.exceptions.MalformedRequestException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.ValidationException;

@ControllerAdvice
public class HandlerError {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<String> handleNoContent(NoContentException ex) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedRequestException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(MalformedRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(InvalidArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(CustomAccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomDataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(CustomDataIntegrityViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //@ExceptionHandler(UnexpectedServerErrorException.class)
    //public ResponseEntity<String> handleUnexpectedServerError(UnexpectedServerErrorException ex) {
      //  return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //}

}
