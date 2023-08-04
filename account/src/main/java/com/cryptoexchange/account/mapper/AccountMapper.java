package com.cryptoexchange.account.mapper;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.dto.BalanceDTO;
import com.cryptoexchange.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "id", source = "id")
    AccountDTO toDTO(Account account);

    Account toEntity(AccountDTO accountDTO);

    BalanceDTO toBalanceDTO(Account account);
}
