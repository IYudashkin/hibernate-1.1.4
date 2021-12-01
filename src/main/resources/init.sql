CREATE SCHEMA IF NOT EXISTS taskjdbc;
USE taskjdbc;

DROP TABLE IF EXISTS user;
CREATE TABLE users (id LONG PRIMARY KEY AUTO_INCREMENT, name STRING, lastName VARCHAR(100), age BYTE);

INSERT INTO users(name. lastName, age) VALUES ('Ivan', 'Petrov', 25);


