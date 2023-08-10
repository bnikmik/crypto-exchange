package com.cryptoexchange.deal.service;

import com.cryptoexchange.common.dto.CustomerDTO;

import java.util.Optional;

public interface CustomerClientService {

    CustomerDTO findCustomerById(Long id);
}
