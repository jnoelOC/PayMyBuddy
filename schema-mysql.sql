/* Setting up PayMyBuddy DB */
drop database if exists paymybuddy;
create database paymybuddy;
use paymybuddy;

create table bank_account(
ID bigint PRIMARY KEY AUTO_INCREMENT,
BANK_NAME varchar(30),
IBAN varchar(30) NOT NULL,
BIC varchar(15) NOT NULL,
LOGIN_MAIL varchar(50) NOT NULL
);

create table transac(
ID bigint PRIMARY KEY AUTO_INCREMENT,
AMOUNT int,
DESCRIPTION varchar(60),
GIVER varchar(50),
RECEIVER varchar(50)
);

create table connection(
ID bigint ,
CONNECTION_ID bigint 
);

create table user_account(
 ID bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
 FIRSTNAME varchar(30),
 LASTNAME varchar(30),
 LOGIN_MAIL varchar(50) NOT NULL,
 PSSWRD varchar(70) NOT NULL,
 SOLDE double
 );
 
 commit;