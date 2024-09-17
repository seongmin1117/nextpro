package com.devsm.nextpro.auth.exception;

public class AuthException extends RuntimeException{

    public AuthException(final String message) {
        super(message);
    }

    public static class DuplicateEmailException extends AuthException {

        public DuplicateEmailException() {
            super("중복되는 이메일이 존재합니다.");
        }
    }

}
