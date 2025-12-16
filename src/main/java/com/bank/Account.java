
package com.bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements AccountService {

    private final DateProvider dateProvider; 
    private int currentBalance = 0;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }
    @Override
    public void deposit(int amount) {
       
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        currentBalance += amount;
        LocalDate transactionDate = dateProvider.now();
        transactions.add(new Transaction(transactionDate, amount, currentBalance));
    }

    @Override
    public void withdraw(int amount) {
        // Validation
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > currentBalance) {
            throw new InsufficientFundsException(
                String.format("Withdrawal of %d failed. Current balance is %d.", amount, currentBalance));
        }
        currentBalance -= amount;
        LocalDate transactionDate = dateProvider.now();
        transactions.add(new Transaction(transactionDate, -amount, currentBalance));
    }

    // --- Reporting ---

    @Override
    public void printStatement() {
        System.out.println("Date       || Amount || Balance");
        System.out.println("-----------||--------||--------");

        List<Transaction> reversedTransactions = new ArrayList<>(transactions);
        Collections.reverse(reversedTransactions);

        for (Transaction transaction : reversedTransactions) {
            System.out.println(transaction.toString());
        }
    }
    
    
    public int getCurrentBalance() {
        return currentBalance;
    }
}