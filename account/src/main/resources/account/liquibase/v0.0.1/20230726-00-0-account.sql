-- liquibase formatted sql
-- changeset bnikmik:4

create table account_schema.account
(
    id        uuid not null primary key,
    currency  varchar(255),
    balance   numeric,
    is_active boolean
);