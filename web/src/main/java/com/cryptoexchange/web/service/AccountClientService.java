package com.cryptoexchange.web.service;

import com.cryptoexchange.common.dto.AccountDTO;

import java.util.List;

public interface AccountClientService {
    List<AccountDTO> findAllAccountsByCustomerId(String customerId);

}
