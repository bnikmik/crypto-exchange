package com.cryptoexchange.web.service;

import com.cryptoexchange.common.model.Currency;

public interface CustomerClientService {

    boolean checkCustomerRegistration(String id);

    void createCustomer();

    void addAccount(Currency currency);
}
