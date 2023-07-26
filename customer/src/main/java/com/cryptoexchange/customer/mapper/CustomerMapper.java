package com.cryptoexchange.customer.mapper;

import com.cryptoexchange.customer.dto.CustomerDTO;
import com.cryptoexchange.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO dto);
}
