package org.example.exceptions;

public class RegisterUserException extends RuntimeException{
    public RegisterUserException(String message) {
        this(message, (Throwable)null);
    }

    public RegisterUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
