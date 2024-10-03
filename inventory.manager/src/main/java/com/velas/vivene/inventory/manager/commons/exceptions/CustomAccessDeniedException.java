package com.velas.vivene.inventory.manager.commons.exceptions;

public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException(String message) {
        super("Acesso negado: " + message);
    }
}
