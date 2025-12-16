package com.bank;

import java.time.LocalDate;

public class MainApp {

    public static void main(String[] args) {
        
      
        LocalDate date10 = LocalDate.of(2012, 1, 10);
        LocalDate date13 = LocalDate.of(2012, 1, 13);
        LocalDate date14 = LocalDate.of(2012, 1, 14);
        
        
        TestDateProvider dateController = new TestDateProvider(date10);
        Account account = new Account(dateController);

      
        dateController.setDate(date10);
        account.deposit(1000);

        dateController.setDate(date13);
        account.deposit(2000);

        dateController.setDate(date14);
        account.withdraw(500);

      
        System.out.println("--- Final Bank Statement ---");
        account.printStatement();
        System.out.println("----------------------------");
    }
}