package com.cryptoexchange.deal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Deal {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private DealType dealType;
    @Enumerated(EnumType.STRING)
    private DealStatus dealStatus;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal balance;
    private UUID sellerId;
    private UUID buyerId;
    private UUID guarantorId;
    @ElementCollection
    @CollectionTable(name = "deal_status_time", joinColumns = @JoinColumn(name = "deal_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "deal_status")
    private Map<DealStatus, Instant> dealStatusTime;
}
