package com.shop.products.application.ports.inbound;

import com.shop.products.domain.model.ProductRate;

import java.time.LocalDate;
import java.util.Optional;

public interface FindProductRateUseCase {

    Optional<ProductRate> findProductRate(LocalDate applicationDate, Integer productId, Integer brandId);
}
