# Credit Module

## Overview

This project is a **Credit Module** built using **Spring Boot**. It allows **Admin** users to manage loans for customers, including the ability to create loans, make payments, and list loan details. Customers can only interact with their own loan information.

## Features
- **Create Loan**: Allows an admin to create loans for customers.
- **List Loans**: Allows an admin to list loans of a customer.
- **List Installments**: Lists all installments for a specific loan.
- **Pay Loan**: Pay off installments, with discounts for early payments and penalties for late payments.

## Prerequisites
- **Java 17** or later
- **Docker** installed on your machine

---

## Setup & Run

### 1. Clone the Repository

Clone this repository to your local machine:

```bash 
git clone https://github.com/mertdestici/CreditModule.git 
cd loan-management-system
```

### 2. Build the Application
```bash
./gradlew clean build
```

### 3. Build the Docker Image
```bash
docker build -t credit-module .
```

### 4. Run the Application with Docker
```bash
docker run -p 8080:8080 credit-module
```

#### Note: open api and postman collection in the resources folder
