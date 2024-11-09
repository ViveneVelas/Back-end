package com.velas.vivene.inventory.manager.commons.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message) {
        super("Argumento inválido: " + message);
    }
}
