package com.bank;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Transaction {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDate date;
    private final int amount; 
    private final int balance; 
    public Transaction(LocalDate date, int amount, int balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }
    @Override
    public String toString() {
        return String.format("%s || %5d || %5d",
                date.format(DATE_FORMATTER),
                amount,
                balance);
    }
}