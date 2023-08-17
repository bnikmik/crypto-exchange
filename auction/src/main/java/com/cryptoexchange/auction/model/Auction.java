package com.cryptoexchange.auction.model;

import com.cryptoexchange.common.model.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID sellerId;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal coinPrice;
    private BigDecimal amountCoins;
    @ElementCollection
    @CollectionTable(name = "auction_deals", joinColumns = @JoinColumn(name = "auction_id"))
    @Column(name = "deal_id")
    private List<UUID> dealsIds;
}
