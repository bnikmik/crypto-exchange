-- liquibase formatted sql
-- changeset bnikmik:2

ALTER TABLE customer
    ADD login varchar(255),
    ADD full_name varchar(255);