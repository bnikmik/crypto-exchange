-- liquibase formatted sql
-- changeset bnikmik:10

create table auction_schema.auction_deals
(
    auction_id uuid not null references auction_schema.auction,
    deal_id    uuid
);