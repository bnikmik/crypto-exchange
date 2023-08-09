package com.cryptoexchange.deal.service;

import com.cryptoexchange.common.dto.CustomerDTO;

public interface CustomerClientService {

    CustomerDTO findCustomerById(Long id);
}
