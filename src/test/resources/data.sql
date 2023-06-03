create table customer
(
    id int         not null
        primary key,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    birthdate  date        not null
);

create table vehicle
(
    id         int            not null
        primary key,
    brand      varchar(50)    not null,
    model      varchar(50)    not null,
    model_year int            not null,
    vin        varchar(17)    null,
    price      decimal(10, 2) not null
);

create table contract
(
    id           int            not null
        primary key,
    monthly_rate decimal(10, 2) not null,
    customer_id  int            not null,
    vehicle_id   int            not null,
    constraint contract_ibfk_1
        foreign key (customer_id) references customer (id),
    constraint contract_ibfk_2
        foreign key (vehicle_id) references vehicle (id)
);


INSERT INTO customer (id, first_name, last_name, birthdate)
VALUES (1, 'John', 'Doe', '1990-01-01'),
       (2, 'Jane', 'Smith', '1985-05-15'),
       (3, 'Michael', 'Johnson', '1992-09-20'),
       (4, 'Emily', 'Williams', '1995-07-12');

INSERT INTO vehicle (id, brand, model, model_year, vin, price)
VALUES (1, 'Toyota', 'Camry', 2022, 'JT2BF22K1W0200001', 25000.00),
       (2, 'Honda', 'Civic', 2021, '2HGFC2F51MH567890', 22000.00),
       (3, 'Ford', 'F-150', 2020, '1FTFW1ED9LFA00001', 35000.00),
       (4, 'Chevrolet', 'Malibu', 2019, '1G1ZD5ST0KF123456', 23000.00);

INSERT INTO contract (id, monthly_rate, customer_id, vehicle_id)
VALUES (1, 350.00, 1, 1),
       (2, 300.00, 2, 2),
       (3, 400.00, 3, 3),
       (4, 320.00, 4, 4);