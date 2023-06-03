CREATE DATABASE leasing_application;

USE leasing_application;

CREATE TABLE customer (
  id INT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  birthdate DATE NOT NULL
);

CREATE TABLE vehicle (
  id INT PRIMARY KEY,
  brand VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  model_year INT NOT NULL,
  vin VARCHAR(17),
  price DECIMAL(10,2) NOT NULL
);

CREATE TABLE contract (
  id INT PRIMARY KEY,
  monthly_rate DECIMAL(10,2) NOT NULL,
  customer_id INT NOT NULL,
  vehicle_id INT NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer(id),
  FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);


