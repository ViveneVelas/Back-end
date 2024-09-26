package com.velas.vivene.inventory.manager.commons.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMensagesResponse {
    private HttpStatus httpStatus;
    private String mesages;

    public ErrorMensagesResponse(HttpStatus httpStatus, String mesages) {
        this.httpStatus = httpStatus;
        this.mesages = mesages;
    }
}
