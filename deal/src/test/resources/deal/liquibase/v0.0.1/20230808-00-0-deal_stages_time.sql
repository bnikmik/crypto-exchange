-- liquibase formatted sql
-- changeset bnikmik:8

create table deal_status_time
(
    deal_id          uuid         not null references deal,
    deal_status_time timestamp,
    deal_status      varchar(255) not null,
    primary key (deal_id, deal_status)
);
