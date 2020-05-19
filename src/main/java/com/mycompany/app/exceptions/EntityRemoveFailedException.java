package com.mycompany.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EntityRemoveFailedException extends Exception{
    public EntityRemoveFailedException(String message){
        super(message);
    }
}
