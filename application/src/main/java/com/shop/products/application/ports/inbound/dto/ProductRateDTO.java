package com.shop.products.application.ports.inbound.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Builder
@Data
public class ProductRateDTO {
    private Long productId;

    private String brandId;

    private String rateToApply;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime applicationStartDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime applicationEndDate;

    private BigDecimal finalPriceToApply;
}
