package com.mycompany.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidEntityException extends Exception{
    
    public InvalidEntityException(String message){
        super(message);
    }
}
