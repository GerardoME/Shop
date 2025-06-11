package com.shop.products.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductRate {
    private Long productId;
    private String brand;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String priceList;
    private Integer priority;
    private BigDecimal price;
    private String curr;
}
