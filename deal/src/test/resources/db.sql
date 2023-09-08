create table deal
(
    id           uuid not null primary key,
    deal_type    varchar(255),
    deal_status  varchar(255),
    currency     varchar(255),
    balance      numeric,
    buyer_id     uuid,
    seller_id    uuid,
    guarantor_id uuid,
    auction_id   uuid
);

create table deal_status_time
(
    deal_id          uuid         not null references deal,
    deal_status_time timestamp,
    deal_status      varchar(255) not null,
    primary key (deal_id, deal_status)
);