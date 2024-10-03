package com.velas.vivene.inventory.manager.commons.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super("Dado não encontrado no banco de dados: " + message);
    }
}
