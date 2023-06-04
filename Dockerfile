FROM mysql:latest

ENV MYSQL_DATABASE=root \
    MYSQL_ROOT_PASSWORD=root

ADD docker-leasing-schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306


