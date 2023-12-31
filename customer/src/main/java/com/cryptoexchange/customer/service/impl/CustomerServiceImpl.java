package com.cryptoexchange.customer.service.impl;

import com.cryptoexchange.common.dto.AccountDTO;
import com.cryptoexchange.common.exception.types.AccountBadRequestException;
import com.cryptoexchange.common.exception.types.RecordNotFoundException;
import com.cryptoexchange.common.model.Currency;
import com.cryptoexchange.customer.dto.CustomerDTO;
import com.cryptoexchange.customer.model.Customer;
import com.cryptoexchange.customer.repository.CustomerRepository;
import com.cryptoexchange.customer.service.AccountClientService;
import com.cryptoexchange.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.cryptoexchange.customer.mapper.CustomerMapper.INSTANCE;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository repository;
    private final AccountClientService accountClientService;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = INSTANCE.toEntity(customerDTO);
        AccountDTO accountDTO = accountClientService.createAccount(Currency.BTC, customer.getId());
        customer.getCustomerAccounts().put(accountDTO.getCurrency(), accountDTO.getId());
        repository.save(customer);

        log.info("Пользователь "+ customer.getLogin() + " создан!");
        return INSTANCE.toDTO(customer);
    }

    @Override
    public CustomerDTO addAccountForCustomerById(UUID id, Currency currency) {
        Customer customer = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Пользователь с ID " + id + " не найден"));
        if (customer.getCustomerAccounts().containsKey(currency))
            throw new AccountBadRequestException("Счет с типом валюты " + currency + " уже существует");
        AccountDTO accountDTO = accountClientService.createAccount(currency, id);
        customer.getCustomerAccounts().put(accountDTO.getCurrency(), accountDTO.getId());
        repository.save(customer);
        return INSTANCE.toDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findCustomerById(UUID id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Пользователь с ID " + id + " не найден"));
        return INSTANCE.toDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAllCustomers() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customerDTO) {
        Customer tmp = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Пользователь с ID " + id + " не найден"));
        tmp.setLogin(customerDTO.getLogin());
        tmp.setFullName(customerDTO.getFullName());
        tmp.setEmail(customerDTO.getEmail());
        tmp.setAvatarLink(customerDTO.getAvatarLink());
        tmp.setPhoneNumber(customerDTO.getPhoneNumber());
        repository.save(tmp);
        return INSTANCE.toDTO(tmp);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Пользователь с ID " + id + " не найден"));
        customer.setIsActive(false);
        repository.save(customer);
    }
}
