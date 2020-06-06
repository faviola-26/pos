package com.mycompany.catalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidEntityException extends RuntimeException {
    
    public InvalidEntityException(String message){
        super(message);
    }
}
