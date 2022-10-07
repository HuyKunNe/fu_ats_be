package com.fu.fuatsbe.exceptions;

public class UsernameOrPasswordNotFoundException extends RuntimeException{
    public UsernameOrPasswordNotFoundException(String message) {
        super(message);
    }
}
