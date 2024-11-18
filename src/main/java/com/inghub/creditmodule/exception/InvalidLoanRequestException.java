package com.inghub.creditmodule.exception;

public class InvalidLoanRequestException extends RuntimeException {
    public InvalidLoanRequestException(String message) {
        super(message);
    }
}