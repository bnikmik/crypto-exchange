-- liquibase formatted sql
-- changeset bnikmik:1

create table account
(
    id             uuid not null primary key,
    currency_type  varchar(255),
    balance        numeric
);