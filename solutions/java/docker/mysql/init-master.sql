CREATE USER 'repl'@'%' IDENTIFIED BY 'slave_password';
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';

CREATE SCHEMA order_db;

create table order_db.t_order_0
(
    user_id  long        not null,
    order_id varchar(32) not null
);

create table order_db.t_order_1
(
    user_id  long        not null,
    order_id varchar(32) not null
);

