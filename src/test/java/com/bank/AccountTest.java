package com.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;
    private TestDateProvider testDateProvider;

    @BeforeEach
    void setUp() {
        LocalDate initialDate = LocalDate.of(2012, 1, 1);
        testDateProvider = new TestDateProvider(initialDate);
        account = new Account(testDateProvider); 
    }

    // --- Acceptance Test Scenario ---

    @Test
    void acceptanceTestScenarioPrintsCorrectStatement() {
        LocalDate date10 = LocalDate.of(2012, 1, 10);
        LocalDate date13 = LocalDate.of(2012, 1, 13);
        LocalDate date14 = LocalDate.of(2012, 1, 14);

        // 1. Deposit 1000 on 10-01-2012
        testDateProvider.setDate(date10);
        account.deposit(1000); 

        // 2. Deposit 2000 on 13-01-2012
        testDateProvider.setDate(date13);
        account.deposit(2000); 

        // 3. Withdrawal 500 on 14-01-2012
        testDateProvider.setDate(date14);
        account.withdraw(500); 

        assertEquals(2500, account.getCurrentBalance());

        // Capture System.out for printStatement()
        ByteArrayOutputStream statementOutput = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(statementOutput));
        
        // Act
        account.printStatement();

        // Assert
        System.setOut(originalOut); // Restore standard output

        String expectedStatement = 
                "Date       || Amount || Balance" + System.lineSeparator() +
                "-----------||--------||--------" + System.lineSeparator() +
                "14/01/2012 ||  -500 ||  2500" + System.lineSeparator() +
                "13/01/2012 ||  2000 ||  3000" + System.lineSeparator() +
                "10/01/2012 ||  1000 ||  1000" + System.lineSeparator();
        
        assertEquals(expectedStatement, statementOutput.toString());
    }


    @Test
    void withdrawalShouldThrowInsufficientFundsException() {
        account.deposit(500);
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(501);
        });
        assertEquals(500, account.getCurrentBalance()); 
    }
    
   
    // Example: Test depositing negative amount
    @Test
    void depositShouldThrowExceptionForNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100);
        });
        assertEquals(0, account.getCurrentBalance());
    }

    // Example: Test withdrawing negative amount
    @Test
    void withdrawShouldThrowExceptionForNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-100);
        });
        assertEquals(0, account.getCurrentBalance());
    }
}