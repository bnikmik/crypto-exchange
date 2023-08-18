-- liquibase formatted sql
-- changeset bnikmik:11

ALTER table account_schema.account
    ADD column customer_id uuid;

