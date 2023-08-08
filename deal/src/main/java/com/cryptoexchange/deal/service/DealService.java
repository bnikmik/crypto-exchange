package com.cryptoexchange.deal.service;

import com.cryptoexchange.deal.dto.DealDTO;
import com.cryptoexchange.deal.dto.DealStatusDTO;

import java.util.UUID;

public interface DealService {

    DealDTO createNewDeal(DealDTO dealDTO);

    DealDTO updateDealStatusById(UUID dealId, DealStatusDTO dealStatusDTO);
}
