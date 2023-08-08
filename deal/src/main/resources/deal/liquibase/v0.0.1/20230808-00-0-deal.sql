-- liquibase formatted sql
-- changeset bnikmik:7

create table deal_schema.deal
(
    id           uuid not null primary key,
    deal_type    varchar(255),
    deal_status  varchar(255),
    currency     varchar(255),
    balance      numeric,
    buyer_id     uuid,
    seller_id    uuid,
    guarantor_id uuid
);

