create table customer
(
    id int         not null
        primary key,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    birthdate  date        not null
);