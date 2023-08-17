package com.cryptoexchange.auction.mapper;

import com.cryptoexchange.auction.dto.AuctionDTO;
import com.cryptoexchange.auction.model.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuctionMapper {

    AuctionMapper INSTANCE = Mappers.getMapper(AuctionMapper.class);

    //    @Mapping(target = "dealsIds", ignore = true)
    AuctionDTO toDTO(Auction account);

    //    @Mapping(target = "dealsIds", ignore = true)
    Auction toEntity(AuctionDTO accountDTO);

}
