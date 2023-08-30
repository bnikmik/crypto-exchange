package com.cryptoexchange.web.service;

import com.cryptoexchange.common.dto.DealDTO;
import com.cryptoexchange.common.model.DealStatus;

import java.util.UUID;

public interface DealClientService {

    DealDTO findDealById(UUID id);

    void updateDealById(UUID id, DealStatus dealStatus);
}
