-- liquibase formatted sql
-- changeset bnikmik:3

create table customer_schema.customer_accounts
(
    customer_id       bigint not null references customer_schema.customer,
    currency          varchar(255),
    customer_accounts uuid
);