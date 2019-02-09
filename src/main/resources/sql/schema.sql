create database db_example;
create user 'springuser'@'%' identified by '1234';
grant all on db_example.* to 'springuser'@'%';
connect db_example;
CREATE TABLE USER (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  FIRST_NAME varchar(255) DEFAULT NULL,
  LAST_NAME varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);
