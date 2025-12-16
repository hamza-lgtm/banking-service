package com.bank;


import java.time.LocalDate;

/**
 * Abstracts the source of time, essential for testability.
 */
public interface DateProvider {
    LocalDate now();
}