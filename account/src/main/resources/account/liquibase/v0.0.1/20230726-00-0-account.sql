-- liquibase formatted sql
-- changeset bnikmik:3

create table account_schema.account
(
    id        uuid not null primary key,
    currency  varchar(255),
    balance   numeric,
    is_active boolean
);