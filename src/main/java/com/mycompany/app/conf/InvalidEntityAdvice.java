package com.mycompany.app.conf;

import com.mycompany.app.exceptions.InvalidEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidEntityAdvice {
    
    @ResponseBody
    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public String InvalidEntityHandler(InvalidEntityException ex){
        return ex.getMessage();
    }
    
}
