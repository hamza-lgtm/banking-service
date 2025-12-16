I understand. Here is the complete, professional documentation for your Banking Service project, presented in the final Markdown format.

-----

```markdown
# 🏦 Banking Service Core API

## Project Summary

This project implements the core transaction functions of a simplified banking system, adhering to the `AccountService` interface contract. It allows for deposits, withdrawals, and printing a chronological statement of all account activities.

---

## 🏛️ Architecture and Design

### 1. Structure and Principles

The solution is built using standard Java/Maven conventions and emphasizes **Dependency Injection (DI)** and **Separation of Concerns (SoC)** for high testability. 

| Component | Role | Design Principle |
| :--- | :--- | :--- |
| **`AccountService`** | Defines the public contract (Interface). | Open-Closed Principle (OCP) |
| **`Account`** | Core business logic and state management. | Single Responsibility Principle (SRP) |
| **`DateProvider`** | Interface to abstract system time. | Dependency Inversion Principle (DIP) |
| **`Transaction`** | Immutable record of a single transaction line. | Value Object |
| **`InsufficientFundsException`** | Custom exception for domain-specific errors. | Robust Exception Handling |

### 2. Codebase Structure

The project uses the standard Maven directory layout. 

```

banking-service/
├── src/
│   ├── main/
│   │   └── java/com/bank/  \<-- Production Code
│   │       ├── Account.java
│   │       ├── AccountService.java
│   │       ├── DateProvider.java
│   │       ├── TestDateProvider.java  \<-- Used by MainApp for deterministic demos
│   │       └── Transaction.java
│   └── test/
│       └── java/com/bank/  \<-- Test Code
│           └── AccountTest.java
└── pom.xml

````

---

## 🛠️ Getting Started

### Prerequisites

* **Java Development Kit (JDK):** Version 17 or higher .
* **Apache Maven:** Installed and configured in your system path.

### Build and Test

Navigate to the project root directory (where `pom.xml` is located) and use Maven to manage the project lifecycle.

| Command | Action | Description |
| :--- | :--- | :--- |
| `mvn clean install` | **FULL BUILD & TEST** | Compiles all code, runs all unit tests, and packages the application (`.jar`). This must pass before running the application. |
| `mvn test` | **RUN TESTS ONLY** | Executes `AccountTest.java` to verify all business rules and the acceptance test scenario. |

### Run the Demonstration

The `MainApp` class runs the exact acceptance test scenario required by the technical specification (deposits on 10th and 13th, withdrawal on 14th).

```bash
mvn exec:java -Dexec.mainClass="com.bank.MainApp"
````

#### Expected Output

```
--- Final Bank Statement ---
Date       || Amount || Balance
-----------||--------||--------
14/01/2012 ||  -500 ||  2500
13/01/2012 ||  2000 ||  3000
10/01/2012 ||  1000 ||  1000
----------------------------
```

-----

## 🧩 Public API (`AccountService`)

The `Account` class implements this interface and is initialized using a `DateProvider` dependency.

### Initialization Example

```java
// Production Initialization (uses current system date)
DateProvider productionDate = new SystemDateProvider();
Account liveAccount = new Account(productionDate);

// Test Initialization (fixes the date for predictable testing)
DateProvider testDate = new TestDateProvider(LocalDate.of(2025, 1, 1));
Account testAccount = new Account(testDate);
```

### Method Specifications

| Method | Description | Throws |
| :--- | :--- | :--- |
| `void deposit(int amount)` | Adds `amount` to the current balance. | `IllegalArgumentException` (if `amount <= 0`) |
| `void withdraw(int amount)` | Deducts `amount` from the balance. | `IllegalArgumentException` (if `amount <= 0`)<br>`InsufficientFundsException` (if balance is too low) |
| `void printStatement()` | Prints all transactions to the console, ordered **newest to oldest**. | None |

-----

## 🧪 Testing Strategy

### Deterministic Testing

The `AccountTest.java` uses the **`TestDateProvider`** to control the system date during the test lifecycle. This ensures that time-sensitive logic (like the acceptance test scenario) is deterministic and repeatable.

### Verification

The `AccountTest` class implements a clear verification strategy. The tests cover core business rules and the acceptance scenario in a deterministic way. Key points:

- Acceptance scenario:
  1. Deposit 1000 on 2012-01-10
  2. Deposit 2000 on 2012-01-13
  3. Withdraw 500 on 2012-01-14
  4. Assert `getCurrentBalance()` equals `2500`.
  5. Capture `System.out` to a `ByteArrayOutputStream`, invoke `printStatement()`, then compare the captured string to the expected multiline statement.

- Error and edge cases:
  - `deposit(-100)` expected to throw `IllegalArgumentException` and leave the balance unchanged.
  - `withdraw(-100)` expected to throw `IllegalArgumentException` and leave the balance unchanged.
  - With a balance of 500, attempting `withdraw(501)` is expected to throw `InsufficientFundsException` and leave the balance at 500.

- Exact expected statement (the test asserts this exact string):

```java
String expectedStatement =
    "Date       || Amount || Balance" + System.lineSeparator() +
    "-----------||--------||--------" + System.lineSeparator() +
    "14/01/2012 ||  -500 ||  2500" + System.lineSeparator() +
    "13/01/2012 ||  2000 ||  3000" + System.lineSeparator() +
    "10/01/2012 ||  1000 ||  1000" + System.lineSeparator();
```

This verifies both formatting (date pattern `dd/MM/yyyy`, column alignment) and transaction ordering (newest first).

<!-- end list -->