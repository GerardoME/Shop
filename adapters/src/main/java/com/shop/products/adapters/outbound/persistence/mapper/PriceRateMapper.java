package com.shop.products.adapters.outbound.persistence.mapper;

import com.shop.products.adapters.outbound.persistence.PriceRateEntity;
import com.shop.products.domain.model.ProductRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PriceRateMapper {

    @Mapping(target = "brand", source = "brand.name")
    @Mapping(target = "priceList", source = "priceList.name")
    ProductRate toProductRate(PriceRateEntity priceRateEntity);
}
