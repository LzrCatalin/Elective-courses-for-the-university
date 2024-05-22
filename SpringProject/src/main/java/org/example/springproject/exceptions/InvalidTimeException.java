package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidTimeException extends RuntimeException{
    private HttpStatus httpStatus;

    public InvalidTimeException(){}
    public InvalidTimeException(String message,HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
    public InvalidTimeException(String s){
        super(s);
    }
    public HttpStatusCode getHttpStatus() {
        return httpStatus;
    }

}
