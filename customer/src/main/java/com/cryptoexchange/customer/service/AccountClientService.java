package com.cryptoexchange.customer.service;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.model.Currency;

import java.util.UUID;

public interface AccountClientService {

    AccountDTO createAccount(Currency currency, UUID id);

}
