package com.shop.products.adapters.mapper;

import com.shop.products.adapters.inbound.model.AppliedRate;
import com.shop.products.domain.model.ProductRate;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper
public interface ApiResponseMapper {

    AppliedRate toAppliedRate(ProductRate productRateDTO);

    default LocalDateTime map(OffsetDateTime value) {
        if (value == null) {
            return null;
        }
        return value.toLocalDateTime();
    }
}
