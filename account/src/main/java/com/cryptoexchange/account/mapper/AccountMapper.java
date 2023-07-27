package com.cryptoexchange.account.mapper;

import com.cryptoexchange.account.dto.AccountDTO;
import com.cryptoexchange.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDTO(Account account);

    Account toEntity(AccountDTO accountDTO);
}
