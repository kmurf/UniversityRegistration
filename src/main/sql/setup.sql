CREATE USER IF NOT EXISTS 'myapp'@'localhost' IDENTIFIED BY 'pazzw0rd';
GRANT ALL PRIVILEGES ON *.* TO 'myapp'@'localhost';
CREATE DATABASE university;