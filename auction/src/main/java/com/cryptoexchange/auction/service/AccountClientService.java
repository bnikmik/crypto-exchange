package com.cryptoexchange.auction.service;

import com.cryptoexchange.common.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

public interface AccountClientService {

    List<AccountDTO> findAllAccountsByCustomerId(UUID customerId);

}
