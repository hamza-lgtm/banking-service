package com.bank;


/**
 * Custom exception for domain-specific business errors.
 */
class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}