# Selenium Java Cucumber Automation Framework

## 📌 Overview
This project is a test automation framework built using Selenium WebDriver, Java, and Cucumber (BDD).

It is designed with a scalable and maintainable structure following real-world automation practices, focusing on stability, reusability, and clear failure analysis.

---

## 🛠️ Tech Stack
- Selenium WebDriver
- Java
- Cucumber (BDD)
- Maven

---

## 📂 Project Structure
src/main/java/

- common → Base classes for reusable functionality  
- data → Environment-based configuration (preprod, uat)  
- feature → Cucumber feature files  
- page → Page Object Model classes (LoginPage, ProductsPage, Cart)  
- region → Environment handling logic  
- runner → Cucumber test runner to execute feature files by path / tags  
- stepDefinition → Step definitions  
- util → Utility classes (ConfigReader)

---

## 🚀 Key Features

- Page Object Model (POM) design pattern  
- Environment-based configuration (UAT / Preprod)  
- Centralized configuration management using properties files  
- Reusable utilities and base classes  
- Cucumber BDD implementation  
- Tag-based execution for selective test runs  

### 🔍 Advanced Error Handling

- Base class contains dedicated methods with separate try-catch blocks  
- Captures exact failure reason for each test case  
- Improves debugging efficiency and reduces analysis time  
- Helps identify root cause quickly during failures  

### ✅ Assertion Handling & Reporting

- Assertions are implemented at key validation points in the test flow  
- Failures are clearly highlighted in execution reports  
- Provides precise validation messages for better debugging  
- Ensures accurate verification of application behavior  

### 📊 Logging Support

- Logging implemented for better traceability of execution  
- Helps in analyzing test flow and identifying failures easily  

---

## 🧪 Test Scenario Covered

End-to-end user journey automation:

- User logs into the application  
- Selects product(s)  
- Adds product to cart  
- Validates cart details  

---

## ▶️ How to Run

1. Clone the repository  
2. Navigate to project folder  
3. Run:

```bash
mvn test
