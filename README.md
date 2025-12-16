# Banking Service

## 📁 Project Structure

```
banking-service/
├── src/
│   ├── main/
│   │   └── java/com/bank/          <- Production Code
│   │       ├── Account.java
│   │       ├── AccountService.java
│   │       ├── DateProvider.java
│   │       ├── SystemDateProvider.java
│   │       ├── TestDateProvider.java  <- Used by MainApp for deterministic demos
│   │       └── Transaction.java
│   └── test/
│       └── java/com/bank/          <- Test Code
│           └── AccountTest.java
└── pom.xml
```

---

## 🛠️ Getting Started

### Prerequisites

- **Java Development Kit (JDK):** Version 17 or higher
- **Apache Maven:** Installed and configured in your system path

### Build and Test

Navigate to the project root directory (where `pom.xml` is located) and use Maven to manage the project lifecycle.

| Command | Action | Description |
| :--- | :--- | :--- |
| `mvn clean install` | **FULL BUILD & TEST** | Compiles all code, runs all unit tests, and packages the application `.jar`. This must pass before running the application. |
| `mvn test` | **RUN TESTS ONLY** | Executes `AccountTest.java` to verify all business rules and the acceptance test scenario. |

### Run the Demonstration

The `MainApp` class runs the exact acceptance test scenario required by the technical specification (deposits on 10th and 13th, withdrawal on 14th).

```bash
mvn exec:java -Dexec.mainClass="com.bank.MainApp"
```

#### Expected Output

```
--- Final Bank Statement ---
Date || Amount || Balance
-----------||--------||--------
14/01/2012 || -500 || 2500
13/01/2012 || 2000 || 3000
10/01/2012 || 1000 || 1000
----------------------------
```

---

## 🧩 Public API - `AccountService`

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
| `void withdraw(int amount)` | Deducts `amount` from the balance. | `IllegalArgumentException` (if `amount <= 0`)<br/>`InsufficientFundsException` (if balance is too low) |
| `void printStatement()` | Prints all transactions to the console, ordered **newest to oldest**. | None |

---

## 🧪 Testing Strategy

Unit tests verify all business rules and edge cases using the `TestDateProvider` for deterministic behavior.
