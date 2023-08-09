-- liquibase formatted sql
-- changeset bnikmik:6

ALTER table account_schema.account
ADD column last_transaction_date timestamp;

