-- liquibase formatted sql
-- changeset bnikmik:1

create table customer_roles
(
    customer_id bigint not null references customer,
    role        varchar(255)
);

