package com.shop.products.adapters.inbound.mapper;

import com.shop.products.adapters.inbound.model.AppliedRate;
import com.shop.products.domain.model.ProductRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper
public interface ApiResponseMapper {

    @Mapping(target = "applicationStartDate", source = "startDate")
    @Mapping(target = "applicationEndDate", source = "endDate")
    @Mapping(target = "finalPriceToApply", source = "price")
    @Mapping(target = "rateToApply", source = "priceList")
    AppliedRate toAppliedRate(ProductRate productRateDTO);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.atOffset(ZoneOffset.UTC);
    }

    default LocalDateTime map(OffsetDateTime value) {
        if (value == null) {
            return null;
        }
        return value.toLocalDateTime();
    }
}
