-- liquibase formatted sql
-- changeset bnikmik:9

create table auction_schema.auction
(
    id           uuid not null primary key,
    seller_id    bigint,
    currency     varchar(255),
    coin_price   numeric,
    amount_coins numeric
);
