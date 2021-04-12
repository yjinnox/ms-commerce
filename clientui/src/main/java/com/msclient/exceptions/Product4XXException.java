package com.msclient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class Product4XXException extends RuntimeException {

    public Product4XXException(String message) {
        super(message);
    }
}
