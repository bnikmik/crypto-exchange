package com.cryptoexchange.auction.repository;

import com.cryptoexchange.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, UUID> {

}
