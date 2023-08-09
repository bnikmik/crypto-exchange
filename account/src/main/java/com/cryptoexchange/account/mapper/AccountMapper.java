package com.cryptoexchange.account.mapper;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.BalanceDTO;
import com.cryptoexchange.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "transactionList", ignore = true)
    AccountDTO toDTO(Account account);

    @Mapping(target = "transactionList", ignore = true)
    Account toEntity(AccountDTO accountDTO);

    BalanceDTO toBalanceDTO(Account account);
}
