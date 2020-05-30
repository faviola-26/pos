package com.mycompany.catalog.exceptions;


public class InvalidEntityException extends RuntimeException {
    
    public InvalidEntityException(String message){
        super(message);
    }
}
