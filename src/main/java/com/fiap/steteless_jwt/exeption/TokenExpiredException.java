package com.fiap.steteless_jwt.exeption;

public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException(String message) {
        super(message);
    }
}
