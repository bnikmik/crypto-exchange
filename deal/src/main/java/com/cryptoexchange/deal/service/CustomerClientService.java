package com.cryptoexchange.deal.service;

import com.cryptoexchange.common.dto.CustomerDTO;

import java.util.UUID;

public interface CustomerClientService {

    CustomerDTO findCustomerById(UUID id);
}
