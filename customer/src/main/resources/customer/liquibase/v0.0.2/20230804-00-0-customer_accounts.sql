-- liquibase formatted sql
-- changeset bnikmik:6

create table customer_schema.customer_accounts
(
    customer_id bigint not null references customer_schema.customer,
    account     uuid
);