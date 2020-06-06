package com.mycompany.catalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class NoSingleResultException extends Exception{
    public NoSingleResultException(String message){
        super(message);
    }
}
