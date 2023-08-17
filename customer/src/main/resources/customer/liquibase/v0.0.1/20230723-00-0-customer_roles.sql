-- liquibase formatted sql
-- changeset bnikmik:2

create table customer_schema.customer_roles
(
    customer_id uuid not null references customer_schema.customer,
    role        varchar(255)
);

