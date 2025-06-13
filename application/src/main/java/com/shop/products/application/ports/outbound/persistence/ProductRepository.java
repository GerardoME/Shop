package com.shop.products.application.ports.outbound.persistence;

import com.shop.products.domain.model.ProductRate;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductRepository {
    Optional<ProductRate> findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(
            Integer productId, Integer brandId, LocalDate applicationDate);
}
