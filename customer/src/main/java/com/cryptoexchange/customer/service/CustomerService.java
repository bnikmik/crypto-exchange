package com.cryptoexchange.customer.service;

import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.customer.dto.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO addAccountForCustomerById(UUID id, Currency currency);

    CustomerDTO findCustomerById(UUID id);

    List<CustomerDTO> findAllCustomers();

    CustomerDTO updateCustomerById(UUID id, CustomerDTO customer);

    void deleteCustomerById(UUID id);
}
