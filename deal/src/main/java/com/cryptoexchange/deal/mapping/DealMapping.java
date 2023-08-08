package com.cryptoexchange.deal.mapping;

import com.cryptoexchange.deal.dto.DealDTO;
import com.cryptoexchange.deal.dto.DealStatusDTO;
import com.cryptoexchange.deal.model.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DealMapping {
    DealMapping INSTANCE = Mappers.getMapper(DealMapping.class);

    DealDTO toDTO(Deal deal);

    Deal toEntity(DealDTO dto);

    Deal toNewStatusEntity(DealStatusDTO dto);
}
