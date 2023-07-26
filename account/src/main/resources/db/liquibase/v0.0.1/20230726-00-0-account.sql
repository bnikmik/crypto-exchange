-- liquibase formatted sql
-- changeset bnikmik:1

create table account
(
    id             bigserial primary key,
    account_number varchar(255),
    currency_type  varchar(255),
    balance        numeric
);