-- liquibase formatted sql
-- changeset bnikmik:3

ALTER TABLE customer
    ADD is_active boolean;