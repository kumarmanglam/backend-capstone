package com.capstone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DuplicateException extends RuntimeException{
    public DuplicateException(String message){
        super(message);
        System.out.println(message);
    }
}