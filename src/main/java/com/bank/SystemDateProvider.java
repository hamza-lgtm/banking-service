package com.bank;


import java.time.LocalDate;

/**
 * Provides the actual system date (used in production).
 */
public class SystemDateProvider implements DateProvider {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}