package com.emotionalcart.hellosearchapi.infra;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        // Handle exception
        e.printStackTrace();
    }

}
