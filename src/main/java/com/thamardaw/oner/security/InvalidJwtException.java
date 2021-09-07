package com.thamardaw.oner.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {
    public InvalidJwtException(String msg) {
        super(msg);
    }
}
