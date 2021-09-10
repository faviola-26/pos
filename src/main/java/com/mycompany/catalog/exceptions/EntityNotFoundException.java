package com.mycompany.catalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message){
        super(message);
    }
}
