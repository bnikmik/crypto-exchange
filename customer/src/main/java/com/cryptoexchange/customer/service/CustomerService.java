package com.cryptoexchange.customer.service;

import com.cryptoexchange.customer.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO findCustomerById(Long id);

    List<CustomerDTO> findAllCustomers();

    CustomerDTO updateCustomerById(Long id, CustomerDTO customer);

    void deleteCustomerById(Long id);
}
