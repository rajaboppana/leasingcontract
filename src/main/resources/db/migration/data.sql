USE leasing_application;

DROP TABLE IF EXISTS contract;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS vehicle;

CREATE TABLE customer (
      id INT AUTO_INCREMENT PRIMARY KEY,
      first_name VARCHAR(50) NOT NULL,
      last_name VARCHAR(50) NOT NULL,
      birthdate DATE NOT NULL
);

CREATE TABLE vehicle (
     id INT AUTO_INCREMENT PRIMARY KEY,
     brand VARCHAR(50) NOT NULL,
     model VARCHAR(50) NOT NULL,
     model_year INT NOT NULL,
     vin VARCHAR(17) UNIQUE ,
     price DECIMAL(10,2) NOT NULL
);

CREATE TABLE contract (
      id INT AUTO_INCREMENT PRIMARY KEY,
      monthly_rate DECIMAL(10,2) NOT NULL,
      customer_id INT,
      vehicle_id INT,
      FOREIGN KEY (customer_id) REFERENCES customer(id),
      FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);