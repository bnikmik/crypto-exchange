package com.cryptoexchange.account.mapper;

import com.cryptoexchange.account.dto.AccountTransactionDTO;
import com.cryptoexchange.account.model.AccountTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountTransactionMapper {

    AccountTransactionMapper INSTANCE = Mappers.getMapper(AccountTransactionMapper.class);

    @Mapping(target = "account",source = "account.id")
    AccountTransactionDTO toDTO(AccountTransaction accountTransaction);

    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "createdAt",ignore = true),
            @Mapping(target = "type",ignore = true),
            @Mapping(target = "account",ignore = true)
    })
    AccountTransaction toEntity(AccountTransactionDTO accountTransactionDTO);
}
