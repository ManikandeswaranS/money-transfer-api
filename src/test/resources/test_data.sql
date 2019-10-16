DROP TABLE IF EXISTS Customer;

CREATE TABLE Customer (CustomerId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 CustomerName VARCHAR(30) NOT NULL,
 Email VARCHAR(30) NOT NULL,
 Country VARCHAR(30) NOT NULL);

CREATE UNIQUE INDEX idx_ue on Customer(CustomerName,Email,Country);

INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test1','test1@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test2','test2@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test3','test3@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test4','test4@test.com','UK');
INSERT INTO Customer (CustomerName, Email, Country) VALUES ('test5','test5@test.com','UK');

DROP TABLE IF EXISTS Account;

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
CustomerName VARCHAR(30),
AccountBalance DECIMAL(19,4),
CurrencyCode VARCHAR(30),
AccountType VARCHAR(30),
IsValid VARCHAR(30),
WithDrawalLimit DECIMAL(19)
);

CREATE UNIQUE INDEX idx_acc on Account(CustomerName,CurrencyCode);

INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test1',700.0000,'GBP','Savings','valid', 1000);
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test2',200.0000,'GBP','Current','valid', 500);
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test3',300.0000,'GBP','Savings','invalid', 1000);
INSERT INTO Account (CustomerName,AccountBalance,CurrencyCode,AccountType,IsValid,WithDrawalLimit) VALUES ('test4',400.0000,'GBP','Savings','valid', 1000);
