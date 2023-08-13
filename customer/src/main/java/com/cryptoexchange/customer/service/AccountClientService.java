package com.cryptoexchange.customer.service;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.model.Currency;

public interface AccountClientService {

    AccountDTO createAccount(Currency currency);

}
