-- liquibase formatted sql
-- changeset bnikmik:2

ALTER TABLE account
    ADD is_active boolean;