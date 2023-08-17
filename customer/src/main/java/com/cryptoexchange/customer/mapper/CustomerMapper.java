package com.cryptoexchange.customer.mapper;

import com.cryptoexchange.customer.dto.CustomerDTO;
import com.cryptoexchange.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerAccounts", source = "customerAccounts")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "customerAccounts", source = "customerAccounts")
    Customer toEntity(CustomerDTO dto);
}
