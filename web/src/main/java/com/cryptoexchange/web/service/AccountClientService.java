package com.cryptoexchange.web.service;

import java.util.List;

public interface AccountClientService {
    List<String> findAllAccountsByCustomerId(String customerId);

}
