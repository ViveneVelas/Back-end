package com.velas.vivene.inventory.manager.commons.exceptions;

public class CustomDataIntegrityViolationException extends RuntimeException {
    public CustomDataIntegrityViolationException(String message) {
        super("Violação de integridade de dados: " + message);
    }
}