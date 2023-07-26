package com.cryptoexchange.customer.service.impl;

import com.cryptoexchange.customer.dto.CustomerDTO;
import com.cryptoexchange.customer.exception.NotFoundException;
import com.cryptoexchange.customer.model.Customer;
import com.cryptoexchange.customer.repository.CustomerRepository;
import com.cryptoexchange.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cryptoexchange.customer.mapper.CustomerMapper.INSTANCE;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        repository.save(INSTANCE.toEntity(customerDTO));
        return customerDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findCustomerById(Long id) {
        Customer customer = repository.findById(id).orElseThrow(NotFoundException::new);
        return INSTANCE.toDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAllCustomers() {
        return repository.findAll().stream().map(INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomerById(Long id, CustomerDTO customerDTO) {
        Customer tmp = repository.findById(id).orElseThrow(NotFoundException::new);
        tmp.setVerified(customerDTO.isVerified());
        tmp.setEmail(customerDTO.getEmail());
        tmp.setRolesList(customerDTO.getRolesList());
        tmp.setAvatarLink(customerDTO.getAvatarLink());
        tmp.setPhoneNumber(customerDTO.getPhoneNumber());
        repository.save(tmp);
        return INSTANCE.toDTO(tmp);
    }

    @Override
    public void deleteCustomerById(Long id) {
        Customer customer = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.delete(customer);
    }
}
