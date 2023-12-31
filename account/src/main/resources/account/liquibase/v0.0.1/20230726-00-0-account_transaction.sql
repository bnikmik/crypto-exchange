-- liquibase formatted sql
-- changeset bnikmik:5

create table account_schema.account_transaction
(
    id         uuid not null primary key,
    created_at timestamp,
    type       varchar(255),
    value      numeric,
    account_id uuid references account_schema.account
);

