package com.cryptoexchange.deal.repository;

import com.cryptoexchange.deal.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, UUID> {
}
