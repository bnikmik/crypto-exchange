package com.cryptoexchange.deal.service;

import com.cryptoexchange.common.model.DealStatus;
import com.cryptoexchange.deal.dto.DealDTO;

import java.util.UUID;

public interface DealService {

    DealDTO createNewDeal(DealDTO dealDTO);

    DealDTO updateDealStatusById(UUID dealId, DealStatus dealStatus);

    DealDTO findDealById(UUID dealId);
}
