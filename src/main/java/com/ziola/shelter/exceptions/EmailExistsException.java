package com.ziola.shelter.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String s) {
        super(s);
    }
}
