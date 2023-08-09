-- liquibase formatted sql
-- changeset bnikmik:7

create table deal_schema.deal
(
    id           uuid not null primary key,
    deal_type    varchar(255),
    deal_status  varchar(255),
    currency     varchar(255),
    balance      numeric,
    buyer_id     bigint,
    seller_id    bigint,
    guarantor_id bigint
);

