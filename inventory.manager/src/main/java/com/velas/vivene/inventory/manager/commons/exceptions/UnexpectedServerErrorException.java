package com.velas.vivene.inventory.manager.commons.exceptions;

public class UnexpectedServerErrorException extends RuntimeException {
    public UnexpectedServerErrorException(String message) {
        super("Um erro inesperado ocorreu no servidor: " + message);
    }
}
