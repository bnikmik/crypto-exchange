-- liquibase formatted sql
-- changeset bnikmik:1

create table customer_schema.customer
(
    id           bigserial primary key,
    login varchar(255),
    full_name varchar(255),
    avatar_link  varchar(255),
    email        varchar(255),
    is_verified  boolean,
    is_active boolean,
    phone_number varchar(255)
);