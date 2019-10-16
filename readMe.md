# money-transfer-api

#### Description

This API is used to manage account data and customer data. Also this will enable you to transfer funds between
different accounts.

#High level Data design:

1. Customer table will hold the customer data having CustomerName, Email and Country.
2. Account table will hold account data having CustomerName, Balance, CurrencyCode, AccountType, IsValid and WithDrawalLimit.
3. test/resources/test_data.sql file has initial test data setup for unit tests and postman tests.

#Test data :
```
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test1','test1@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test2','test2@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test3','test3@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test4','test4@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test5','test5@test.com','UK');
```
```
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test1',700.0000,'GBP','Savings','valid', 1000);
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test2',200.0000,'GBP','Current','valid', 500);
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test3',300.0000,'GBP','Savings','invalid', 1000);
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test4',400.0000,'GBP','Savings','valid', 1000);
```

High level API design:
=====================
This API supports below operations on Account, Customer and mainly fund transfers.

Account :
=======
1. ######Get Account By Id
        url - http://localhost:8080/accounts/1
        header - Content-Type:application/json
2. ######Get All Accounts
        url - http://localhost:8080/accounts/list
        header - Content-Type:application/json
3. ######Get Account Balance
        url - http://localhost:8080/accounts/1/balance
        header - Content-Type:application/json
4. ######PUT Create Account
        url - http://localhost:8080/accounts/create
        header - Content-Type:application/json
        body application/json:  {
                                    "accountId": 5,
                                    "accountType": "Savings",
                                    "accountValid": true,
                                    "customerName": "test5",
                                    "accountBalance": 710,
                                    "currencyCode": "GBP",
                                    "withDrawLimit": 1000
                               }
5. ######PUT Deposit fund
        url - http://localhost:8080/accounts/1/deposit/10
        header - Content-Type:application/json
6. ######PUT WithDraw fund
        url - http://localhost:8080/accounts/1/withdraw/10
        header - Content-Type:application/json
7. ######DEL Account
        url - http://localhost:8080/accounts/4
        header - Content-Type:application/json

Customer:
=========
1. ######Get Customer By Name
        url - http://localhost:8080/customers/test5
        header - Content-Type:application/json
2. ######Get All Customers
        url - http://localhost:8080/customers/list
        header - Content-Type:application/json
3. ######POST Create Customer
        url - http://localhost:8080/accounts/create
        header - Content-Type:application/json
        body application/json:  {
                                   "customerName": "test5",
                                   "email": "test5@test.com",
                                   "country": "UK"
                                }
4. ######PUT Update Customer
        url - http://localhost:8080/customers/5
        header - Content-Type:application/json
        body application/json:  {
                                   "customerId": 5,
                                   "customerName": "test5",
                                   "email": "test5@gmail.com",
                                   "country": "UK"
                                }
5. ######DEL Customer
        url - http://localhost:8080/customers/5
        header - Content-Type:application/json

Transfers:
=========
1. ######POST Transfer Fund
        url - http://localhost:8080/transfers
        header - Content-Type:application/json
        body application/json:  {
                                	"amount": 10,
                                	"sourceAccountId": 1,
                                	"targetAccountId":4
                                }

Build and Execution:
===================
```
1. This is gradle build project.
        group 'com.org.transfers'
        version '1.0-SNAPSHOT'
        artifact 'moneyTransfer'
2. 'Gradle clean build' will create a moneyTransfer-1.0-SNAPSHOT.jar having all dependencies.
3. For the ease of testing, I have checked in the jar in the test_lib folder.
4. moneyTransfer-1.0-SNAPSHOT.jar can be executed using
   'java -jar moneyTransfer-1.0-SNAPSHOT.jar' which will start local server.
5. After the server is started, we can test the services using postman.
6. Postman collections also checked in for reference.
Please find the same in the root path with the name 'postman_collection.json'.
```
Code structure:
==============
```
1. Root package for this project is com.org.transfers.
2. Resource package in src/main/java contains all the resources(rest end points) for accounts, customer and money transfers.
3. Repository package in src/main/java contains all the classes which will integrate with in-memory H2 database.
4. Application.java in the root package is the main class which will initialise H2 database and start the jetty server.
5. Domain package in src/main/java contains all the domain objects or POJOs.
6. Junit tests are created within tests package.
7. Logs are written inside logs/transferMoney.log file.
```