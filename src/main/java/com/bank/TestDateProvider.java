package com.bank;


import java.time.LocalDate;

/**
 * A mock date provider used in tests to fix or set a specific date.
 */
public class TestDateProvider implements DateProvider {
    private LocalDate fixedDate;

    public TestDateProvider(LocalDate initialDate) {
        this.fixedDate = initialDate;
    }
    
    public void setDate(LocalDate newDate) {
        this.fixedDate = newDate;
    }

    @Override
    public LocalDate now() {
        return this.fixedDate;
    }
}