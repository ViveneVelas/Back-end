package com.velas.vivene.inventory.manager.commons.exceptions;

public class MalformedRequestException extends RuntimeException {
    public MalformedRequestException(String message) {
        super("O corpo da requisição está mal formado: " + message);
    }
}
